# Text

For the text creating there are several tools. The most simple one is to concatenate several text sources to make list of text sources as a result:

```kotlin
val sources = "Regular start of text " + bold("with bold part") + italic("and italic ending")
```

But there is a little bit more useful way: entities builder:

```kotlin
val items = (0 until 10).map { it.toString() }
buildEntities(" ") {// optional " " auto separator which will be pasted between text sources
    +"It is regular start too" + bold("it is bold as well")
    items.forEachIndexed { i, item ->
        if (i % 2) {
            italic(item)
        } else {
            strikethrough(item)
        }
    }
}
```

In the code above we are creating an items list just for demonstrating that inside of buildEntities body we may use any operations for cunstructing our result list of `TextSource`s. As a result, will be created the list which will looks like in telegram as "It is regular start too **it is bold as well** *0* ~~1~~ *2* ~~3~~ *4* ~~5~~ *6* ~~7~~ *8* ~~9~~".