# QQ 9.3.55 原 IME 发图通路核查

## 用户要求

保持其他输入法选中，由本应用独立悬浮窗使用已有特化途径向当前 QQ 聊天发图；不切换至本应用 IME，不要求其他键盘改包名。

## 已核实的静态调用链

来源为用户设备已安装的 QQ 9.3.55 APK，仅只读提取分析。

1. `AIOEditTextInitializeVBDelegate.java:257` 注册 `setOnPrivateIMECommandListener`。
2. 同文件 `h()`（116–129 行）先调用 `InputMethodUtil.checkSogouInputDefault(context)`，仅通过时派发内部 `h.h` 消息。**检查失败也返回 true**。
3. `InputMethodUtil.java:80` 实际读取系统 `default_input_method`，对其做字符串 contains 检查：`com.sohu.inputmethod.sogou`、`com.tencent.qqpinyin`、`com.sogou.zhuyininput`。不是对图片 URI 的 provider 包名或我们的调用代码包名做这项检查。`suzu` 不参与匹配。
4. `CommandIntentHandler.java:253` 起处理该内部消息，再调用 `aa.d(...)`。这里的 handleIntent 接收的是 QQ 内部 MVI 消息，并非 Android 广播 Intent，不能直接用 sendBroadcast 进入。
5. `QQImageCommand.java:d()` 分发 `com.sogou.inputmethod.exp.commit` 到 `c()`；`c()`（530 行）取 `EXP_PATH_URI`、查询 `_display_name`，调用 `ISoGouEmoApi.sendNewExp`，同时传入当前 QQ 内部聊天上下文。

## 对实验的影响

- 既有发送包含两个不同条件：取得当前聊天输入连接来递交命令，以及 QQ 检查当前选中输入法属于支持名单。单独保留本应用包名和 Provider，不能替代它们。
- 选中搜狗时，以上静态白名单本身可以通过，但本应用仍没有搜狗持有的 QQ InputConnection。合作键盘可以用自己的有效连接转发命令；那是新增合作接口，不是直接调用未激活的本应用 IME。
- 选中不匹配名单的开源键盘时，即使该键盘转发命令，此接收分支也会忽略。图片来自 `com.tencent.qqpinyin.suzu.provider` 无法改变该检查结果。
- 原有独立悬浮窗仅取 `TestImageIME.instance.currentInputConnection`，无法满足其他键盘保持选中的目标。重复准备图片、修改 URI 或维持 IME 对象存活均未解决通路归属。
- 返回 true 不证明 QQ 接受了图片发送。Provider 被读取也不等同于消息最终发送成功。

## 结论范围

普通应用权限下，不能通过“调用我们原有发送函数”直接复用未激活 IME 的当前聊天通路。这是本条已追踪路径的限制，不是已证明 QQ 所有外部入口都不存在。尚未找到可供本应用独立调用、且复用当前聊天上下文的外部 IPC 入口，不能承诺已有可用修复。

反编译结果含混淆和反编译警告；QQ 有运行时热修复机制。这里的结论针对 APK 中确认的分支，不泛化至微信或所有 QQ 版本。

本轮未安装、卸载、清数据、切换输入法或操作设备 UI；未修改正常发图实现。
