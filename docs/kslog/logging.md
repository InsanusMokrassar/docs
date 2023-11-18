# Logging

> NOTE: **Message type notice**
> On this page all the messages will be just simple `String`, but you may pass any object as the message

As has been said in the [setup](setup.md) section, this library contains next levels of logging with their default representations on each platform:

| Weight (by order) | LogLevel name |      JS       |  JVM Loggers  | Android |
| -: |:-------------:|:-------------:|:-------------:|:-------:|
| 0 |     TRACE     |  console.trace + console.debug  | Level.FINEST  |  Log.d  |
| 1 |     DEBUG     |  console.debug  | Level.FINER  |  Log.d  |
| 2 |    VERBOSE    | console.info  |  Level.FINE   |  Log.v  |
| 3 |     INFO      | console.info  |  Level.INFO   |  Log.i  |
| 4 |    WARNING    | console.warn  | Level.WARNING |  Log.w  |
| 5 |     ERROR     | console.error | Level.SEVERE  |  Log.e  |
| 6 |    ASSERT     | console.error | Level.SEVERE  | Log.wtf |

Each of these levels have fullname and shortname shortcat extensions:

* `KSLog.trace`/`KSLog.t`/`KSLog.tS`
* `KSLog.debug`/`KSLog.d`/`KSLog.dS`
* `KSLog.verbose`/`KSLog.v`/`KSLog.vS`
* `KSLog.info`/`KSLog.i`/`KSLog.iS`
* `KSLog.warning`/`KSLog.w`/`KSLog.wS`
* `KSLog.error`/`KSLog.e`/`KSLog.eS`
* `KSLog.assert`/`KSLog.wtf`/`KSLog.wtfS`

And any of these shortcuts may accept one of several arguments combinations:

* Tag (Optional), Throwable (Optional), Message Builder (simple inline callback for lazy creating of log message). This type of arguments is duplicated with `S` suffix for `suspendable` messages creating, for example
* Message, Throwable (Optional)
* Tag, Message, Throwable (Optional)

So, when you want to log some expected exception, there are three common ways to do it:

```kotlin
val logger = KSLog.default

// with callback
logger.info(tag, throwable) {
  "Some your message for this event"
}

// with suspendable callback
logger.infoS(tag, throwable) {
  withContext(Dispatchers.Default) {
    "Some your message for this event"
  }
}

// Just with message
logger.info("Some your message for this event", throwable)

// With message and tag as strings
logger.info(tag, "Some your message for this event", throwable)
```

Of course, any of this calls can be shortenned:

```kotlin
val logger = KSLog.default

// with callback
logger.i(tag, throwable) {
  "Some your message for this event"
}

// with suspendable callback
logger.iS(tag, throwable) {
  withContext(Dispatchers.Default) {
    "Some your message for this event"
  }
}

// Just with message
logger.i("Some your message for this event", throwable)

// With message and tag as strings
logger.i(tag, "Some your message for this event", throwable)
```

There is special shortcat - for base `performLog`. In that case the only change is that you will require to pass the `LogLevel` more obviously:

```kotlin
val logger = KSLog.default

// with callback
logger.log(LogLevel.INFO, tag, throwable) {
  "Some your message for this event"
}

// with suspendable callback
logger.logS(LogLevel.INFO, tag, throwable) {
  withContext(Dispatchers.Default) {
    "Some your message for this event"
  }
}

// Just with message
logger.log(LogLevel.INFO, "Some your message for this event", throwable)

// With message and tag as strings
logger.log(LogLevel.INFO, tag, "Some your message for this event", throwable)
```

OR

```kotlin
val logger = KSLog.default

// with callback
logger.l(LogLevel.INFO, tag, throwable) {
  "Some your message for this event"
}

// with suspendable callback
logger.lS(LogLevel.INFO, tag, throwable) {
  withContext(Dispatchers.Default) {
    "Some your message for this event"
  }
}

// Just with message
logger.l(LogLevel.INFO, "Some your message for this event", throwable)

// With message and tag as strings
logger.l(LogLevel.INFO, tag, "Some your message for this event", throwable)
```
