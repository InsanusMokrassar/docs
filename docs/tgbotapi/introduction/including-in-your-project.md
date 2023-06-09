# Including in your project

There are three projects:

* `TelegramBotAPI Core` - project with base for all working with Telegram Bot API
* `TelegramBotAPI API Extensions` - extension of `TelegramBotAPI` with functions for more comfortable work with Telegram Bot API
* `TelegramBotAPI Utils Extensions` - extension of `TelegramBotAPI` with functions for extending of different things like retrieving of updates

> NOTE: **TelegramBotAPI**
>
> Also, there is an aggregator-version `tgbotapi`, which will automatically include all projects above. It is most recommended version due to the fact that it is including all necessary tools around `TelegramBotAPI Core`, but it is optionally due to the possible restrictions on the result methods count (for android) or bundle size

> NOTE: **Examples**
>
> You can find full examples info in [this repository](https://github.com/InsanusMokrassar/TelegramBotAPI-examples/). In this repository there full codes which are working in normal situation. Currently, there is only one exception when these examples could work incorrectly: you are living in the location where Telegram Bot API is unavailable. For solving this problem you can read [Proxy setup](proxy-setup) part

### Notice about repository
  
To use this library, you will need to include `Maven Central` repository in your project

###### build.gradle

```groovy
mavenCentral()
```

###### pom.xml

```xml
<repository>
  <id>central</id>
  <name>mavenCentral</name>
  <url>https://repo1.maven.org/maven2</url>
</repository>
```

###### Dev channel

Besides, there is [developer versions repo](https://git.inmo.dev/InsanusMokrassar/-/packages/maven/dev.inmo-tgbotapi). To use it in your project, add the repo in `repositories` section:

<details><summary>Gradle</summary>
  
```groovy
maven {
    url "https://git.inmo.dev/api/packages/InsanusMokrassar/maven"
}
```

</details>

<details><summary>Maven</summary>
  
```xml
<repository>
  <id>dev.inmo</id>
  <name>InmoDev</name>
  <url>https://git.inmo.dev/api/packages/InsanusMokrassar/maven</url>
</repository>
```
  
</details>

### TelegramBotAPI

As `tgbotapi_version` variable in next snippets will be used variable with next last published version:

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/dev.inmo/tgbotapi/badge.svg)](https://maven-badges.herokuapp.com/maven-central/dev.inmo/tgbotapi)

###### build.gradle

```groovy
implementation "dev.inmo:tgbotapi:$tgbotapi_version"
```

###### pom.xml

```xml
<dependency>
    <groupId>dev.inmo</groupId>
    <artifactId>tgbotapi</artifactId>
    <version>${tgbotapi_version}</version>
</dependency>
```

### TelegramBotAPI Core

As `tgbotapi_version` variable in next snippets will be used variable with next last published version:

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/dev.inmo/tgbotapi.core/badge.svg)](https://maven-badges.herokuapp.com/maven-central/dev.inmo/tgbotapi.core)

###### build.gradle

```groovy
implementation "dev.inmo:tgbotapi.core:$tgbotapi_version"
```

###### pom.xml

```xml
<dependency>
    <groupId>dev.inmo</groupId>
    <artifactId>tgbotapi.core</artifactId>
    <version>${tgbotapi_version}</version>
</dependency>
```

### TelegramBotAPI API Extensions

As `tgbotapi_version` variable in next snippets will be used variable with next last published version:

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/dev.inmo/tgbotapi.api/badge.svg)](https://maven-badges.herokuapp.com/maven-central/dev.inmo/tgbotapi.api)

###### build.gradle

```groovy
implementation "dev.inmo:tgbotapi.api:$tgbotapi_version"
```

###### pom.xml

```xml
<dependency>
    <groupId>dev.inmo</groupId>
    <artifactId>tgbotapi.api</artifactId>
    <version>${tgbotapi_version}</version>
</dependency>
```

### TelegramBotAPI Utils Extensions

As `tgbotapi_version` variable in next snippets will be used variable with next last published version:

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/dev.inmo/tgbotapi.utils/badge.svg)](https://maven-badges.herokuapp.com/maven-central/dev.inmo/tgbotapi.utils)

###### build.gradle

```groovy
implementation "dev.inmo:tgbotapi.utils:$tgbotapi_version"
```

###### pom.xml

```xml
<dependency>
    <groupId>dev.inmo</groupId>
    <artifactId>tgbotapi.utils</artifactId>
    <version>${tgbotapi_version}</version>
</dependency>
```

## Next steps

* [Proxy setup](proxy-setup.html)
* [First bot](first-bot.html)
