# 抖音、快手外部图片发送静态排查（2026-09-25）

## 结论

当前会话内通过第三方输入法直接发送任意本地图片：未找到可接入的实现，不建议直接进入开发。不能据静态排查宣布绝对不可能。

外部图片进入应用自己的私信分享面板：两款应用均有明确实现，值得作为独立的小范围验证方向；不等于当前会话直发，也没有验证 GIF 动画保留。

## 范围

- 来自已连接手机的已安装 APK：抖音 36.2.0，快手 14.8.30.50423。
- 扫描抖音 56 个、快手 31 个顶层 DEX 的字符串和方法引用；用 JADX 1.5.6 定向反编译关键类。
- 未修改产品代码，未安装新版本，未启动分享或发送消息。
- APK、选取的 DEX 和反编译文件保存在本目录，总计约 637 MB（十进制）；不是项目依赖。
- 不覆盖动态下发代码、运行时热补丁、native 实现及所有页面；未通过运行时追踪确认用户当时输入框的确切类。

## 当前会话输入法路径

### 抖音

- `douyin/RichEditText.java:407`：聊天富文本输入框创建 InputConnectionWrapper，覆写的是删除及按键处理，没有图片接收覆写。
- `douyin/DmtEditText.java:56`：粘贴交给 AppCompatEditText，随后通知监听器；此处没有图片消费逻辑。
- `douyin/PasteCareMentionEditText.java:20`：同样委托父类粘贴。此类属于回复页面，不能直接当作所有聊天页面的实现。
- `douyin/PrivateCommandWrapper.java:33`：找到的私有命令处理是 AndroidX/support 的 COMMIT_CONTENT 兼容实现，并非抖音私有表情协议；库代码存在不意味着当前输入框已接入。
- `douyin/InputConnection0BKa.java:516`：另一处 InputConnection 的 commitContent 返回 false；其使用 Compose TextFieldValue，不能据此代表全部聊天页面。

### 快手

- `kuaishou/ImeOptionsEmojiEditTextView.java:97` 附近：调整回车发送选项，再包装删除操作，没有图片接收实现。
- `kuaishou/EmojiEditText.java:278`：输入连接委托父类或删除键包装器。
- `kuaishou/EmojiEditText.java:306`：粘贴通知监听器后调用父类，监听器实现未穷尽追踪。
- `kuaishou/InputConnectionWrapper.java`：只处理 deleteSurroundingText。
- 全部顶层 DEX 的方法引用索引中，未找到 commitContent / performPrivateCommand / onReceiveContent 的相应接收方法引用。

与此前日志相符：当前输入框 contentMimeTypes 为空、无搜狗表情能力标记，Provider 访问仅来自 Suzu 自检，未看到目标应用打开图片。

## 分享路径

### 抖音

`douyin/SystemShareNewActivity.java:206` 起设置 OPEN_SYSTEM_IM 来源，通过 SharePipeline 处理外部内容；约第 234 行创建 SystemSearchShareFragment，第 507 行起整理图片/视频路径，交给私信分享界面。

这是外部内容进入私信分享 UI 的实际代码证据。没有找到从该入口自动取得“用户当前聊天对象”并直接发送的依据。

### 快手

- `kuaishou/ThirdPartyShareFriendsActivity.java:41`：受 shareFromSystemPhotoAlbumToMessage 配置、青少年限制、存储权限和登录状态影响。
- `kuaishou/ShareHandler.java:80`：读取 ACTION_SEND、MIME 和 EXTRA_STREAM。
- 同文件第 211 行：图片转换为 IMShareOuterImageObject；第 219 行创建 IMShareRequest，初始接收者列表为空，随后进入分享服务。
- `kuaishou/ThirdPartyShareContentType.java`：MIME 层以 image/ 前缀归类，image/gif 在此层不被专门排除；后续仍依赖文件扩展名和解码。
- `kuaishou/ShareContentResolver.java:49`：需要 URI 转可用文件路径，有备用解析器；第 94 行使用 BitmapFactory.decodeStream 获取图片信息。没有验证备用解析器是否兼容 Suzu FileProvider，也不能据 BitmapFactory 的存在断言最终 GIF 必然静止。
- `kuaishou/ShareHandler.java:55`：当 ACTION_SEND 的 ClipData URI 同时包含 baidu 和 input 时，此版本静态逻辑会走拒绝分支。该规则仅属于此分享入口，不能推断其原因或扩大为全应用行为。

## 建议的停止点和后续最小实验

1. 当前输入法直发路线先停，不做包名猜测、命令遍历或大规模逆向。没有接收实现时，发送端改参数无法补出接收能力。
2. 若接受选择好友确认发送，可验证上述定向分享入口；先 PNG，再原始 GIF，检查是否读取 URI、能否预览、是否保持动画及是否发送为图片/表情。
3. 若只能接受当前会话一键直发，进一步工作将涉及运行时追踪、监听器调用链和可能的内部会话/发送实现，成本显著高于一般兼容改动，当前证据不足以支持投入。
4. 可考虑相册中转和界面辅助操作，但属于另一种交互方案，并非发现了隐藏输入法协议。
