# KrontabScheduler

`KronScheduler` is the simple interface with only one function `next`. This function optionally get as a parameter `DateTime` which will be used as start point for the calculation of next trigger time. This function will return the next `DateTime` when something must happen.

### Default realisation

Default realisation (`CronDateTimeScheduler`) can be created using several ways:

* Via `buildSchedule` (or `createSimpleScheduler`) functions with crontab-like syntax parameter
* Via `buildSchedule` (or `SchedulerBuilder` object), which using lambda to configure scheduler

In the examples below the result of created scheduler will be the same.

#### Crontab-like way

> NOTE: **Crontab-like syntax**
> See [String format](string-format.md) for more info about the crontab-line syntax

This way will be very useful for cases when you need to configure something via external configuration (from file on startup or via some parameter from requests, for example):

```kotlin
val schedule = "5 * * * *"
val scheduler = buildSchedule(schedule)

scheduler.asFlow().onEach {
  // this block will be called every minute at 5 seconds
}.launchIn(someCoroutineScope)
```

#### Lambda way

In case of usage builder (lets call it `lambda way`), you will be able to configure scheduler in more type-safe way:

```kotlin
val scheduler = buildSchedule {
  seconds {
    at(5)
  }
}

scheduler.asFlow().onEach {
  // this block will be called every minute at 5 seconds
}.launchIn(someCoroutineScope)
```

### Custom scheduler

You are always able to use your own realisation of scheduler. For example:

```kotlin
class RandomScheduler : KronScheduler {
  override suspend fun next(relatively: DateTime): DateTime {
    return relatively + DateTimeSpan(seconds = Random.nextInt() % 60)
  }
}
```

In the example above we have created `RandomScheduler`, which will return random next time in range `0-60` seconds since `relatively` argument.