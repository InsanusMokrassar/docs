# Behaviour Builder

In the previous pages about [updates handling](https://bookstack.inmo.dev/books/telegrambotapi/page/updates-with-flows) and was mentioned that currently in the most cases you should use [Flow](https://kotlinlang.org/docs/flow.html)s. So, there is an improvement for that system which hide direct work with flows and allow you to create more declarative logic of your bot.

## Main parts of Behaviour Builder

There are several things you should know for better understanding of behaviour builder:

* [BehaviourContext](https://github.com/InsanusMokrassar/TelegramBotAPI/blob/master/tgbotapi.behaviour_builder/src/commonMain/kotlin/dev/inmo/tgbotapi/extensions/behaviour_builder/BehaviourContext.kt#L34) - it is the thing which contains all necessary tools for working with bots
* [Triggers](https://tgbotapi.inmo.dev/tgbotapi.behaviour_builder/dev.inmo.tgbotapi.extensions.behaviour_builder.triggers_handling/index.html) - `on*` extensions for `BehaviourContext` which allow you to create reaction on some update
* [Expectations](https://tgbotapi.inmo.dev/tgbotapi.behaviour_builder/dev.inmo.tgbotapi.extensions.behaviour_builder.expectations/index.html) (or waiters) - `wait*` extensions which you **may** use in [buildBehaviour](https://tgbotapi.inmo.dev/docs/dev.inmo.tgbotapi.extensions.behaviour_builder/build-behaviour.html) function, but ***it is recommended*** to use it in bodies of triggers

## Initialization

As was said above, there is [buildBehaviour](https://tgbotapi.inmo.dev/docs/dev.inmo.tgbotapi.extensions.behaviour_builder/build-behaviour.html) function which allow you set up your bot logic. Let's see an example:

```kotlin
val bot = telegramBot("TOKEN")

bot.buildBehaviour {
  onCommand("start") { // creating of trigger
    val message = it
    val content = message.content

    reply(message, "Ok, send me one photo") // send text message with replying on incoming message

    val photoContent = waitPhoto().first() // waitPhoto will return List, so, get first element

    val photo = downloadFile(photoContent) // ByteArray of photo

    // some logic with saving of photos
  }
}
```

## Filters

In most cases there are opportunity to filter some of messages before starting of main logic. Let's look at this using the example above:

```kotlin
val bot = telegramBot("TOKEN")

bot.buildBehaviour {
  onCommand(
    "start",
    initialFilter = {
      it.content.textSources.size == 1 // make sure that user has sent /start without any additions
    }
  ) {
    // ...
  }
}
```

OR

```kotlin
val bot = telegramBot("TOKEN")

bot.buildBehaviour {
  onCommand(
    "start",
    requireOnlyCommandInMessage = true // it is default, but you can overwrite it with `requireOnlyCommandInMessage = false`
  ) {
    // ...
  }
}
```

## Long polling

In case you wish to start long polling, you have two options:

* Start long polling with `buildBehaviour` additional functions
* Start long polling from using the result of `buildBehaviour`

> NOTE: **More info**
>
> More info about updates retrieving you may read at the [Long Polling](../updates/long-polling.md) and [Webhooks](../updates/webhooks.md)
> articles


### Long polling from `buildBehaviour`

With prepared bot:

```kotlin
val bot = telegramBot("TOKEN")

bot.buildBehaviourWithLongPolling {
    onCommand(
        "start",
        requireOnlyCommandInMessage = true // it is default, but you can overwrite it with `requireOnlyCommandInMessage = false`
    ) {
        // ...
    }
}
```

Without prepared bot:

```kotlin
telegramBotWithBehaviourAndLongPolling("TOKEN") {
    onCommand(
        "start",
        requireOnlyCommandInMessage = true // it is default, but you can overwrite it with `requireOnlyCommandInMessage = false`
    ) {
        // ...
    }
}
```

### Long polling from the result of behaviour building

```kotlin
val bot = telegramBot("TOKEN")
val rootContext = bot.buildBehaviour {
    onCommand(
        "start",
        requireOnlyCommandInMessage = true // it is default, but you can overwrite it with `requireOnlyCommandInMessage = false`
    ) {
        // ...
    }
}

bot.longPolling(rootContext) // start listening updates
```
