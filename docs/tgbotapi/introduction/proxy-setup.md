# Proxy setup

In some locations Telegram Bots API urls will be unavailable. In this case all examples will just throw exception like:

```bash
Exception in thread "main" java.net.ConnectException: Connection refused
	at sun.nio.ch.SocketChannelImpl.checkConnect(Native Method)
	at sun.nio.ch.SocketChannelImpl.finishConnect(SocketChannelImpl.java:717)
	at io.ktor.network.sockets.SocketImpl.connect$ktor_network(SocketImpl.kt:36)
	at io.ktor.network.sockets.SocketImpl$connect$1.invokeSuspend(SocketImpl.kt)
	at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
	at kotlinx.coroutines.DispatchedTask.run(DispatchedTask.kt:56)
	at kotlinx.coroutines.scheduling.CoroutineScheduler.runSafely(CoroutineScheduler.kt:571)
	at kotlinx.coroutines.scheduling.CoroutineScheduler$Worker.executeTask(CoroutineScheduler.kt:738)
	at kotlinx.coroutines.scheduling.CoroutineScheduler$Worker.runWorker(CoroutineScheduler.kt:678)
	at kotlinx.coroutines.scheduling.CoroutineScheduler$Worker.run(CoroutineScheduler.kt:665)

Process finished with exit code 1
```

There are several ways to solve this problem:

* Built-in proxy config (will require some socks or http proxy server)
* System-configured VPN or proxy
* Your own Bot API Server

### Using Ktor Client built-in proxy

First of all, you will need to use one more library:

**build.gradle**:

```groovy
implementation "io.ktor:ktor-client-okhttp:2.0.1"
```

> NOTE: **Dependency note**
> In the snippet above was used version `2.0.1` which is actual for `TelegramBotAPI` at the moment of filling this documentation (`May 22 2022`, `TelegramBotAPI` version `2.0.0`) and you can update version of this dependency in case if it is outdated.

For configuring proxy for your bot inside your program, you can use next snippet:

```kotlin
val botToken = "HERE MUST BE YOUR TOKEN"
val bot = telegramBot(botToken) {
  ktorClientEngineFactory = OkHttp
  proxy = ProxyBuilder.socks("127.0.0.1", 1080)
}
```

Explanation line by line:

1. `val botToken = "HERE MUST BE YOUR TOKEN"` - here we are just creating variable `botToken`
2. `val bot = telegramBot(botToken) {` - start creating bot
3. `ktorClientEngineFactory = OkHttp` - setting up engine factory of our bot. On the time of documentation filling, `OkHttp` is one of the engines in `Ktor` system which supports socks proxy. More you can read on [Ktor](https://ktor.io) site in subparts about [engines](https://ktor.io/clients/http-client/engines.html#okhttp) and [proxy](https://ktor.io/clients/http-client/features/proxy.html)
4. `proxy = ProxyBuilder.socks("127.0.0.1", 1080)` - here we are setting up our proxy. Here was used local server which (as assumed) will connect to server like `shadowsocks`

## Next steps

* [First bot](first-bot.md)