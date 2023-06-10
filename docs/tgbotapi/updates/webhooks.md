# Webhooks

In telegram bot API there is an opportunity to get updates via [webhooks](https://core.telegram.org/bots/webhooks). In this case you will be able to retrieve updates without making additional requests. Most of currently available methods for webhooks are working on [ktor server](https://ktor.io/servers/index.html) for JVM. Currently, next ways are available for using for webhooks:

* `Route#includeWebhookHandlingInRoute` for [ktor server](https://ktor.io/servers/index.html)
  * `Route#includeWebhookHandlingInRouteWithFlows` 
* `startListenWebhooks`
* `RequestsExecutor#setWebhookInfoAndStartListenWebhooks`

### `setWebhookInfoAndStartListenWebhooks`

It is the most common way to set updates webhooks and start listening of them. Example:

```kotlin
val bot = telegramBot(TOKEN)

val filter = flowsUpdatesFilter {
  // ...
}

bot.setWebhookInfoAndStartListenWebhooks(
  8080, // listening port. It is required for cases when your server hidden by some proxy or other system like Heroku
  CIO, // default ktor server engine. It is recommended to replace it with something like `Netty`. More info about engines here: https://ktor.io/servers/configuration.html
  SetWebhook(
    "address.com/webhook_route",
    File("/path/to/certificate").toInputFile(), // certificate file. More info here: https://core.telegram.org/bots/webhooks#a-certificate-where-do-i-get-one-and-how
    40, // max allowed updates, by default is null
    filter.allowedUpdates
  ),
  {
    it.printStackTrace() // optional handling of exceptions
  },
  "0.0.0.0", // listening host which will be used to bind by server
  "subroute", // Optional subroute, if null - will listen root of address
  WebhookPrivateKeyConfig( // optional config of private key. It will be installed in server to use TLS with custom certificate. More info here: https://core.telegram.org/bots/webhooks#a-certificate-where-do-i-get-one-and-how
    "/path/to/keystore.jks",
    "KeystorePassword",
    "Keystore key alias name",
    "KeystoreAliasPassword"
  ),
  scope, // Kotlin coroutine scope for internal transforming of media groups
  filter.asUpdateReceiver
)
```

If you will use previous example, ktor server will bind and listen url `0.0.0.0:8080/subroute` and telegram will send requests to address `address.com/webhook_route` with custom certificate. Alternative variant will use the other `SetWebhook` request variant:

```kotlin
SetWebhook(
  "address.com/webhook_route",
  "some_file_bot_id".toInputFile(),
  40, // max allowed updates, by default is null
  filter.allowedUpdates
)
```

As a result, request `SetWebhook` will be executed and after this server will start its working and handling of updates.

### `startListenWebhooks`

This function is working almost exactly like previous example, but this one will not set up webhook info in telegram:

```kotlin
val filter = flowsUpdatesFilter {
  // ...
}

startListenWebhooks(
  8080, // listening port. It is required for cases when your server hidden by some proxy or other system like Heroku
  CIO, // default ktor server engine. It is recommended to replace it with something like `Netty`. More info about engines here: https://ktor.io/servers/configuration.html
  {
    it.printStackTrace() // optional handling of exceptions
  },
  "0.0.0.0", // listening host which will be used to bind by server
  "subroute", // Optional subroute, if null - will listen root of address
  WebhookPrivateKeyConfig( // optional config of private key. It will be installed in server to use TLS with custom certificate. More info here: https://core.telegram.org/bots/webhooks#a-certificate-where-do-i-get-one-and-how
    "/path/to/keystore.jks",
    "KeystorePassword",
    "Keystore key alias name",
    "KeystoreAliasPassword"
  ),
  scope, // Kotlin coroutine scope for internal transforming of media groups
  filter.asUpdateReceiver
)
```

The result will be the same as in previous example: server will start its working and handling of updates on `0.0.0.0:8080/subroute`. The difference here is that in case if this bot must not answer or send some requiests - it will not be necessary to create bot for receiving of updates.

### Extensions `includeWebhookHandlingInRoute` and `includeWebhookHandlingInRouteWithFlows`

For these extensions you will need to start your server manualy. In common case it will look like:

```kotlin
val scope = CoroutineScope(Dispatchers.Default)

val filter = flowsUpdatesFilter {
  // ...
}

val environment = applicationEngineEnvironment {
  module {
    routing {
      includeWebhookHandlingInRoute(
        scope,
        {
          it.printStackTrace()
        },
        filter.asUpdateReceiver
      )
    }
  }
  connector {
    host = "0.0.0.0"
    port = 8080
  }
}

embeddedServer(CIO, environment).start(true) // will start server and wait its stoping
```

In the example above server will started and binded for listening on `0.0.0.0:8080`.

## See also

* [Updates filters](updates-filters.md)
* [Long polling](long-polling.md)