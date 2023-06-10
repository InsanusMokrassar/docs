# Updates filters

Due to the fact, that anyway you will get updates in one format (`Update` objects), some time ago was solved to create one point of updates filters for more usefull way of updates handling

## UpdatesFilter

`UpdatesFilter` currently have two properties:

* `asUpdateReceiver` - required to represent this filter as common updates receiver which able to get any `Update`
* `allowedUpdates` - required to determine, which updates are usefull for this filter

Anyway, this filter can't work with updates by itself. For retrieving updates you should pass this filter to some of getting updates functions ([long polling](long-polling) or [webhooks](webhooks.md)).

### SimpleUpdatesFilter

`SimpleUpdatesFilter` is a simple variant of filters. It have a lot of `UpdateReceiver` properties which can be set up on creating of this object. For example, if you wish to get messages from chats (but not from channels), you can use next snippet:

```kotlin
SimpleUpdatesFilter {
  println(it)
}
```

### FlowsUpdatesFilter

A little bit more modern way is to use `FlowsUpdatesFilter`. It is very powerfull API of Kotlin Coroutines Flows, built-in support of additional extensions for `FlowsUpdatesFilter` and `Flow<...>` receivers and opportunity to split one filter for as much receivers as you want. Filter creating example:

```kotlin
val scope = CoroutineScope(Dispatchers.Default)
flowsUpdatesFilter {
  messageFlow.onEach {
    println(it)
  }.launchIn(scope)
}
```

#### Combining of flows

In cases you need not separate logic for handling of messages from channels and chats there are three ways to combine different flows into one:

* Standard `plus` operation and handling of different flows:
```kotlin
flowsUpdatesFilter {
  (messageFlow + channelPostFlow).onEach {
    println(it) // will be printed each message update from channels and chats both
  }.launchIn(scope)
}
```
* TelegramBotAPI library support function `aggregateFlows`:
```kotlin
flowsUpdatesFilter {
  aggregateFlows(
    scope,
    messageFlow,
    channelPostFlow
  ).onEach {
    println(it) // will be printed each message update from channels and chats both
  }.launchIn(scope)
}
```
* `FlowsUpdatesFilter` extensions:
```kotlin
flowsUpdatesFilter {
  allSentMessagesFlow.onEach {
    println(it) // will be printed each message update from channels and chats both
  }.launchIn(scope)
}
```

#### Types filtering

`FlowsUpdatesFilter` have a lot of extensions for messages types filtering:

```kotlin
flowsUpdatesFilter {
  textMessages(scope).onEach {
    println(it) // will be printed each message from channels and chats both with content only `TextContent`
  }.launchIn(scope)
}
```

The same things were created for media groups:

```kotlin
flowsUpdatesFilter {
  mediaGroupMessages(scope).onEach {
    println(it) // will be printed each media group messages list from both channels and chats without filtering of content
  }.launchIn(scope)

  mediaGroupPhotosMessages(scope).onEach {
    println(it) // will be printed each media group messages list from both channels and chats with PhotoContent only
  }.launchIn(scope)

  mediaGroupVideosMessages(scope).onEach {
    println(it) // will be printed each media group messages list from both channels and chats with VideoContent only
  }.launchIn(scope)
}
```

Besides, there is an opportunity to avoid separation on media groups and common messages and receive photos and videos content in one flow:


```kotlin
flowsUpdatesFilter {
  sentMessagesWithMediaGroups(scope).onEach {
    println(it) // will be printed each message including each separated media group message from both channels and chats without filtering of content
  }.launchIn(scope)

  photoMessagesWithMediaGroups(scope).onEach {
    println(it) // will be printed each message including each separated media group message from both channels and chats with PhotoContent only
  }.launchIn(scope)

  videoMessagesWithMediaGroups(scope).onEach {
    println(it) // will be printed each message including each separated media group message from both channels and chats with VideoContent only
  }.launchIn(scope)
}
```


## See also

* [Long polling](long-polling.md)
* [Webhooks](webhooks.md)