import os
import re

def check():
    ime_file = "app/src/main/java/com/suzu/test/ime/TestImageIME.kt"
    with open(ime_file, "r", encoding="utf-8") as f:
        content = f.read()

    print("=== ① 检查自动切回方法是否只出现在 exitAndRestoreIme 中 ===")
    methods = ["switchToPreviousInputMethod", "restorePreviousIme", "requestHideSelf", "finishInput"]
    for m in methods:
        matches = [m_match.start() for m_match in re.finditer(m, content)]
        print(f"  {m} 出现次数: {len(matches)}")

    print("\n=== ② 检查微信 arm 是否在 setPrimaryClip 之前 ===")
    arm_pos = content.find("service.armWeChatAutoSend")
    clip_pos = content.find("clipboard?.setPrimaryClip(clip)")
    print(f"  armWeChatAutoSend 首次位置: {arm_pos}, setPrimaryClip 首次位置: {clip_pos}, arm在前: {arm_pos < clip_pos}")

    print("\n=== ③ 检查全库 'com.tencent.mobileqq' 硬编码 ===")
    qq_matches = []
    for root, _, files in os.walk("app/src"):
        for file in files:
            if file.endswith(".kt") or file.endswith(".java") or file.endswith(".xml"):
                path = os.path.join(root, file).replace("\\", "/")
                with open(path, "r", encoding="utf-8", errors="ignore") as fp:
                    for idx, line in enumerate(fp, 1):
                        if "com.tencent.mobileqq" in line:
                            qq_matches.append((path, idx, line.strip()))
    for q in qq_matches:
        print(f"  {q[0]}:{q[1]} -> {q[2]}")

if __name__ == "__main__":
    check()
