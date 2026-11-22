# Android / Windows 资源包扩展

ZIP 仍使用 `format_version: 1`，基础 `manifest.json`、`catalog.json`、`assets/` 保持兼容。

## 导出范围与标签

manifest 新增 `export_scope`（`all` 或 `categories`）和 `includes_keywords`（布尔值）。只有“导出全部”写入资源的 `keywords` 字符串数组；选择收藏夹，即使全选，也省略该字段。旧包没有范围标记时继续读取已有 keywords。

导入新图片恢复标签；重复图片将包内标签追加到本地标签后，按完整标签保序去重。未携带标签不清除本地标签。用户标签使用既有 keywords 存储，Windows 的 tags 列不作为标签来源。

## 收藏夹图标

catalog.categories 的每项可增加 icon：

- 缺省或 `{"type":"default"}`：不覆盖本地图标。
- `{"type":"text","text":"🌸"}`：文字或 Emoji。
- `{"type":"image","asset_path":"icons/<sha256>.png","asset_size":123,"asset_sha256":"<sha256>"}`：独立图片附件。

图片附件保留原始文件，支持 PNG、GIF、JPEG、WebP、BMP。引用同一附件的收藏夹共享包内文件，附件不进入表情资源列表。即使原图片不在导出收藏夹中，也要携带图标。

manifest 的 `icon_assets`、`total_icon_bytes` 独立统计图标文件；旧 `counts.assets` 和 `total_asset_bytes` 仍只统计表情文件，使旧 Windows 校验器可继续读取基本资源。旧客户端忽略新增图标字段。

导入会恢复空收藏夹。同名收藏夹保留本地有效自定义图标，缺失或失效时才补充。附件检查路径、大小、SHA-256 和图像可解码性；图标异常单独警告，不阻止有效表情导入。

本地和包内都没有有效自定义图标时，在分类关系写入完成后，默认使用该分类当前显示顺序第一张图片作为图标，保存为 `res:<资源ID>`。包内图标缺失或损坏也适用此回退；空分类保持默认图标。合并到已有分类时按合并后的分类内排序取第一张，已有有效图标不会被覆盖。

Android 附件存放于 filesDir/category_icons，iconPath 使用 `file:category_icons/<filename>`；原 `res:` 和 `text:` 继续支持。Windows 附件存放于 data/category_icons，数据库和 JSON 保存 `category_icons/<filename>`，界面解析成绝对路径。

## 验证

Windows：在 Windows 项目目录运行 `python -m unittest discover -s tests -p test_exchange_package.py -v`。

Android：在 Android 项目目录运行 `gradlew.bat :app:compileDebugKotlin :app:testDebugUnitTest --offline`。设备验收还需检查两个方向的互导、收藏夹栏及键盘栏显示、重启持久化。

## 局域网同步

两端从「导入导出 → 局域网同步」进入。Windows 打开临时会话后，Android 扫码或输入 6 位配对码。输入配对码时先自动查找电脑；找不到可填 Windows 显示的 `IPv4:端口` 直连。两端分别核对并确认校验码后，任意一端都可发起发送或接收，另一端同意后才传输。双方同时发起时，一次只处理一个请求。Windows 防火墙需允许应用在受信任的专用网络上通信；访客 Wi-Fi 的客户端隔离也可能阻断直连。

每次传输复用「导出全部」的 v1 ZIP，接收方合并入库。重复图片不增加副本，收藏夹按名称合并，标签追加；不传播删除、重命名或排序。传输用 TLS，二维码包含临时证书指纹，手输配对码时需要比对两端校验码。资源包传输及展开大小均限制为 2 GiB，接收方在导入前校验完整 ZIP，Android 另校验每个图片附件的大小和哈希。自动发现只交换连接地址，配对后停止发现；关闭会话即停止对外监听。

局域网协议现为第 2 版，需要同时更新两端应用；资源包仍是原有 v1 ZIP。

设备回归：用 `gradlew.bat -PlanSyncTestOnly=true :app:assembleDebug :app:assembleDebugAndroidTest` 构建独立测试应用 `com.tencent.qqpinyin.suzu.lansynctest`，不会覆盖正常安装。Windows 测试程序运行 `--lan-android-fixture <临时输出目录>`，将输出的 `suzu-lan:2|…` 作为 instrumentation 参数 `lanFixture` 交给 `com.suzu.test.LanSyncWindowsTest`，可用 `lanMode=manual` 另跑手输直连模式。每次需要新建 Windows 测试会话。Windows 的 `--lan-sync-tests <临时输出目录>` 验证双端请求、拒绝、重复导入和损坏包；Android 的 `com.suzu.test.ExchangeUiTest` 验证入口、图标和多选。
