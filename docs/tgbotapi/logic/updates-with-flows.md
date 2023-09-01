# Updates with flows

Of course, in most cases here we will look up the way of using [utils extnsions](https://github.com/InsanusMokrassar/TelegramBotAPI/tree/master/tgbotapi.utils/), but you may read deeper about updates retrieving [here](https://bookstack.inmo.dev/books/telegrambotapi/chapter/updates-retrieving).

## Phylosophy of `Flow` updates retrieving

In most updates retrieving processes there are two components: [UpdatesFiler](https://github.com/InsanusMokrassar/TelegramBotAPI/blob/master/tgbotapi.core/src/commonMain/kotlin/dev/inmo/tgbotapi/updateshandlers/UpdatesFilter.kt) and its inheritor [FlowsUpdatesFilter](https://github.com/InsanusMokrassar/TelegramBotAPI/blob/master/tgbotapi.core/src/commonMain/kotlin/dev/inmo/tgbotapi/updateshandlers/FlowsUpdatesFilter.kt#L12). It is assumed, that you will do several things in your app to handle updates:

* Create your `UpdatesFilter` (for example, with [flowsUpdatesFilter](https://tgbotapi.inmo.dev/docs/dev.inmo.tgbotapi.extensions.utils.updates/flows-updates-filter.html) factory)
* Set it up (in case of `flowsUpdatesFilter` you will set up updates handling in the lambda passed to this factory)
* Provide updates to this filter with [filter#asUpdateReceiver](https://tgbotapi.inmo.dev/docs/dev.inmo.tgbotapi.updateshandlers/-updates-filter/as-update-receiver.html) object

Let's look how it works with the factory above:

```kotlin
// Step 1 - create filter
val filter = flowsUpdatesFilter {
  // Step 2 - set up handling. In this case we will print any message from group or user in console
  messageFlow.onEach {
    println(it)
  }.launchIn(someCoroutineScope)
}

// Step 3 - passing updates to filter
bot.getUpdates().forEach {
  filter.asUpdatesReceiver(it)
}
```

## Long polling

Some example with long polling has been described above. But it is more useful to use some factories for it. In this page we will look for simple variant with [TelegramBot#longPolling](https://tgbotapi.inmo.dev/docs/dev.inmo.tgbotapi.extensions.utils.updates.retrieving/long-polling.html). So, with this function, your handling of updates will looks like:

```kotlin
val bot = telegramBot("TOKEN")

bot.longPolling {
  messageFlow.onEach {
    println(it)
  }.launchIn(someCoroutineScope)
}.join()
```

This example looks like the example above with three steps, but there are several important things here:

* You **do not** manage retrieving of updates by hands
* `.join()` will suspend your function 😊 `longPolling` function returns `Job` and you may use it to:
  * `cancel` working of long polling (just call `job.cancel()`)
  * `join` and wait while the work of `longPolling` will not be completed (it will works infinity if you will not cancel it anywhere)
* [FlowsUpdatesFilter](https://tgbotapi.inmo.dev/docs/dev.inmo.tgbotapi.updateshandlers/-flows-updates-filter/index.html) has been created under the hood of `longPolling` function

## Results and `What is next?`

As a result you can start listen updates and react on it. Next recommended articles:

* [Behaviour Builder](https://docs.inmo.dev/tgbotapi/logic/behaviour-builder.html) as a variant of asynchronous handling of your bot logic
* [FSM variant of Behaviour Builder](https://bookstack.inmo.dev/books/telegrambotapi/page/behaviour-builder-with-fsm)
