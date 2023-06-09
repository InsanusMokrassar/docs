# Types conversations

One of the most important topics in context of tgbotapi is types conversations. This library is very strong-typed and a lot of things are based on types hierarchy. Lets look into the hierarchy of classes for the [Message](https://github.com/InsanusMokrassar/TelegramBotAPI/blob/master/tgbotapi.core/src/commonMain/kotlin/dev/inmo/tgbotapi/types/message/abstracts/Message.kt#L12) in 0.35.8: [![Message Diagram.png](https://bookstack.inmo.dev/uploads/images/gallery/2021-09/scaled-1680-/message-diagram.png)](https://bookstack.inmo.dev/uploads/images/gallery/2021-09/message-diagram.png)

As you may see, it is a little bit complex and require several tools for types conversation.

## As

`as` conversations will return new type in case if it is possible. For example, when you got `Message`, you may use `asContentMessage` conversation to get message with `content`:

```kotlin
val message: Message;
println(message.asContentMessage() ?.content)
```

This code will print `null` in case when `message` is not `ContentMessage`, and `content` when is.

## Require

`require` works like `as`, but instead of returning nullable type, it will always return object with required type OR throw `ClassCastException`:

```kotlin
val message: Message;
println(message.requireContentMessage().content)
```

This code will throw exception when message is not `ContentMessage` and print `content` when is.

## When

`when` extensions will call passed `block` when type is correct. For example:

```kotlin
val message: Message;
message.whenContentMessage {
    println(it.content)
}
```

Code placed above will print `content` when `message` is `ContentMessage` and do nothing when not