# How to use

## Previous pages

* [Including in project](including-in-project.md)

## `buildSchedule`

> NOTE: **Custom KronScheduler**
> 
> You are always may create your own scheduler. In this section will be presented different ways and examples around standard `CronDateTimeScheduler` builders `buildSchedule`. You can read about schedulers in [KrontabScheduler](../describing/krontabscheduler.md)

Currently, `buildSchedule` is the recommended start point for every scheduler. Usually, it is look like:

```kotlin
val scheduler = buildSchedule("5 * * * *")
```

Or:

```kotlin
val scheduler = buildSchedule {
  seconds {
    at(5)
  }
}
```

On the top of any `KronScheduler` currently there are several groups of extensions:

* Executes
* Shortcuts
* Flows

### Executes

All executes are look like `do...`. All executes are described below:

* `doOnce` - will get the next time for executing, delay until that time and call `block` with returning of the `block` result
* `doWhile` - will call `doOnce` while it will return `true` (that means that `block` must return `true` if it expects that next call must happen). In two words: it will run while `block` returning `true`
* `doInfinity` - will call the `block` using `doWhile` with predefined returning `true`. In two words: it will call `block` while it do not throw error

### Shortcuts

Shortcuts are the constants that are initializing in a lazy way to provide preset `KronScheduler`s. For more info about `KrontabScheduler` you can read its own [page](../describing/krontabscheduler.md).

* `AnyTimeScheduler` - will always return incoming `DateTime` as next
* `Every*Scheduler` - return near * since the passed `relatively`:
  * `EverySecondScheduler` / `KronScheduler.everyMillisecond`
  * `EverySecondScheduler` / `KronScheduler.everySecond`
  * `EveryMinuteScheduler` / `KronScheduler.everyMinute`
  * `EveryHourScheduler` / `KronScheduler.hourly`
  * `EveryDayOfMonthScheduler` / `KronScheduler.daily`
  * `EveryMonthScheduler` / `KronScheduler.monthly`
  * `EveryYearScheduler` / `KronScheduler.annually`

### Flows

Here currently there is only one extension for `KronScheduler`: `KronScheduler#asFlow`. As a result you will get `Flow<DateTime>` (in fact `SchedulerFlow`) which will trigger next `emit` on each not null `next` `DateTime`
