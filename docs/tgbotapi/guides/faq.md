# FAQ

## How to filter updates in some part of `BehaviourBuilder`?

You may create subcontext with
`BehaviourBuilder.`[`createSubContext`](https://tgbotapi.inmo.dev/tgbotapi.behaviour_builder/dev.inmo.tgbotapi.extensions.behaviour_builder/create-sub-context.html)
and pass there `updatesUpstreamFlow` parameter with any operations over parent behaviour builder:

```kotlin
buildBehaviourWithLongPolling {
    createSubContext(
        updatesUpstreamFlow = filter { /* some condition */ }
    ) {
        onCommand() //...
    }
}
```

### Additional info

* [Flows docs](https://kotlinlang.org/docs/flow.html#intermediate-flow-operators)
* [BehaviourBuilder](../logic/behaviour-builder.md)

### Cases

* Filtering of chats and users:
    ```kotlin
        updatesUpstreamFlow = filter { it.sourceChat() ?.id == requiredChatId || it.sourceUser() ?.id == requiredUserId }
    ```
    * **See**:
        * [Update.sourceChat](https://tgbotapi.inmo.dev/tgbotapi.utils/dev.inmo.tgbotapi.extensions.utils.extensions/source-chat.html)
        * [Update.sourceUser](https://tgbotapi.inmo.dev/tgbotapi.utils/dev.inmo.tgbotapi.extensions.utils.extensions/source-user.html)
