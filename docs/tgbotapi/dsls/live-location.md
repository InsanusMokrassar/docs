# Live Location

Bot API allows you to send live locations and update them during their lifetime. In this library there are several ways to use this API:

* Directly via API calls ([sendLiveLocation](https://tgbotapi.inmo.dev/docs/dev.inmo.tgbotapi.extensions.api.send/send-live-location.html) and [editLiveLocation](https://tgbotapi.inmo.dev/docs/dev.inmo.tgbotapi.extensions.api.edit.location.live/edit-live-location.html))
* [startLiveLocation](https://tgbotapi.inmo.dev/docs/dev.inmo.tgbotapi.extensions.api/start-live-location.html)
* [handleLiveLocation](https://tgbotapi.inmo.dev/docs/dev.inmo.tgbotapi.extensions.api/handle-live-location.html)

## sendLiveLocation

In the Bot API there is no independent `sendLiveLocation` method, instead it is suggested to use [sendLocation](https://core.telegram.org/bots/api#sendlocation) with setting up `live_period`. In this library in difference with original Bot API live location is special request. It was required because of in fact live locations and static locations are different types of location info and you as bot developer may interact with them differently.

Anyway, in common case the logic looks like:

* Send [sendLiveLocation](https://tgbotapi.inmo.dev/docs/dev.inmo.tgbotapi.extensions.api.send/send-live-location.html)
* Use [editLiveLocation](https://tgbotapi.inmo.dev/docs/dev.inmo.tgbotapi.extensions.api.edit.location.live/edit-live-location.html) to change it during its lifetime
* Use [stopLiveLocation](https://tgbotapi.inmo.dev/docs/dev.inmo.tgbotapi.extensions.api.edit.location.live/stop-live-location.html) to abort it before lifetime end

## startLiveLocation

In difference with [sendLiveLocation](#sendLiveLocation), [startLiveLocation](https://tgbotapi.inmo.dev/docs/dev.inmo.tgbotapi.extensions.api/start-live-location.html) using [LiveLocationProvider](https://tgbotapi.inmo.dev/docs/dev.inmo.tgbotapi.extensions.api/-live-location-provider/index.html). With this provider you __need not__ to handle chat and message ids and keep some other data for location changes. Instead, you workflow with provider will be next:

* [startLiveLocation](https://tgbotapi.inmo.dev/docs/dev.inmo.tgbotapi.extensions.api/start-live-location.html)
* Use [LiveLocationProvider#updateLocation](https://tgbotapi.inmo.dev/docs/dev.inmo.tgbotapi.extensions.api/-live-location-provider/update-location.html) to update location and optionally add [inline keyboard](https://tgbotapi.inmo.dev/docs/dev.inmo.tgbotapi.types.buttons/-inline-keyboard-markup/index.html)
* Use [LiveLocationProvider#close](https://tgbotapi.inmo.dev/docs/dev.inmo.tgbotapi.extensions.api/-live-location-provider/close.html) to abort live location before its end

Besides, `LiveLocationProvider` [contains different useful parameters](https://tgbotapi.inmo.dev/docs/dev.inmo.tgbotapi.extensions.api/-live-location-provider/index.html#141178175%2FProperties) about live location

## handleLiveLocation

This way of live locations handling is based on coroutines [Flow](https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/) and allow you to pass some external `Flow` with [EditLiveLocationInfo](https://tgbotapi.inmo.dev/docs/dev.inmo.tgbotapi.extensions.api/-edit-live-location-info/index.html). So, workflow:

* Create your own flow of locations. For example:
```kotlin
flow {
  var i = 0
  while (isActive) {
    val newInfo = EditLiveLocationInfo(
      latitude = i.toDouble(),
      longitude = i.toDouble(),
      replyMarkup = flatInlineKeyboard {
        dataButton("Cancel", "cancel")
      }
    )
    emit(newInfo)
    i++
    delay(10000L) // 10 seconds
  }
}
```
* In case you needed, create your collector to store the message with live location:
```kotlin
val currentMessageState = MutableStateFlow<ContentMessage<LocationContent>?>(null)
```
* Start handle live location. [handleLiveLocation](https://tgbotapi.inmo.dev/docs/dev.inmo.tgbotapi.extensions.api/handle-live-location.html) works synchronosly (in current coroutine) and will ends only when your flow will ends. Thats why there are two ways to call it:
```kotlin
handleLiveLocation(
  it.chat.id,
  locationsFlow,
  sentMessageFlow = FlowCollector { currentMessageState.emit(it) }
)
// this code will be called after `locationsFlow` will ends
```
OR
```kotlin
scope.launch {
  handleLiveLocation(
    it.chat.id,
    locationsFlow,
    sentMessageFlow = FlowCollector { currentMessageState.emit(it) }
  )
}
// this code will be called right after launch will be completed
```

See our [example](https://github.com/InsanusMokrassar/TelegramBotAPI-examples/blob/master/LiveLocationsBot/src/main/kotlin/LiveLocationsBot.kt) to get more detailed sample