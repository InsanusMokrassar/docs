# Setup

## Dependency installation

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/dev.inmo/kslog/badge.svg)](https://maven-badges.herokuapp.com/maven-central/dev.inmo/kslog)

### Gradle (Groovy)

```groovy
implementation "dev.inmo:kslog:$kslog_version"
```

### Gradle (Kotlin Script)

```kotlin
implementation("dev.inmo:kslog:$kslog_version")
```

### Maven (pom)

```xml
<dependency>
  <groupId>dev.inmo</groupId>
  <artifactId>kslog</artifactId>
  <version>${kslog_version}</version>
</dependency>
```

## Setup in code

The main point in setup in your code is to setup default logger:

```kotlin
KSLog.default = KSLog("defaultTag")
```

You may use custom `messageFormatter` in any of `KSLog` factory to customize output of `KSLog` logging. For example:

```kotlin
KSLog(
  "loggingWithCustomFormat",
  messageFormatter = { level, tag, message, throwable ->
    println("[$level] $tag - $message: $throwable")
  }
)
```

Additionally you may use one of several different settings:

* `minLoggingLevel` - minimal logging level for the log which will be logged. The order of log level is next:
  * DEBUG
  * VERBOSE
  * INFO
  * WARNING
  * ERROR
  * ASSERT
* `levels` - and iterable with the levels which should be logged
* `firstLevel`,`secondLevel`,`otherLevels` - as `levels`, but `vararg` :)

In case you are passing `minLoggingLevel`, the **level and more important levels** will be passed to logs. For example, when you are settings up your logger as in next snippet:

```kotlin
val logger = KSLog(
    "exampleTag",
    minLoggingLevel = LogLevel.INFO
)
```

The next levels will be logged with `logger`:

* `INFO`
* `WARNING`
* `ERROR`
* `ASSERT`

## Special loggers

### CallbackKSLog

It is logger which will call incoming `performLogCallback` on each logging. This logger can be create simply with one callback:

```kotlin
KSLog { level, tag, message, throwable ->
  println("[$level] $tag - $message: $throwable")
}
```

### TagLogger

It is simple value class which can be used for zero-cost usage of some tag and calling for `KSLog.default`. For example, if you will create tag logger with next code:

```kotlin
val logger = TagLogger("tagLoggerTag")
```

The `logger` will call `KSLog.default` with the tag `tagLoggerTag` on each calling of logging.

### FilterKSLog

This pretty simple logger will call its `fallbackLogger` only in cases when incoming `messageFilter` will return true for logging:

```kotlin
val baseLogger = KSLog("base") // log everything with the tag `base` if not set other
val filtered = baseLogger.filtered { _, t, _ ->
    t == "base"
}
```

In the example above `baseLogger` will perform logs in two ways: when it has been called directly or when we call log performing with the tag `"base"` or `null`. Besides, you can see there extension `filtered` which allow to create `FilterKSLog` logger with simple lambda.

### TypedKSLog

This logger accepts map of types with the target loggers. You may build this logger with the special simple DSL:

```kotlin
val baseLogger = KSLog("base") // log everything with the tag `base` if not set other
val typed = buildTypedLogger {
  on<Int>(baseLogger) // log all ints to the baseLogger
  on<Float> { _, _, message, _ ->// log all floats to the passed logger
  	println(message.toString()) // just print all floats
  }
  default { level, tag, message, throwable ->
    KSLog.performLog(level, tag, message, throwable)
  }
}
```

### Automatical loggers

There are two things which can be useful in your code: `logger` and `logTag` extensions. `logTag` is the autocalculated by your object classname tag. `logger` extension can be used with applying to any object like in the next snippet:

```kotlin
class SomeClass {
  init {
    logger.i("inited")
  }
}
```

The code above will trigger calling of logging in `KSLog.default` with level `LogLevel.INFO` using tag `SomeClass` and message `"inited"`. As you could have guessed, `logger` is using `TagLogger` with `logTag` underhood and the most expensive operation here is automatical calculation of `logTag`.

* Extension `logger`

## JVM specific setup

For JVM you may setup additionally use java loggers as the second parameter of `KSLog` factory. For example:

```kotlin
KSLog(
  "yourTag"
  Logger.getLogger("YourJavaLoggerName")
)
```