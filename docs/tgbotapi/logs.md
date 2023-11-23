# Logging

In this library we are using [KSLog](/kslog) for logging of events in telegram bots. There are several ways to set it
up and configure.

* Globally, you may change [DefaultKTgBotAPIKSLog]() logger __BEFORE__ creating of your bots
* Granular, you may configure and pass `logger` variable to the `KtorRequestsExecutor`

Besides, you always may pass your logger to the bot via `KtorRequestsExecutorBuilder`:

```kotlin
val bot = telegramBot(YOUR_TOKEN) {
    logger = KSLog("SomeLogger")
}
```
