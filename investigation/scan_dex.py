import zipfile, struct, re, json
from pathlib import Path

out = Path(__file__).parent
interesting = {'performPrivateCommand','onCreateInputConnection','commitContent','onCommitContent','onTextContextMenuItem','onReceiveContent'}
targets = {'RichEditText','PasteCareMentionEditText','ImeOptionsEmojiEditTextView','EmojiEditText','PasteTextEditText','ThirdPartyShareFriendsActivity','SystemShareNewActivity','OpenPlatformShareRealActivity','DmtEditText','KEmojiEditText','EmotionEditTextView'}
pattern = re.compile(r'sogou|EXP_PATH|SUPPORT.*EXPRESSION|baidu.*(commit|image|expression)|inputmethod.*(commit|image|expression)', re.I)
for apk in (out/'apks').glob('*.apk'):
    results=[]
    with zipfile.ZipFile(apk) as z:
        for name in z.namelist():
            if not re.fullmatch(r'classes\d*\.dex',name): continue
            b=z.read(name)
            def u32(p): return struct.unpack_from('<I',b,p)[0]
            def uleb(p):
                v=s=0
                while True:
                    c=b[p]; p+=1; v|=(c&127)<<s
                    if c<128: return v,p
                    s+=7
            ns,so=struct.unpack_from('<II',b,56)
            strings=[]
            for i in range(ns):
                p=u32(so+4*i); _,p=uleb(p)
                strings.append(b[p:b.index(0,p)].decode('utf-8','replace'))
            nt,to=struct.unpack_from('<II',b,64)
            types=[strings[u32(to+4*i)] for i in range(nt)]
            nc,co=struct.unpack_from('<II',b,96)
            selected=[types[u32(co+32*i)] for i in range(nc) if types[u32(co+32*i)].split('/')[-1].rstrip(';') in targets]
            if selected:
                dexdir=out/apk.stem
                dexdir.mkdir(exist_ok=True)
                (dexdir/name).write_bytes(b)
                print(apk.stem,name,selected,flush=True)
            nm,mo=struct.unpack_from('<II',b,88)
            methods=[]
            for i in range(nm):
                ci,pi,ni=struct.unpack_from('<HHI',b,mo+8*i)
                methods.append((types[ci], strings[ni]))
            hits=[{'class':c,'method':m} for c,m in methods if m in interesting]
            sh=[s for s in strings if pattern.search(s) and len(s)<500]
            results.append({'dex':name,'methods':hits,'strings':sh})
    (out/(apk.stem+'_index.json')).write_text(json.dumps(results,ensure_ascii=False,indent=2),encoding='utf-8')
    print(apk.stem, 'dex',len(results),'method hits',sum(len(r['methods']) for r in results),'string hits',sum(len(r['strings']) for r in results))
