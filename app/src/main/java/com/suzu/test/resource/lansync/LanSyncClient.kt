package com.suzu.test.resource.lansync

import java.io.Closeable
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.NetworkInterface
import java.net.Socket
import java.net.SocketTimeoutException
import java.security.MessageDigest
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.Locale
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocket
import javax.net.ssl.X509TrustManager
import org.json.JSONObject

internal data class LanEndpoint(val host: String, val port: Int) {
    companion object {
        fun parse(value: String): LanEndpoint {
            val parts = value.trim().split(':')
            require(parts.size == 2) { "请输入电脑显示的地址（IPv4:端口）。" }
            val octets = parts[0].split('.')
            require(octets.size == 4 && octets.all { it.matches(Regex("0|[1-9][0-9]{0,2}")) && it.toInt() <= 255 } &&
                octets[0].toInt() in 1..223 && octets[0] != "127") { "请输入有效的局域网 IPv4 地址。" }
            val port = parts[1].takeIf { it.matches(Regex("[0-9]{1,5}")) }?.toInt() ?: 0
            require(port in 1..65535) { "请输入有效端口。" }
            return LanEndpoint(parts[0], port)
        }
    }
}

internal data class LanPairing(val code: String, val addresses: List<LanEndpoint>, val pin: ByteArray? = null) {
    companion object {
        fun manual(code: String, address: String): LanPairing =
            LanPairing(validateCode(code.trim()), if (address.isBlank()) emptyList() else listOf(LanEndpoint.parse(address)))

        fun qr(value: String): LanPairing {
            require(value.length <= 512) { "不是有效的 SuzuEmojy 配对二维码。" }
            val fields = value.split('|')
            if (fields.firstOrNull() == "suzu-lan:1") throw IllegalArgumentException("请先更新电脑端，再扫描新的配对二维码。")
            require(fields.size == 4 && fields[0] == "suzu-lan:2" && fields[2].matches(Regex("[a-f0-9]{64}"))) {
                "不是有效的 SuzuEmojy 配对二维码。"
            }
            val addresses = fields[1].split(',')
            require(addresses.size in 1..12) { "二维码内的连接地址无效。" }
            return LanPairing(validateCode(fields[3]), addresses.map(LanEndpoint::parse).distinct(),
                ByteArray(32) { fields[2].substring(it * 2, it * 2 + 2).toInt(16).toByte() })
        }

        private fun validateCode(value: String): String {
            require(value.matches(Regex("[0-9]{6}"))) { "请输入电脑显示的 6 位配对码。" }
            return value
        }
    }
}

internal enum class LanTransfer(val wire: Int) { DOWNLOAD('D'.code), UPLOAD('U'.code), SYNC('S'.code) }
internal data class LanOffer(val transfer: LanTransfer, val fromComputer: Boolean)

internal class LanSyncClient : Closeable {
    companion object {
        const val MAX_PACKAGE = 2L * 1024 * 1024 * 1024
        private const val MAX_MESSAGE = 24 * 1024 * 1024
        private const val DISCOVERY_PORT = 41731
        private const val DISCOVERY = "SUZU-LAN-2"
        private val random = SecureRandom()

        internal fun commitment(role: Char, pin: ByteArray, nonce: ByteArray): ByteArray =
            MessageDigest.getInstance("SHA-256").digest(byteArrayOf(role.code.toByte()) + pin + nonce)

        internal fun verification(pin: ByteArray, clientNonce: ByteArray, serverNonce: ByteArray): String {
            val bytes = MessageDigest.getInstance("SHA-256").digest(byteArrayOf('V'.code.toByte()) + pin + clientNonce + serverNonce)
            var number = 0L
            for (i in 0..3) number = (number shl 8) or (bytes[i].toLong() and 255)
            return String.format(Locale.ROOT, "%06d", number % 1_000_000)
        }
    }

    private val resourceLock = Any()
    @Volatile private var closed = false
    private var active: Closeable? = null
    private lateinit var input: DataInputStream
    private lateinit var output: DataOutputStream
    private lateinit var connection: SSLSocket
    var canSync = false
        private set

    fun pair(pairing: LanPairing): String {
        val addresses = pairing.addresses.ifEmpty { discover() }
        if (addresses.isEmpty()) throw IOException("没有找到电脑，请输入电脑显示的地址和配对码。")
        var failure: Exception? = null
        for (address in addresses) {
            ensureOpen()
            try {
                return try { connect(address, pairing, modern = true) }
                catch (_: LegacyPeer) { connect(address, pairing, modern = false) }
            }
            catch (e: IOException) { failure = e }
        }
        if (addresses.size == 1 && pairing.addresses.isNotEmpty() && (failure is PairingRejected || failure is VersionMismatch)) throw failure
        throw IOException("未能配对，请检查配对码、地址及电脑防火墙。", failure)
    }

