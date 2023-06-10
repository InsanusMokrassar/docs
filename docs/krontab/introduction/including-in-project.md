# Including in project

In two words, you must add dependency `dev.inmo:krontab:$krontab_version` to your project. The latest version presented by next badge:

 [![Maven Central](https://maven-badges.herokuapp.com/maven-central/dev.inmo/krontab/badge.svg)](https://maven-badges.herokuapp.com/maven-central/dev.inmo/krontab)

### Notice about repository
  
To use this library, you will need to include `MavenCentral` repository in you project

###### build.gradle

```groovy
mavenCentral()
```

### Dependencies

Next snippets must be placed into your `dependencies` part of `build.gradle` (for gradle) or `pom.xml` (for maven).

#### Gradle

```groovy
implementation "dev.inmo:krontab:$krontab_version"
```

#### Maven

```xml
<dependency>
    <groupId>dev.inmo</groupId>
    <artifactId>krontab</artifactId>
    <version>${krontab_version}</version>
</dependency>
```