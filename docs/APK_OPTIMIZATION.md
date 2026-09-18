# APK 默认打包配置与瘦身记录

2026-09-18，以当时工作区代码重新构建基线，保留原有功能、依赖和源码。

## 改动

- Debug 和 Release 默认启用 R8（`isMinifyEnabled`）和资源裁剪（`isShrinkResources`），共用优化与保留规则，无需额外参数。
- 保留 `ImportSummary` 的类名和成员，保护 `SavedStateHandle` 序列化状态恢复。
- 保持现有签名、应用 ID、版本号、数据库结构和发送诊断逻辑。

## 同一份业务代码的实测结果

| 构建 | 字节数 | MiB |
| --- | ---: | ---: |
| 未优化基线 | 5,840,590 | 5.57 |
| 仅 R8 | 2,952,983 | 2.82 |
| R8 + 资源裁剪 | 2,866,783 | 2.73 |

最终减少 2,973,807 字节，约 50.9%。主要收益来自 DEX，资源裁剪额外减少 86,200 字节。

## 已验证

- 各阶段 `:app:assembleRelease :app:testReleaseUnitTest` 成功，Release 必要 Lint 检查通过。
- 92 项单元测试通过，无失败、错误或跳过；后续阶段复用了未变化的测试输入结果。
- 基线与最终 APK 的 `aapt dump xmltree ... AndroidManifest.xml` 输出一致。
- `apksigner verify --print-certs` 验证通过，基线与最终 APK 的签名证书相同。
- 在最终 APK 的 DEX 中核实了 28 个组件、自定义控件、数据库及状态类，无缺失。
- 合并的 R8 配置包含 Room、Glide 和 ViewModel 的依赖保留规则。
- R8 自动移除了未引用的 `MediaStoreImageLoader` 和 `CategorySwipeTouchListener`，未删除源码。

## 功能验收

用户已确认优化后的 Release 测试完成、没有问题，并要求将优化固化为默认打包配置。
以下保留为后续版本的回归清单。Debug 保留可调试属性和 `BuildConfig.DEBUG=true`，
与 Release 共用瘦身配置，但并非字节相同的安装包；上述 Release 验收不代表对新增优化 Debug 的真机验收。

- 覆盖安装：已有数据库、资源、分类、偏好设置及授权保持正常。
- 输入法：唤出、切换、分类翻页、搜索、最近使用、静态图和 GIF 发送。
- 发送兼容：QQ/微信等目标应用接收、失败转发、URI 访问和缓存清理。
- 悬浮球、边缘手势、无障碍服务和白名单。
- 导入导出、相册权限、分类管理、相似图片清理。
- 各设置页、自定义控件、深浅主题、导入页面进程重建后的状态恢复。

## 产物和回退

当次基线、仅 R8 和最终 APK 存于 `app/build/apk-optimization/`，目录内同时保存
体积数据、Manifest 对照、基线测试结果和最终 `optimized-mapping/` 混淆报告。
这些是被 Git 忽略的构建产物，执行 clean 前应另行留存；排查崩溃必须使用对应 APK 的 mapping。

默认打包命令（PowerShell，在项目根目录运行）：

```powershell
.\gradlew.bat :app:assembleDebug
.\gradlew.bat :app:assembleRelease
```

产物分别为 `app/build/outputs/apk/debug/app-debug.apk` 和
`app/build/outputs/apk/release/app-release.apk`。对应混淆报告分别位于
`app/build/outputs/mapping/debug/` 和 `app/build/outputs/mapping/release/`。

若后续真机回归异常，可先关闭 `isShrinkResources` 隔离资源问题；需要完整回退时同时将
`isMinifyEnabled` 与 `isShrinkResources` 设为 `false` 并重新构建。
