# API Extensions

[API extensions](https://github.com/InsanusMokrassar/TelegramBotAPI/tree/master/tgbotapi.api) is a module which you may include in your project in addition to [core part](https://github.com/InsanusMokrassar/TelegramBotAPI/tree/master/tgbotapi.core). In most cases this module will allow just use syntax like `bot.getUpdates()` instead of `bot.execute(GetUpdates())`, but there are several other things you will achieve with that syntax.

## Bot builder

This functionality allow you to build bot in more unified and comfortable way than standard creating with `telegramBot` function

```kotlin
buildBot(
    "TOKEN"
) {
  proxy = ProxyBuilder.socks(host = "127.0.0.1", port = 4001) // just an example, more info on https://ktor.io/docs/proxy.html
  ktorClientConfig = {
    // configuring of ktor client
  }
  ktorClientEngineFactory = {
   // configuring of ktor client engine 
  }
}
```

## Downloading of files

In standard library requests there are no way to download some file retrieved in updates or after requests. You may use syntax like `bot.downloadFile(file)` where `file` is `TelegramMediaFile` from telegram, `FileId` or even `PathedFile` from [GetFile](https://github.com/InsanusMokrassar/TelegramBotAPI/blob/master/tgbotapi.core/src/commonMain/kotlin/dev/inmo/tgbotapi/requests/get/GetFile.kt) request ([sources](https://github.com/InsanusMokrassar/TelegramBotAPI/blob/master/tgbotapi.api/src/commonMain/kotlin/dev/inmo/tgbotapi/extensions/api/files/DownloadFile.kt)).

## Live location

By default, you should handle updates of Live location by your code. But with extension [bot#startLiveLocation](https://github.com/InsanusMokrassar/TelegramBotAPI/blob/master/tgbotapi.api/src/commonMain/kotlin/dev/inmo/tgbotapi/extensions/api/LiveLocationProvider.kt#L84) you may provide all necessary startup parameters and handle updates with just calling `updateLocation` for retrieved [LiveLocationProvider](https://tgbotapi.inmo.dev/docs/dev.inmo.tgbotapi.extensions.api/-live-location-provider/index.html).

## What is next?

There are several things you may read next:

* [Updates retrieving](https://bookstack.inmo.dev/books/telegrambotapi/chapter/updates-retrieving)
* Read about [second level](https://bookstack.inmo.dev/books/telegrambotapi/page/updates-with-flows) of working with library
* Read about [BehaviourBuilder](https://bookstack.inmo.dev/books/telegrambotapi/page/behaviour-builder)