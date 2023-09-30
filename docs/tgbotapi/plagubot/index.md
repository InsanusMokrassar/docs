# PlaguBot

[PlaguBot](https://github.com/InsanusMokrassar/PlaguBot) is a small framework for unifying developing of modules of bots. It is built with two parts:

* [Plugin](https://github.com/InsanusMokrassar/PlaguBot/blob/master/plugin/src/main/kotlin/dev/inmo/plagubot/Plugin.kt)
* [Bot](https://github.com/InsanusMokrassar/PlaguBot/tree/master/bot/src/main/kotlin/dev/inmo/plagubot)

## Plugin

Plugin is a partially independent part of bot. Plugin have several parts:

* `setupDI` - this method should be used to configure DI part of module
* `setupBotPlugin` - method to start/configure your bot actions

Plugin realization should be an `object` or `class` with empty constructor.

## Bot

Most important of bot is `main` function (full reference: `dev.inmo.plagubot.AppKt`). It consumes one argument - path to config.

Bot is initializing with the next algorithm:

```mermaid
flowchart TB
    main["Main"]

    subgraph ConfigReading
        ConfigJsonParsing["Parsing to Json"]
        ConfigParsing["Parsing to global config"]
        ConfigJsonParsing --> ConfigParsing
    end
    ConfigReading["Reading of config"]
    
    BotConstructorCalling["Calling of PlaguBot constructor"]

    subgraph BotStart
        BotStartKoinAppInit["Initialization of koin app"]
        
        subgraph BotStartSetupDI
            sample
        end
        
        BotStartKoinAppInit --> BotStartSetupDI
    end
    
    main --> ConfigReading
    ConfigReading --> BotConstructorCalling
    BotConstructorCalling --> BotStart
```
