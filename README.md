# SuzuEmojy (Android)

SuzuEmojy Android 是一个专注于本地表情包管理与快捷发送的轻量工具。

通过内置的表情键盘与管理面板，帮助用户高效整理、分类与发送个人表情资源。

---

## 主要特性

- **表情包分类与管理**：支持自定义分类、排序、多选导入与去重。
- **表情键盘面板**：提供轻量化虚拟输入法界面，无需反复切换应用即可快速浏览并选择表情。
- **悬浮呼出与快捷切换**：支持悬浮球与辅助服务联动，在需要发送表情时快速呼出面板。
- **本地化隐私保障**：资源与元数据均存储于本地数据库，无需网络上传。

---

## 构建与运行

### 1. 构建 Debug APK
确保根目录已存在 `local.properties` 并正确配置 Android SDK 路径，执行：

```bash
# Windows
.\gradlew.bat assembleDebug

# Linux / macOS
./gradlew assembleDebug
```

- **APK 产物路径**：`app/build/outputs/apk/debug/app-debug.apk`

### 2. 命令行安装
```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

---

## 已知限制

- 在部分社交客户端环境下，表情直接投递能力兼容性不稳定。受外部应用实现差异影响，该功能无法保证持续可用，未来可能随时失效。

- 本项目为独立自研软件，未获得第三方社交平台的相关合作授权。使用时请遵守对应平台用户协议，相关使用风险由使用者自行承担。

---

## 开源协议

本项目基于 [MIT License](LICENSE) 开源。
