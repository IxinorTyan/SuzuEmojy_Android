# SuzuEmojy (Android)

<div align="center">

<img src="ico.png" alt="SuzuEmojy Logo" width="120" height="120" />

### 高效、私密、原生的本地表情包管理与即时发送助手

[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Android](https://img.shields.io/badge/Platform-Android%208.0%2B%20(API%2026%2B)-green.svg)](https://developer.android.com)
[![Kotlin](https://img.shields.io/badge/Kotlin-1.9%2B-purple.svg)](https://kotlinlang.org)
[![Version](https://img.shields.io/badge/Version-v1.7.1-orange.svg)](https://github.com/IxinorTyan/SuzuEmojy_Android/releases)
[![Target SDK](https://img.shields.io/badge/Target%20SDK-34-brightgreen.svg)](https://developer.android.com)

[功能特性](#-功能特性) • [快速开始](#-快速开始) • [核心架构](#-技术架构) • [构建与编译](#-构建与运行) • [已知限制](#-已知限制与免责声明) • [交流反馈](#-交流与支持)

</div>

---

## 📖 项目简介

**SuzuEmojy Android** 是一款专为移动端聊天与社交场景打造的轻量级本地表情包管理与发送工具。

传统表情包管理往往面临“相册杂乱、发送步骤繁琐、云端隐私泄露、跨平台无法互通”等痛点。SuzuEmojy 通过**自建图片虚拟输入法（IME）**、**灵动悬浮球**与**边缘手势唤起**机制，让您在任意聊天界面下无需切换应用即可秒选、秒搜、秒发表情；配合高效的**感知哈希动静态去重清理**与 **PC 跨端格式互通**，为表情包重度爱好者提供丝滑无缝的使用体验。

---

## ✨ 功能特性

### ⌨️ 原生级表情键盘面板 (TestImageIME)
- **无缝打字协同**：作为系统虚拟输入法运行，点击即可快速将表情插入聊天，告别相册翻找与多步骤分享。
- **自定义分类导航**：支持分类 Tab 栏与快捷下拉折叠菜单，可在不同表情套系之间无缝滑动切换。
- **灵活视图定制**：支持键盘高度（120~400 dp）、网格列数（3~8 列）、Tab 图标大小自定义，随心调整单屏展示密度。
- **最近使用面板**：自动记录近期发送频次最高的表情，常用表情信手拈来。

### 🎈 多维度快捷唤起体系
- **灵动悬浮球**：
  - 支持随手指自由拖动贴边吸附，横竖屏分别记忆独立坐标。
  - 支持自定义贴图、形状（圆形、圆角矩形、无边框）、大小及透明度调节。
  - **键盘联动感知**：可配置仅在系统软键盘弹起时智能显现，收起软键盘时自动隐匿，界面干净清爽。
  - **应用白名单管控**：独家支持前台活动窗口白名单识别，仅在微信、QQ、TIM 等指定社交软件中呈现，在桌面、浏览器或系统界面静默隐藏。
- **边缘手势呼出**：
  - 支持屏幕左/右侧边缘手势交互，支持上下半区独立配置触发宽度与滑动距离。
  - 支持「上滑 / 下滑 / 内滑」多方向自定义动作：快速切换输入法、一键唤出悬浮搜索框或展开收藏夹。
- **全局浮动搜索条**：
  - 在当前聊天界面顶层直接弹出搜索框，支持标签与分类即时过滤。
  - 搜索结果点击直接发送，无需整体切换输入法，打字聊天更流畅。

### 🗂️ 全能表情资源库 (Library & Picker)
- **自建高性能媒体选择器**：无需依赖第三方图库，支持全屏滑动多选（Slide Selection）、大图缩放预览、动图播放。
- **精细化分类整理**：支持分类创建、重命名、拖拽排序、批量移动表情，分类封面可自定义图片或 Emoji。
- **多维属性筛选**：支持根据图片格式（GIF、PNG、JPG、WebP）、画面长宽比（横图/竖图/方图）、分辨率及自定义标签组合筛选。
- **一键原图清理**：导入表情至私有库后，可选自动安全删除系统相册中的原始文件，释放手机存储。

### 🔍 智能相似图片清理 (Similarity Cleanup)
- **双模感知哈希对比**：
  - **静态图**：基于 DCT 频域特征的 pHash 算法与汉明距离计算，精准识别不同分辨率、微小裁切或轻微色差的重复表情。
  - **动态图（GIF）**：独创多时间点等比采样与帧特征比对，智能识别加速、减速或循环重复的 GIF 动图。
- **BK-树算法加速**：告别全量 $O(n^2)$ 盲目比对，秒级完成海量表情特征聚类。
- **动态阈值与安全操作**：通过滑动条实时调节比对宽松度；结果以分组形式展示客观参数，绝不自动勾选、不强行代删，把控制权完全留给用户。
- **独立特征缓存**：在应用缓存区维护专用 SQLite 特征缓存，二次扫描无需重新解码。

### 🔄 跨端资源互通与备份 (Resource Package)
- **全格式双向互通**：完全兼容 Windows PC 端 SuzuEmojy 专有 ZIP 格式资源包（`format_version: 1`）。
- **细粒度导出支持**：支持导出全量资源包或单独导出勾选分类，完整封包关键词、自定义分类图标与元数据。
- **在线源一键导入**：支持从网页分享源（如 [suzuemojy-share.pages.dev](https://suzuemojy-share.pages.dev/)）下载资源包后快速导入，开箱即用。
- **严格去重保障**：像素级 MD5 / sync_key 双重防重逻辑，重复导入绝不生成垃圾冗余。

### 🔒 本地化隐私与稳定存储
- **全本地化运行**：所有表情资源与 SQLite 数据库均存放在 App 私有存储目录（`context.filesDir`），零数据上传、零网络依赖。
- **系统清理免疫**：与开放图库隔离，避免被第三方系统清理软件误删，保证元数据与物理文件的一致性。

---

## 🚀 快速开始

### 权限配置引导

为保证各功能的正常运作，首次安装启动后请根据向导完成基础授权：

| 权限项 | 权限用途 | 授权必要性 |
| :--- | :--- | :--- |
| **虚拟输入法** | 允许 SuzuEmojy 作为输入法被系统启用与选择 | **核心功能**（必需） |
| **照片和媒体读取** | 用于从系统相册选取并导入表情包图片 | **核心功能**（必需） |
| **显示在其他应用上层** | 悬浮球、边缘手势区域与浮动搜索框的呈现 | 悬浮相关功能必需 |
| **无障碍服务** | 用于自动感知宿主应用及键盘弹出状态，实现平滑切入切出与白名单过滤 | 悬浮球智能隐藏/白名单必需 |

> 💡 **提示**：无障碍服务仅在本地监听窗口状态变化与焦点包名，不采集任何击键内容或个人隐私数据。

---

## 🛠️ 构建与运行

### 编译环境要求
- **Android Studio**：Hedgehog (2023.1.1) 或更高版本
- **JDK**：Java 17
- **Android SDK**：Compile SDK 34，Min SDK 26 (Android 8.0+)
- **构建工具**：Gradle 8.x + KSP

### 命令行编译

确保项目根目录下存在 `local.properties` 并正确指定 `sdk.dir` 路径：

```bash
# Windows
.\gradlew.bat :app:assembleDebug
.\gradlew.bat :app:assembleRelease

# Linux / macOS
./gradlew :app:assembleDebug
./gradlew :app:assembleRelease
```

- **编译产物位置**：
  - Debug APK: `app/build/outputs/apk/debug/app-debug.apk`
  - Release APK: `app/build/outputs/apk/release/app-release.apk`
- Debug 与 Release 均已集成 R8 代码混淆与资源精简配置；混淆映射表位于 `app/build/outputs/mapping/`。

### ADB 快速安装
```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

### 运行单元测试
```bash
# Windows
.\gradlew.bat :app:testDebugUnitTest

# Linux / macOS
./gradlew :app:testDebugUnitTest
```

---

## 📐 技术架构

```
com.suzu.test
├── accessibility/   # 无障碍监听与前台宿主解析 (AccessibilityStateMonitor / BallForegroundResolver)
├── control/         # 富文本与 CommitContent 对照测试基准
├── db/              # Jetpack Room 核心数据库 (ResourceDao / CategoryDao / RecentDao)
├── floating/        # 悬浮球、边缘手势与浮动搜索框控制层
├── ime/             # 输入法服务、视图适配器、主题、多分支图片投递引擎 (ImageSender)
├── provider/        # 安全 FileProvider (DiagnosticFileProvider)
├── resource/        # 资源导入导出服务、ZIP 解析器、去重哈希工具
│   ├── exportpkg/   # 跨端规范 ZIP 导出
│   ├── importpkg/   # 跨端规范 ZIP 导入
│   └── similar/     # 相似图片清理 (DCT / pHash / BK-Tree / 动图时间采样)
├── storage/         # 临时缓存清理与私有存储管理
└── ui/              # 业务页面与 ViewModel
```

- **数据流与架构**：采用清晰的 MVVM 架构，全面基于 Kotlin Coroutines 与 `StateFlow` 实现响应式状态管理。
- **持久化层**：采用 Android Jetpack Room 统一管理本地资源与关系关联，保证 ACID 事务与级联操作可靠性。
- **图像管线**：Glide 4.x 进行高性能位图加载与内存缓存；在像素层通过 `copyPixelsToBuffer` 提取原始 RGBA 进行跨端无损 MD5 校验。
- **投递策略引擎**：内置 CommitContent 标准协议、搜狗私有命令兼容分支（H1β）、剪贴板自动注入与定向分享 Fallback，最大限度提高不同聊天客户端下的直发成功率。

---

## ⚠️ 已知限制与免责声明

1. **客户端兼容性说明**：
   - 表情的直接插入与发送受第三方社交客户端（如微信、QQ、TIM 等）各版本私有实现与富媒体协议规范的限制。
   - 部分平台可能由于自身输入框限制或协议更新而无法直接上屏，SuzuEmojy 会自动回退至剪贴板注入或系统分享机制。此能力无法保证永久在所有第三方应用各版本中均持续可用。
2. **免责声明**：
   - 本项目为独立个人/开源研制软件，未获得任何第三方社交平台的官方授权或合作。
   - 使用本项目过程中请严格遵守各相关平台的用户服务协议与法律法规，因使用本软件产生的一切相关后果及风险由使用者自行承担。

---

## 📄 开源协议

本项目遵循 [MIT License](LICENSE) 许可协议开源。

---

## 👥 交流与支持

- **开发者**：[IxinorTyan](https://github.com/IxinorTyan)
- **项目仓库**：[IxinorTyan/SuzuEmojy_Android](https://github.com/IxinorTyan/SuzuEmojy_Android)
- **表情资源分享站点**：[suzuemojy-share.pages.dev](https://suzuemojy-share.pages.dev/)
- **反馈与交流 QQ 群**：`834586488`
