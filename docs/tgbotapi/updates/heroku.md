# Heroku

> NOTE: **Preview reading**
>
> It is recommended to visit our pages about [UpdatesFilters](updates-filters.html) and [Webhooks](webhooks.html) to have more clear understanding about what is happening in this examples page

[Heroku](https://heroku.com/) is a popular place for bots hosting. In common case you will need to configure webhooks for your server to include getting updates without problems. There are several things related to heroku you should know:

* Heroku apps by default accessible via `https://<app name>.herokuapp.com/`
* Heroku provide one port to be proxied for the link above. You can retrieve number of this port by calling `System.getenv("PORT").toInt()`
* Currently (`Sat Aug 15 5:04:21 +00 2020`) there is only one official server engine for ktor which is correctly working with Heroku: [Tomcat server engine](https://ktor.io/servers/configuration.html#tomcat)

> NOTE: **Server configuration alternatives**
> Here will be presented variants of configuration of webhooks and starting server. You always able to set webhook manualy, create your own ktor server and include webhooks handling in it or create and start server with only webhooks handling. More info you can get on page [Webhooks](webhooks.html)

### Short example with Behaviour Builder

```kotlin
suspend fun main {
    // This subroute will be used as random webhook subroute to improve security according to the recommendations of Telegram
    val subroute = uuid4().toString()
    // Input/Output coroutines scope more info here: https://kotlinlang.org/docs/coroutines-guide.html
    val scope = CoroutineScope(Dispatchers.IO)
    // Here will be automatically created bot and available inside of lambda where you will setup your bot behaviour
    telegramBotWithBehaviour(
        // Pass TOKEN inside of your application environment variables
        System.getenv("TOKEN"),
        scope = scope
    ) {
        // Set up webhooks and start to listen them
        setWebhookInfoAndStartListenWebhooks(
        	// Automatic env which will be passed by heroku to the app
            System.getenv("PORT").toInt(),
        	// Server engine. More info here: https://ktor.io/docs/engines.html
            Tomcat,
        	// Pass URL environment variable via settings of application. It must looks like https://<app name>.herokuapp.com
            SetWebhook("${System.getenv("URL").removeSuffix("/")}/$subroute"),
            // Just callback which will be called when exceptions will happen inside of webhooks
            {
                it.printStackTrace()
            },
            // Set up listen requests from outside
            "0.0.0.0",
            // Set up subroute to listen webhooks to
            subroute,
            // BehaviourContext is the CoroutineScope and it is recommended to pass it inside of webhooks server
            scope = this,
            // BehaviourContext is the FlowsUpdatesFilter and it is recommended to pass its asUpdateReceiver as a block to retrieve all the updates
            block = asUpdateReceiver
        )
      	// Test reaction on each command with reply and text `Got it`
        onUnhandledCommand {
            reply(it, "Got it")
        }
    }
  	// Just potentially infinite await of bot completion
    scope.coroutineContext.job.join()
}
```

### Configuration example without Behaviour Builder

```kotlin
// This subroute will be used as random webhook subroute to improve security according to the recommendations of Telegram
val subroute = uuid4().toString()
val bot = telegramBot(TOKEN)
val scope = CoroutineScope(Dispatchers.Default)

val filter = flowsUpdatesFilter {
  messageFlow.onEach {
    println(it) // will be printed 
  }.launchIn(scope)
}

val subroute = UUID.randomUUID().toString() // It will be used as subpath for security target as recommended by https://core.telegram.org/bots/api#setwebhook

val server = bot.setWebhookInfoAndStartListenWebhooks(
  // Automatic env which will be passed by heroku to the app
  System.getenv("PORT").toInt(),
  // Server engine. More info here: https://ktor.io/docs/engines.html
  Tomcat,
  // Pass URL environment variable via settings of application. It must looks like https://<app name>.herokuapp.com
  SetWebhook("${System.getenv("URL").removeSuffix("/")}/$subroute"),
  // Just callback which will be called when exceptions will happen inside of webhooks
  {
    it.printStackTrace()
  },
  // Set up listen requests from outside
  "0.0.0.0",
  // Set up subroute to listen webhooks to
  subroute,
  scope = scope,
  block = filter.asUpdateReceiver
)

server.environment.connectors.forEach {
  println(it)
}
server.start(false)
```