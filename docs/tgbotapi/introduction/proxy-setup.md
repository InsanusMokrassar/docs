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
implementation "io.ktor:ktor-client-okhttp:2.3.5"
```

> NOTE: **Dependency note**
> In the snippet above was used version `2.3.5` which is actual for `TelegramBotAPI` at the moment of filling this documentation (`october 11 2023`, `TelegramBotAPI` version `9.2.2`) and you can update version of this dependency in case if it is outdated.

For configuring proxy for your bot inside your program, you can use next snippet:

```kotlin
val botToken = "HERE MUST BE YOUR TOKEN" // (1)
val bot = telegramBot(botToken) { // (2)
    client = HttpClient(OkHttp) { // (3)
        engine { // (4)
            config { // (5)
                proxy( // (6)
                    Proxy( // (7)
                        Proxy.Type.SOCKS, // (8)
                        InetSocketAddress("127.0.0.1", 1080) // (9)
                    )
                )
            }
        }
    }
}
```

1. Here we are just creating variable `botToken`
2. Start creating bot
3. Setting `HttpClient` of our bot. On the time of documentation filling, `OkHttp` is one of the engines in `Ktor` system which supports socks proxy. More you can read on [Ktor](https://ktor.io) site in subparts about [engines](https://ktor.io/docs/http-client-engines.html#okhttp) and [proxy](https://ktor.io/docs/proxy.html#socks_proxy)
4. Start setting up of `HttpClient` engine
5. Start setting up of `HttpClient` engine configuration
6. Start setting up of proxy
7. Creating proxy info object
8. Saying that it is `Socks` proxy
9. Creating address. Note that `"127.0.0.1"` and `1080` are configurable parameters

## More complex and flexible variant

You may try to use [custom engine for ktor](https://ktor.io/docs/http-client-engines.html). For example:

```kotlin
// JVM
// OkHttp engine
// Socks5 proxy

val bot = telegramBot(botToken) {
    val proxyHost = "your proxy host"
    val proxyPort = 1080 //your proxy port
    val username = "proxy username"
    val password = "proxy password"

    val proxyAddr = InetSocketAddress(proxyHost, proxyPort)
    val proxy = Proxy(Proxy.Type.SOCKS, proxyAddr)

    Authenticator.setDefault(object : Authenticator() {
        protected val passwordAuthentication: PasswordAuthentication?
            protected get() {
                if (requestingHost.lowercase() == proxyHost.lowercase()) {
                    if (proxyPort == requestingPort) {
                        return PasswordAuthentication(username, password.toCharArray())
                    }
                }
                return null
            }
    })
    this.client = HttpClient(OkHttp) {
        engine {
            config {
                proxy(proxy)
            }
        }
    }
}
```

## Next steps

* [First bot](first-bot.md)