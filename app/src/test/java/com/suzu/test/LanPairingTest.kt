package com.suzu.test

import com.suzu.test.resource.lansync.LanEndpoint
import com.suzu.test.resource.lansync.LanPairing
import com.suzu.test.resource.lansync.LanSyncClient
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertThrows
import org.junit.Test

class LanPairingTest {
    @Test fun manualCodeCanDiscoverOrUseDirectAddress() {
        val discovery = LanPairing.manual(" 001234 ", "")
        assertEquals("001234", discovery.code)
        assertEquals(emptyList<LanEndpoint>(), discovery.addresses)
        assertNull(discovery.pin)
        assertEquals(listOf(LanEndpoint("192.168.1.2", 32001)), LanPairing.manual("001234", "192.168.1.2:32001").addresses)
        assertThrows(IllegalArgumentException::class.java) { LanPairing.manual("12345", "") }
        assertThrows(IllegalArgumentException::class.java) { LanPairing.manual("123456", "host:32001") }
        assertThrows(IllegalArgumentException::class.java) { LanPairing.manual("123456", "192.168.1.2:65536") }
    }

    @Test fun qrContainsCandidatesCodeAndCertificatePin() {
        val pin = ByteArray(32) { it.toByte() }
        val pairing = LanPairing.qr("suzu-lan:2|192.168.1.2:32001,10.0.0.1:32001|${pin.toHex()}|012345")
        assertEquals("012345", pairing.code)
        assertEquals(listOf(LanEndpoint("192.168.1.2", 32001), LanEndpoint("10.0.0.1", 32001)), pairing.addresses)
        assertArrayEquals(pin, pairing.pin)
        assertThrows(IllegalArgumentException::class.java) { LanPairing.qr("https://example.com") }
        assertThrows(IllegalArgumentException::class.java) { LanPairing.qr("suzu-lan:1|192.168.1.2:32001|${pin.toHex()}|012345") }
    }

    @Test fun verificationMatchesWindowsVector() {
        val pin = ByteArray(32) { it.toByte() }
        val client = pin.copyOfRange(0, 16)
        val server = pin.copyOfRange(16, 32)
        assertEquals("861136", LanSyncClient.verification(pin, client, server))
        assertEquals("7ffe81c74fe822411040290bd5572399577bffec5474b33a7973945177358cc0",
            LanSyncClient.commitment('C', pin, client).toHex())
    }

    private fun ByteArray.toHex() = joinToString("") { "%02x".format(it.toInt() and 255) }
}
