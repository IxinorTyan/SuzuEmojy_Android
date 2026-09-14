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

Android 附件存放于 filesDir/category_icons，iconPath 使用 `file:category_icons/<filename>`；原 `res:` 和 `text:` 继续支持。Windows 附件存放于 data/category_icons，数据库和 JSON 保存 `category_icons/<filename>`，界面解析成绝对路径。

## 验证

Windows：在 Windows 项目目录运行 `python -m unittest discover -s tests -p test_exchange_package.py -v`。

Android：在 Android 项目目录运行 `gradlew.bat :app:compileDebugKotlin :app:testDebugUnitTest --offline`。设备验收还需检查两个方向的互导、收藏夹栏及键盘栏显示、重启持久化。
