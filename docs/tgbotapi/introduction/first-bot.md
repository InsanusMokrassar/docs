# First bot

> NOTE: **Examples info**
> A lot of examples with using of Telegram Bot API you can find in [this github repository](https://github.com/InsanusMokrassar/TelegramBotAPI-examples)

### The most simple bot

The most simple bot will just print information about itself. All source code you can find [in this repository](https://github.com/InsanusMokrassar/TelegramBotAPI-examples/tree/master/GetMeBot). Our interest here will be concentrated on the next example part:

```kotlin
suspend fun main(vararg args: String) {
  val botToken = args.first()
  val bot = telegramBot(botToken)
  println(bot.getMe())
}
```

So, let's get understanding, about what is going on:

1. `suspend fun main(vararg args: String)`:
    * `suspend` required for making of requests inside of this function. For more info you can [open official documentation](https://kotlinlang.org/docs/reference/coroutines/basics.html) for coroutins. In fact, `suspend fun main` is the same that `fun main() = runBlocking {}` from examples
2. `val botToken = args.first()`: here we are just getting the bot token from first arguments of command line
3. `val bot = telegramBot(botToken)` : inside of `bot` will be [RequestsExecutor](https://tgbotapi.inmo.dev/docs/com.github.insanusmokrassar.-telegram-bot-a-p-i.bot/-requests-executor/index.html) object which will be used for all requests in any project with this library
4. `println(bot.getMe())`: here happens calling of [getMe](https://tgbotapi.inmo.dev/docs/com.github.insanusmokrassar.-telegram-bot-a-p-i.extensions.api.bot/get-me.html) extension

As a result, we will see in the command line something like

```shell
ExtendedBot(id=ChatId(chatId=123456789), username=Username(username=@first_test_ee17e8_bot), firstName=Your bot name, lastName=, canJoinGroups=false, canReadAllGroupMessages=false, supportsInlineQueries=false)
```