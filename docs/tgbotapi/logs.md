# Logging

In this library we are using [KSLog](/kslog) for logging of events in telegram bots. There are several ways to set it
up and configure.

```kotlin
// (1)
setDefaultKSLog(
    KSLog { level: LogLevel, tag: String?, message: Any, throwable: Throwable? ->
        println(defaultMessageFormatter(level, tag, message, throwable))
    }
)

// (2)
DefaultKTgBotAPIKSLog = KSLog { level: LogLevel, tag: String?, message: Any, throwable: Throwable? ->
    println(defaultMessageFormatter(level, tag, message, throwable))
}

// (3)
val bot = telegramBot(YOUR_TOKEN) {
    logger = KSLog { level: LogLevel, tag: String?, message: Any, throwable: Throwable? ->
        println(defaultMessageFormatter(level, tag, message, throwable))
    }
}
```

1. This variant will set __GLOBAL DEFAULT__ logger. For example, if you will use somewhere `TagLogger`, it will use
`KSLog.default` under the hood
2. All the bots created after this setup will use new logger until more specified logger configured (see below)
3. Passing of `logger` variable to the `KtorRequestsExecutor` or one of factories
`telegrabBot` will lead to granular setup of logging