    private fun discover(): List<LanEndpoint> {
        val nonce = ByteArray(8).apply(random::nextBytes).joinToString("") { "%02x".format(Locale.ROOT, it.toInt() and 255) }
        val request = "$DISCOVERY?$nonce".toByteArray(Charsets.US_ASCII)
        val replyPrefix = "$DISCOVERY!$nonce:"
        val targets = linkedSetOf(InetAddress.getByName("255.255.255.255"))
        NetworkInterface.getNetworkInterfaces()?.toList()?.filter { it.isUp && !it.isLoopback }
            ?.flatMap { it.interfaceAddresses }?.mapNotNullTo(targets) { it.broadcast }
        val found = linkedSetOf<LanEndpoint>()
        DatagramSocket().use { socket ->
            track(socket)
            socket.broadcast = true
            for (target in targets) {
                ensureOpen()
                try { socket.send(DatagramPacket(request, request.size, target, DISCOVERY_PORT)) } catch (_: IOException) { }
            }
            val deadline = System.nanoTime() + 1_500_000_000L
            while (found.size < 8) {
                val remaining = (deadline - System.nanoTime()) / 1_000_000
                if (remaining <= 0) break
                socket.soTimeout = remaining.toInt()
                val packet = DatagramPacket(ByteArray(64), 64)
                try { socket.receive(packet) } catch (_: SocketTimeoutException) { break }
                if (packet.port != DISCOVERY_PORT) continue
                val reply = String(packet.data, packet.offset, packet.length, Charsets.US_ASCII)
                if (!reply.startsWith(replyPrefix)) continue
                runCatching { LanEndpoint.parse("${packet.address.hostAddress}:${reply.removePrefix(replyPrefix)}") }
                    .getOrNull()?.let { found.add(it) }
            }
        }
        return found.toList()
    }

    private fun connect(address: LanEndpoint, pairing: LanPairing, modern: Boolean): String {
        val host = InetAddress.getByName(address.host) // Input is already a numeric IPv4 address.
        if (!host.isSiteLocalAddress && !host.isLinkLocalAddress && !sameSubnet(host)) {
            throw IOException("该地址不在当前局域网内。")
        }
        val raw = Socket()
        track(raw)
        try {
            raw.connect(InetSocketAddress(host, address.port), 2_000)
            raw.soTimeout = 5_000
            val trust = PairingTrust(pairing.pin)
            val context = SSLContext.getInstance("TLS")
            context.init(null, arrayOf(trust), random)
            val ssl = context.socketFactory.createSocket(raw, address.host, address.port, true) as SSLSocket
            track(ssl)
            ssl.enabledProtocols = arrayOf("TLSv1.2")
            ssl.startHandshake()
            val input = DataInputStream(ssl.inputStream.buffered())
            val output = DataOutputStream(ssl.outputStream.buffered())
            output.write((if (modern) "SZLAN3" else "SZLAN2").toByteArray(Charsets.US_ASCII))
            output.flush()
            val protocol = input.readUnsignedByte()
            if (modern && protocol == 3) throw LegacyPeer()
            if (protocol != if (modern) 4 else 2) throw VersionMismatch()
            val fingerprint = trust.fingerprint ?: error("TLS 未提供电脑证书")
            val nonce = ByteArray(16).apply(random::nextBytes)
            output.write(pairing.code.toByteArray(Charsets.US_ASCII))
            output.write(commitment('C', fingerprint, nonce))
            output.flush()
            if (input.readUnsignedByte() != 1) throw PairingRejected()
            val serverCommit = ByteArray(32).also(input::readFully)
            output.write(nonce)
            output.flush()
            val serverNonce = ByteArray(16).also(input::readFully)
            if (!MessageDigest.isEqual(serverCommit, commitment('S', fingerprint, serverNonce))) {
                throw IOException("配对校验失败。")
            }
            ssl.soTimeout = 60_000
            this.input = input
            this.output = output
            connection = ssl
            canSync = modern
            return verification(fingerprint, nonce, serverNonce)
        } catch (e: Exception) {
            raw.close()
            throw e
        }
    }

    private fun sameSubnet(host: InetAddress): Boolean {
        val target = host.address
        return NetworkInterface.getNetworkInterfaces()?.toList()?.filter { it.isUp && !it.isLoopback }
            ?.flatMap { it.interfaceAddresses }?.any { address ->
                val local = address.address.address
                val bits = address.networkPrefixLength.toInt()
                local.size == 4 && target.size == 4 && bits in 1..32 && (0 until bits).all { bit ->
                    ((local[bit / 8].toInt() xor target[bit / 8].toInt()) and (128 ushr (bit % 8))) == 0
                }
            } == true
    }

    fun confirm() {
        writeStatus(1)
        if (input.readUnsignedByte() != 1) throw IOException("电脑未确认连接。")
        connection.soTimeout = 600_000
    }

