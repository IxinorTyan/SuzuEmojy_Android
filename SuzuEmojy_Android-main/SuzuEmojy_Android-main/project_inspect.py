import os
import re

def inspect():
    print("=== File Tree ===")
    for root, dirs, files in os.walk("."):
        dirs[:] = [d for d in dirs if d not in [".git", ".gradle", ".idea", "build", "temp_gradle", "captures", "qqin", "sougou", "xunfei"]]
        level = root.replace("\\", "/").count('/')
        if level <= 4:
            indent = " " * 4 * level
            print(f"{indent}{os.path.basename(root)}/")
            subindent = " " * 4 * (level + 1)
            for f in files:
                print(f"{subindent}{f}")

    print("\n=== Search Keywords in app/src ===")
    patterns = {
        "performPrivateCommand": re.compile(r'performPrivateCommand'),
        "performAction": re.compile(r'performAction'),
        "commitContent": re.compile(r'commitContent'),
        "setPrimaryClip": re.compile(r'setPrimaryClip'),
        "ClipData": re.compile(r'ClipData'),
        "startDragAndDrop": re.compile(r'startDragAndDrop|startDrag'),
        "editorInfo_extras": re.compile(r'extras\.get|extras\[|SOGOU_EXPRESSION|SUPPORT_SOGOU_EXPRESSION|editorInfo\.extras', re.I)
    }
    
    matches = {k: [] for k in patterns}
    
    for root, _, files in os.walk("app/src"):
        for f in files:
            path = os.path.join(root, f).replace("\\", "/")
            try:
                with open(path, "r", encoding="utf-8", errors="ignore") as fp:
                    lines = fp.readlines()
                for idx, line in enumerate(lines, 1):
                    for k, pat in patterns.items():
                        if pat.search(line):
                            matches[k].append((path, idx, line.strip()))
            except Exception as e:
                pass
                
    for k, v in matches.items():
        print(f"\n--- {k} ({len(v)} matches) ---")
        for item in v:
            print(f"  {item[0]}:{item[1]} -> {item[2]}")

if __name__ == "__main__":
    inspect()
