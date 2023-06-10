# Low-level work with bots

The base version of library was done a lot of time ago and just got several additions related to improvements, updates in [Telegram Bot API](https://core.telegram.org/bots/api) or some requests from [our community](https://t.me/InMoTelegramBotAPIChat).

# Base things

There are several important things in context of this library:

* [RequestsExecutor](https://github.com/InsanusMokrassar/TelegramBotAPI/blob/master/tgbotapi.core/src/commonMain/kotlin/dev/inmo/tgbotapi/bot/RequestsExecutor.kt#L13) (also "known" as `TelegramBot`)
* [Types](https://github.com/InsanusMokrassar/TelegramBotAPI/tree/master/tgbotapi.core/src/commonMain/kotlin/dev/inmo/tgbotapi/types)
* [Requests](https://github.com/InsanusMokrassar/TelegramBotAPI/tree/master/tgbotapi.core/src/commonMain/kotlin/dev/inmo/tgbotapi/requests)

So, in most cases all your request calls with simplified api of this library (like `bot.getMe()`) will looks like `bot.execute(GetMe)`. Result of these calls is defined in type of any request (for example, for [GetMe](https://github.com/InsanusMokrassar/TelegramBotAPI/blob/master/tgbotapi.core/src/commonMain/kotlin/dev/inmo/tgbotapi/requests/bot/GetMe.kt) request the result type is [ExtendedBot](https://github.com/InsanusMokrassar/TelegramBotAPI/blob/master/tgbotapi.core/src/commonMain/kotlin/dev/inmo/tgbotapi/types/chat/Extended.kt#L101)). As a result, you can avoid any extension api (like [special API extensions](api-extensions.md)) and use low level request with full controlling of the whole logic flow.

## How to handle updates

As was written above, it will require some request:

```kotlin
val updates = bot.execute(GetUpdates())
```

Result type of [GetUpdates](https://github.com/InsanusMokrassar/TelegramBotAPI/blob/master/tgbotapi.core/src/commonMain/kotlin/dev/inmo/tgbotapi/requests/GetUpdates.kt#L24) request is [Update](https://github.com/InsanusMokrassar/TelegramBotAPI/blob/master/tgbotapi.core/src/commonMain/kotlin/dev/inmo/tgbotapi/types/update/abstracts/Update.kt#L13). You may find _inheritors_ of this interface in [Update kdocs](https://tgbotapi.inmo.dev/docs/dev.inmo.tgbotapi.types.update.abstracts/-update/index.html).

## What is next?

As was said above, you may look into our [API extensions](api-extensions.md) in case you wish to use more high-level functions instead of `bot.execute(SomeRequest())`. Besides, it will be very useful to know more about [updates retrieving](../updates).