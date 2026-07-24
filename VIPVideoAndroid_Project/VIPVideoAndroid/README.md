# 视频网址助手（Android）

这是根据原始 Tkinter Python 程序改写的原生 Android Java 应用。

## 功能
- 输入并打开合法视频网页网址
- 清空输入框
- 快速打开爱奇艺、腾讯视频、优酷
- 针对手机屏幕重新设计界面

## 合规调整
原 Python 文件中将视频地址拼接到第三方“VIP解析”网站的功能没有保留。Android 版只直接打开用户输入的网址，不提供会员破解、付费内容解析或访问控制绕过功能。

## 构建
使用 Android Studio 打开本目录，等待 Gradle 同步完成后运行或生成 APK。

- applicationId: `com.example.videolinkassistant`
- minSdk: 23
- targetSdk: 35
- version: 1.0.0
