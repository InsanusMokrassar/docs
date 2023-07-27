# Insanus Mokrassar libraries home

Hello :) It is my libraries docs place and I glad to welcome you here. I hope, this documentation place will help you.

## Projects

| Common and independent | TelegramBotAPI | Plagubot |
| :--- | :---: | ---: |
| [![Maven Central](https://img.shields.io/maven-central/v/dev.inmo/micro_utils.common?label=microutils&style=flat-square)](https://github.com/InsanusMokrassar/MicroUtils) | [![Maven Central](https://img.shields.io/maven-central/v/dev.inmo/tgbotapi?label=tgbotapi&style=flat-square)](https://github.com/InsanusMokrassar/TelegramBotAPI) | [![Maven Central](https://img.shields.io/maven-central/v/dev.inmo/plagubot.plugin?label=plagubot&style=flat-square)](https://github.com/InsanusMokrassar/PlaguBot) |
| [![Maven Central](https://img.shields.io/maven-central/v/dev.inmo/kslog?label=kslog&style=flat-square)](https://github.com/InsanusMokrassar/KSLog) | [![Maven Central](https://img.shields.io/maven-central/v/dev.inmo/tgbotapi.libraries.cache.admins.common?label=tgbotapi.libraries&style=flat-square)](https://github.com/InsanusMokrassar/TelegramBotAPILibraries) | [![Maven Central](https://img.shields.io/maven-central/v/dev.inmo/plagubot.plugins.commands?label=plagubot.plugins&style=flat-square)](https://github.com/InsanusMokrassar/PlaguBotPlugins) |
| [![Maven Central](https://img.shields.io/maven-central/v/dev.inmo/krontab?label=krontab&style=flat-square)](https://github.com/InsanusMokrassar/Krontab) | | |
| [![Maven Central](https://img.shields.io/maven-central/v/dev.inmo/kjsuikit?label=kjsuikit&style=flat-square)](https://github.com/InsanusMokrassar/JSUIKitKBindings) | | |
| [![Maven Central](https://img.shields.io/maven-central/v/dev.inmo/saucenaoapi?label=saucenaoapi&style=flat-square)](https://github.com/InsanusMokrassar/SauceNaoAPI) | | |
| [![Maven Central](https://img.shields.io/maven-central/v/dev.inmo/navigation.core?label=navigation&style=flat-square)](https://github.com/InsanusMokrassar/navigation) | | |

## Dependencies graph:

```mermaid
%%{init: {"flowchart": {"defaultRenderer": "elk"}} }%%
flowchart TB
    KSLog[<a href='https://github.com/InsanusMokrassar/kslog'>KSLog</a>]
    MicroUtils[<a href='https://github.com/InsanusMokrassar/MicroUtils'>MicroUtils</a>]
    TelegramBotAPI[<a href='https://github.com/InsanusMokrassar/ktgbotapi'>TelegramBotAPI</a>]
    TelegramBotAPI-examples[<a href='https://github.com/InsanusMokrassar/TelegramBotAPI-examples'>TelegramBotAPI-examples </a>]
    PlaguBot[<a href='https://github.com/InsanusMokrassar/PlaguBot'>PlaguBot</a>]
    TelegramBotAPILibraries[<a href='https://github.com/InsanusMokrassar/TelegramBotAPILibraries'>TelegramBotAPILibraries</a>]
    PlaguBotPlugins[<a href='https://github.com/InsanusMokrassar/PlaguBotPlugins'>PlaguBotPlugins</a>]
    PlaguBotExample[<a href='https://github.com/InsanusMokrassar/PlaguBotExample'>PlaguBotExample</a>]
    BooruGrabberTelegramBot[<a href='https://github.com/InsanusMokrassar/BooruGrabberTelegramBot'>BooruGrabberTelegramBot</a>]
    SauceNaoTelegramBot[<a href='https://github.com/InsanusMokrassar/SauceNaoTelegramBot'>SauceNaoTelegramBot</a>]
    PlaguPoster[<a href='https://github.com/InsanusMokrassar/PlaguPoster'>PlaguPoster</a>]
    PlaguBotSuggestionsBot[<a href='https://github.com/InsanusMokrassar/PlaguBotSuggestionsBot'>PlaguBotSuggestionsBot</a>]
    TelegramBotTutorial[<a href='https://github.com/InsanusMokrassar/TelegramBotTutorial'>TelegramBotTutorial</a>]
    Krontab[<a href='https://github.com/InsanusMokrassar/krontab'>Krontab</a>]
    KJSUiKit[<a href='https://github.com/InsanusMokrassar/JSUiKitKBindings'>KJSUiKit</a>]
    SauceNaoAPI[<a href='https://github.com/InsanusMokrassar/SauceNaoAPI'>SauceNaoAPI</a>]
    Navigation[<a href='https://github.com/InsanusMokrassar/navigation'>Navigation</a>]

    TelegramBotAPI-bot_template[<a href='https://github.com/InsanusMokrassar/TelegramBotAPI-bot_template'>TelegramBotAPI-bot_template</a>]
    PlaguBotPluginTemplate[<a href='https://github.com/InsanusMokrassar/PlaguBotPluginTemplate'>PlaguBotPluginTemplate</a>]
    PlaguBotBotTemplate[<a href='https://github.com/InsanusMokrassar/PlaguBotBotTemplate'>PlaguBotBotTemplate</a>]

    MicroUtils --> KSLog
    TelegramBotAPI --> MicroUtils
    TelegramBotAPI-examples --> TelegramBotAPI
    PlaguBot --> TelegramBotAPI
    TelegramBotAPILibraries --> PlaguBot
    PlaguBotPlugins --> TelegramBotAPILibraries
    PlaguBotExample --> PlaguBotPlugins
    BooruGrabberTelegramBot --> TelegramBotAPI
    BooruGrabberTelegramBot --> Krontab
    SauceNaoTelegramBot --> TelegramBotAPI
    SauceNaoTelegramBot --> SauceNaoAPI
    TelegramBotTutorial --> PlaguBotPlugins
    PlaguBotSuggestionsBot --> PlaguBotPlugins
    PlaguPoster --> PlaguBotPlugins
    PlaguPoster --> Krontab
    SauceNaoAPI --> MicroUtils
    Navigation --> MicroUtils

    TelegramBotAPI-bot_template -.- TelegramBotAPI
    PlaguBotPluginTemplate -.- PlaguBot
    PlaguBotBotTemplate -.- PlaguBot
```
