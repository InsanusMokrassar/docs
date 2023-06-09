# Long polling

Long polling is a technology of getting updates for cases you do not have some dedicated server or you have no opportunity to receive updates via [webhooks](webhooks.html). More about this you can read in [wiki](https://en.wikipedia.org/wiki/Push_technology#Long_polling).

## Related topics

* [Updates filters](updates-filters.html)

## Long polling in this library

There are a lot of ways to include work with long polling:

* `RequestsExecutor#longPollingFlow` Is the base way to get all updates cold Flow. Remember, that __this flow will not be launched automatically__
	* `RequestsExecutor#startGettingOfUpdatesByLongPolling` Old and almost deprecated way
    * `RequestsExecutor#longPolling` Works like `startGettingOfUpdatesByLongPolling` but shorted in a name :)
* `RequestsExecutor#createAccumulatedUpdatesRetrieverFlow` Works like `longPollingFlow`, but flow inside will return only the updates accumulated at the moment of calls (all new updates will not be passed throw this flow)
    * `RequestsExecutor#retrieveAccumulatedUpdates` Use `createAccumulatedUpdatesRetrieverFlow` to perform all accumulated updates
    * `RequestsExecutor#flushAccumulatedUpdates` Works like `retrieveAccumulatedUpdates` but perform all updates directly in a place of calling
* By yourself with `GetUpdates` request or `RequestsExecutor#getUpdates` extension

### longPolling

`longPolling` is a simple way to start getting updates and work with bot:

```kotlin
val bot = telegramBot(token)
bot.longPolling(
  textMessages().subscribe(scope) { // here "scope" is a CoroutineScope
    println(it) // will be printed each update from chats with messages
  }
)
```

### startGettingOfUpdatesByLongPolling

The main aim of `startGettingOfUpdatesByLongPolling` extension was to provide more simple way to get updates in automatic mode:

```kotlin
val bot = telegramBot(token)
bot.startGettingOfUpdatesByLongPolling(
  {
    println(it) // will be printed each update from chats with messages
  }
)
```

The other way is to use the most basic `startGettingOfUpdatesByLongPolling` extension:

```kotlin
val bot = telegramBot(token)
bot.startGettingOfUpdatesByLongPolling {
  println(it) // will be printed each update
}
```

## See also

* [Webhooks](webhooks.html)
* [Updates filters](updates-filters.html)