# Media Groups

As you know, Telegram have the feature named Media Groups. Media groups have several differences with the common messages:

* Each media group message contains special media group id
* Media group may have special caption which will be visible if only the first message of media group contains caption
* In most cases media groups came with long polling/webhooks in one pack
* Media groups can be one of three types:
    * Visual (image/video)
    * Documents
    * Playlists (audio)

## Specific of media groups in libraries

> NOTE: **Row updates**
> In tgbotapi there is no any additional handling of media groups by default and in case you will use simple [bot.getUpdates](https://insanusmokrassar.github.io/TelegramBotAPI/docs/dev.inmo.tgbotapi.extensions.api/get-updates.html), you will get the list of row updates and media groups will be included in this list as separated messages with [MediaGroupPartContent](https://tgbotapi.inmo.dev/docs/dev.inmo.tgbotapi.types.message.content/-media-group-part-content/index.html). In that case you may use [convertWithMediaGroupUpdates](https://tgbotapi.inmo.dev/docs/dev.inmo.tgbotapi.extensions.utils.updates/convert-with-media-group-updates.html) to be able to work with media groups as will be described below


In case you are using standard [long polling](https://tgbotapi.inmo.dev/docs/dev.inmo.tgbotapi.extensions.utils.updates.retrieving/long-polling.html) (one of alternatives is [telegramBotWithBehaviourAndLongPolling](https://tgbotapi.inmo.dev/docs/dev.inmo.tgbotapi.extensions.behaviour_builder/telegram-bot-with-behaviour-and-long-polling.html)) or [webhooks](https://tgbotapi.inmo.dev/docs/dev.inmo.tgbotapi.extensions.utils.updates.retrieving/set-webhook-info-and-start-listen-webhooks.html) updates will be converted uner the hood and as a result, you will take media groups as a content in one message:

```kotlin
telegramBotWithBehaviourAndLongPolling(
  "token"
) {
  onVisualGallery { // it: CommonMessage<MediaGroupContent<VisualMediaGroupPartContent>>
    it.content // MediaGroupContent<VisualMediaGroupPartContent>
    it.content.group // List<MediaGroupCollectionContent.PartWrapper<VisualMediaGroupPartContent>>
    it.content.group.forEach { // it: MediaGroupCollectionContent.PartWrapper<VisualMediaGroupPartContent>
      it.messageId // source message id for current media group part
      it.sourceMessage // source message for current media group part
      it.content // VisualMediaGroupPartContent
      println(it.content) // will print current content part info
    }
  }
}
```

**KDocs:**

* [onVisualGallery](https://tgbotapi.inmo.dev/docs/dev.inmo.tgbotapi.extensions.behaviour_builder.triggers_handling/on-visual-gallery-messages.html)
* [MediaGroupContent](https://tgbotapi.inmo.dev/docs/dev.inmo.tgbotapi.types.message.content/-media-group-content/index.html)
* [VisualMediaGroupPartContent](https://tgbotapi.inmo.dev/docs/dev.inmo.tgbotapi.types.message.content/-visual-media-group-part-content/index.html)
* [MediaGroupCollectionContent.PartWrapper](https://tgbotapi.inmo.dev/docs/dev.inmo.tgbotapi.types.message.content/-media-group-collection-content/-part-wrapper/index.html)

In two words, in difference with row Telegram Bot API, you will take media groups in ___one message___ instead of ___messages list___.