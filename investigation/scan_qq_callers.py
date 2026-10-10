import json
import struct
import zipfile
from pathlib import Path

root = Path(__file__).parent
output = root / 'qq_ime'
output.mkdir(exist_ok=True)
hits = []
with zipfile.ZipFile(root / 'apks/qq-9.3.55.apk') as apk:
    for name in apk.namelist():
        if not name.endswith('.dex'):
            continue
        data = apk.read(name)
        if not data.startswith(b'dex\n'):
            continue
        def u32(offset):
            return struct.unpack_from('<I', data, offset)[0]
        def uleb(offset):
            value = shift = 0
            while True:
                byte = data[offset]
                offset += 1
                value |= (byte & 127) << shift
                if byte < 128:
                    return value, offset
                shift += 7
        count, offset = struct.unpack_from('<II', data, 56)
        strings = []
        for i in range(count):
            _, pos = uleb(u32(offset + i * 4))
            strings.append(data[pos:data.index(0, pos)].decode('utf-8', 'replace'))
        needles = {}
        count, offset = struct.unpack_from('<II', data, 64)
        types = [strings[u32(offset + i * 4)] for i in range(count)]
        method_count, method_offset = struct.unpack_from('<II', data, 88)
        methods = []
        for mi in range(method_count):
            ci, pi, ni = struct.unpack_from('<HHI', data, method_offset + mi * 8)
            methods.append((types[ci], strings[ni]))
        needles = {i: c + '->' + m for i, (c, m) in enumerate(methods) if
            (c == 'Lcom/tencent/util/InputMethodUtil;' and m in ('checkSogouInputDefault', 'checkInputDefault')) or
            (c == 'Lcom/tencent/qqnt/aio/utils/aa;' and m == 'd') or
            m == 'sendNewExp'}
        if not needles:
            continue
        count, offset = struct.unpack_from('<II', data, 96)
        dex_hits = []
        for i in range(count):
            definition = offset + i * 32
            class_name = types[u32(definition)]
            pos = u32(definition + 24)
            if not pos:
                continue
            counts = []
            for _ in range(4):
                value, pos = uleb(pos)
                counts.append(value)
            for _ in range(counts[0] + counts[1]):
                _, pos = uleb(pos)
                _, pos = uleb(pos)
            for method_count in counts[2:]:
                method_index = 0
                for _ in range(method_count):
                    diff, pos = uleb(pos)
                    method_index += diff
                    _, pos = uleb(pos)
                    code_offset, pos = uleb(pos)
                    if not code_offset:
                        continue
                    start = code_offset + 16
                    end = start + 2 * u32(code_offset + 12)
                    found = set()
                    for instruction in range(start, end, 2):
                        op = data[instruction]
                        if op in (0x6e,0x6f,0x70,0x71,0x72,0x74,0x75,0x76,0x77,0x78) and instruction + 6 <= end:
                            index = struct.unpack_from('<H', data, instruction + 2)[0]
                        else:
                            continue
                        if index in needles:
                            found.add(needles[index])
                    if found:
                        method_name = strings[u32(method_offset + method_index * 8 + 4)]
                        dex_hits.append(dict(dex=name, cls=class_name, method=method_name, strings=sorted(found)))
        if dex_hits:
            (output / Path(name).name).write_bytes(data)
            hits.extend(dex_hits)
            for hit in dex_hits:
                print(json.dumps(hit, ensure_ascii=False), flush=True)
(output / 'callers.json').write_text(json.dumps(hits, ensure_ascii=False, indent=2), encoding='utf-8')
