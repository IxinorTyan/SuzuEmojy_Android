# 自家 IME 原发送路径的独立悬浮窗实验

入口：设置 → 其他设置 → 开启独立发送测试悬浮窗。
不需要 org.suzu.imeprobe。旧实验模块不再参与构建，主应用也不再绑定它。

## 两步对照

1. 在 QQ 测试聊天中开启我们自己的表情 IME，从独立悬浮窗展开图库、点击一张图片。
2. 在同一聊天切回平时使用的输入法，从同一个悬浮窗点击同一张图片。

两次均调用 ImageSender.executeH1b，图片准备、包名、Provider 和特化命令一致。
连接仅取自 TestImageIME.instance.currentInputConnection；无连接时直接报告并停止，不准备或授权图片。
不缓存已经结束的连接，不启动 IME，
不自动切换输入法，不走剪贴板、外部 IME 或分享兜底。
窗口不获取焦点。准备图片期间实例、输入会话或默认 IME 变化则取消操作，避免发向变化后的目标。
如连接存在，必须确认其 EditorInfo 属于 QQ；如连接不存在，记录 IC=NULL、未递交命令。

## 日志

TestPoC / StandaloneSendTest：点击、默认 IME、自家实例、输入是否开始、连接是否存在、会话编号。
TestPoC / ImageSender：同一 executeH1b 的命令返回、异常和事件 ID。
TestPoC / DiagnosticFileProvider：图片读取者的包名和 UID。

实验隔离旧 DEBUG 模拟开关，并禁用全部分享兜底；正式 IME 原有配置保持不变。
命令递交后立即允许下一次点击，五秒诊断在后台进行；旧事件的诊断不会覆盖新事件的界面。
每次实验生成独立的发送副本和 URI，清理仅作用于该次副本，不触碰资源原图或共享转换缓存。
窗口显示的“返回 true”不代表已发送，须结合 QQ 的 openFile 与聊天实际图片判断。

第一步用于验证“悬浮窗触发我们原有发送”能工作；第二步检验我们的 IME 未激活时这条通道是否仍可用。
若第二步实例或连接为空，说明当前未获得该通道，不能把它记录为 QQ 白名单拒绝。

## 自动化验证

本次只在电脑运行 OverlaySendAttemptTest、SendSnapshotTest，覆盖提交后立即继续点击、
旧回调不能解锁新准备任务、首次发送和重复发送副本独立、删除旧副本不影响源图或新副本。
不运行 connectedDebugAndroidTest，不在用户设备上执行自动安装/卸载测试。
这些单元测试不代替 QQ 实际发图验证。
