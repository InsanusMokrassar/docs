# FAQ

#### How oftern new versions are releasing?

Not very often. It depend on libraries (coroutines, korlibs/klock) updates and on some new awesome, but lightweight, features coming.

#### Where this library could be useful?

First of all, this library will be useful for long uptime applications which have some tasks to do from time to time.

#### How to use crontab-like syntax?

In two words, you should call `buildSchedule` or `createSimpleScheduler`:

```kotlin
buildSchedule("5 * * * *").asFlow().collect { /* do something */ }
```

You can read more about syntax in [String format](../describing/string-format.md) section.