    fun poll(request: LanTransfer?): LanOffer? {
        output.writeByte('P'.code)
        writeStatus(request?.wire ?: 0)
        return when (val action = input.readUnsignedByte()) {
            0 -> null
            else -> LanTransfer.entries.firstOrNull { it.wire == action && (it != LanTransfer.SYNC || canSync) }
                ?.let { LanOffer(it, readDecision()) } ?: throw IOException("收到不支持的传输操作。")
        }
    }

    fun readDecision(): Boolean = when (input.readUnsignedByte()) {
        0 -> false
        1 -> true
        else -> throw IOException("未收到对方的确认。")
    }

    fun upload(file: File, progress: (Int) -> Unit): Int {
        sendPackage(file, progress)
        return input.readUnsignedByte().also { if (it !in 0..2) throw IOException("未收到电脑的导入结果。") }
    }

    fun sendPackage(file: File, progress: (Int) -> Unit) {
        val size = checkedSize(file.length())
        val sha = MessageDigest.getInstance("SHA-256")
        file.inputStream().use { copyExact(it, null, size, sha) {} }
        output.writeLong(size)
        output.write(sha.digest())
        file.inputStream().use { copyExact(it, output, size, null, progress) }
        output.flush()
    }

    fun download(file: File, progress: (Int) -> Unit) {
        if (input.readUnsignedByte() != 1) throw IOException("电脑未能导出资源包。")
        receivePackage(file, progress)
    }

    fun receivePackage(file: File, progress: (Int) -> Unit) {
        val size = checkedSize(input.readLong())
        if ((file.parentFile?.usableSpace ?: 0L) < size) throw IOException("手机暂存空间不足。")
        val expected = ByteArray(32).also(input::readFully)
        val sha = MessageDigest.getInstance("SHA-256")
        file.outputStream().use { copyExact(input, it, size, sha, progress) }
        if (!MessageDigest.isEqual(expected, sha.digest())) throw IOException("资源包 SHA-256 校验失败。")
    }

    fun writeStatus(status: Int) { output.writeByte(status); output.flush() }

    fun readMessage(): JSONObject {
        val size = input.readInt()
        if (size !in 1..MAX_MESSAGE) throw IOException("同步清单大小无效。")
        return JSONObject(ByteArray(size).also(input::readFully).toString(Charsets.UTF_8))
    }

    fun writeMessage(message: JSONObject) {
        val bytes = message.toString().toByteArray(Charsets.UTF_8)
        if (bytes.size !in 1..MAX_MESSAGE) throw IOException("同步清单超过 24 MiB。")
        output.writeInt(bytes.size)
        output.write(bytes)
        output.flush()
    }

    private class LegacyPeer : IOException()

    private fun checkedSize(size: Long): Long {
        if (size !in 1..MAX_PACKAGE) throw IOException("资源包大小不受支持（上限 2 GiB）。")
        return size
    }

    private fun copyExact(from: InputStream, to: OutputStream?, length: Long, sha: MessageDigest?, progress: (Int) -> Unit) {
        val buffer = ByteArray(65_536)
        var remaining = length
        var last = -1
        while (remaining > 0) {
            ensureOpen()
            val count = from.read(buffer, 0, minOf(buffer.size.toLong(), remaining).toInt())
            if (count <= 0) throw IOException("资源包传输中断。")
            sha?.update(buffer, 0, count)
            to?.write(buffer, 0, count)
            remaining -= count
            val percent = ((length - remaining) * 100 / length).toInt()
            if (percent != last) { progress(percent); last = percent }
        }
    }

    private fun ensureOpen() { if (closed) throw IOException("连接已关闭。") }

    private fun track(resource: Closeable) = synchronized(resourceLock) {
        if (closed) { resource.close(); ensureOpen() }
        active = resource
    }

    override fun close() {
        val resource = synchronized(resourceLock) { closed = true; active.also { active = null } }
        runCatching { resource?.close() }
    }

    private class PairingRejected : IOException("配对码不正确或已失效。")
    private class VersionMismatch : IOException("电脑端版本不兼容，请先更新两端。")

    /** Does not change platform trust: only this socket uses a separately verified, short-lived certificate. */
    private class PairingTrust(private val pin: ByteArray?) : X509TrustManager {
        var fingerprint: ByteArray? = null
            private set

        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
            val cert = chain.singleOrNull() ?: throw CertificateException("配对证书无效")
            cert.checkValidity()
            if (cert.subjectX500Principal != cert.issuerX500Principal) throw CertificateException("配对证书无效")
            try { cert.verify(cert.publicKey) }
            catch (e: Exception) { throw CertificateException("配对证书无效", e) }
            val received = MessageDigest.getInstance("SHA-256").digest(cert.encoded)
            if (pin != null && !MessageDigest.isEqual(pin, received)) throw CertificateException("配对证书与二维码不符")
            fingerprint = received // Manual pairing requires the same verification number on both devices.
        }

        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) = throw CertificateException()
        override fun getAcceptedIssuers(): Array<X509Certificate> = emptyArray()
    }
}
