# Files handling

According to the [documentation](https://core.telegram.org/bots/api#sending-files) there are several ways to work with files:

* By [FileId](https://github.com/InsanusMokrassar/TelegramBotAPI/blob/master/tgbotapi.core/src/commonMain/kotlin/dev/inmo/tgbotapi/requests/abstracts/InputFile.kt#L52)
* By [FileUrl](https://github.com/InsanusMokrassar/TelegramBotAPI/blob/master/tgbotapi.core/src/commonMain/kotlin/dev/inmo/tgbotapi/requests/abstracts/InputFile.kt#L56) (`typealias` for the `FileId`)
* By some [MultipartFile](https://github.com/InsanusMokrassar/TelegramBotAPI/blob/master/tgbotapi.core/src/commonMain/kotlin/dev/inmo/tgbotapi/requests/abstracts/InputFile.kt#L74) (in Telegram Bot API it is multipart requests)

# Files receiving

There are several cases you may need in your app to work with files:

* Save `FileId` (for sending in future)
* Download some file into memory/file in filesystem

### Where to get File id or url?

The most simple way to send some file is to get file id and send it. You may get file id from any message with media. For example, if you have received some [Message](https://github.com/InsanusMokrassar/TelegramBotAPI/blob/master/tgbotapi.core/src/commonMain/kotlin/dev/inmo/tgbotapi/types/message/abstracts/Message.kt#L12), you may use [asCommonMessage](https://github.com/InsanusMokrassar/TelegramBotAPI/blob/master/tgbotapi.utils/src/commonMain/kotlin/dev/inmo/tgbotapi/extensions/utils/ClassCasts.kt#L1094) conversation to be able to get its `content` and then convert it to some content with media. Full code here:

```kotlin
val message: Message;

val fileId = message.asCommonMessage() ?.withContent<MediaContent>() ?.content ?.media ?.fileId;
```

**WAT? O.o**

In the code above we get some message, safely converted it to `CommonMessage` with `asCommonMessage`, then safely took its content via `withContent<MediaContent>() ?.content` and then just get its media file id.

## Download files

There are three ways to download files:

* Download it in memory as `ByteArray`
* Take `ByteReadChannelAllocator` which allow to retrieve [ByteReadChannel](https://api.ktor.io/ktor-io/io.ktor.utils.io/-byte-read-channel/index.html) and do whatever you want with it
* `[JVM Only]` Download it directly to file or temporal file

### Downloading with `API` extensions

#### Files (JVM/Android)

```kotlin
val bot: TelegramBot;
val fileId: FileId;
val outputFile: File;

bot.downloadFile(fileId, outputFile)
```

See [downloadFile](https://tgbotapi.inmo.dev/docs/dev.inmo.tgbotapi.extensions.api.files/download-file.html) extension docs in the __JVM tab__ to get more available options

There is also way with saving of data into temporal file. That will allow you to do with data whatever you want without high requirements to memory or network connection:

```kotlin
val bot: TelegramBot;
val fileId: FileId;

val tempFile: File = bot.downloadFileToTemp(fileId)
```

See [downloadFileToTemp](https://tgbotapi.inmo.dev/docs/dev.inmo.tgbotapi.extensions.api.files/download-file-to-temp.html) extension docs to get more available options

#### Byte read channel

```kotlin
val bot: TelegramBot;
val fileId: FileId;

val bytes: ByteReadChannelAllocator = bot.downloadFileStream(fileId)
```

See [downloadFileStream](https://tgbotapi.inmo.dev/docs/dev.inmo.tgbotapi.extensions.api.files/download-file-stream.html) extension docs to get more available options

#### Byte read channel allocator

```kotlin
val bot: TelegramBot;
val fileId: FileId;

val bytes: ByteReadChannelAllocator = bot.downloadFileStreamAllocator(fileId)
```

See [downloadFileStreamAllocator](https://tgbotapi.inmo.dev/docs/dev.inmo.tgbotapi.extensions.api.files/download-file-stream-allocator.html) extension docs to get more available options

#### Byte arrays

```kotlin
val bot: TelegramBot;
val fileId: FileId;

val bytes: ByteArray = bot.downloadFile(fileId)
```

See [downloadFile](https://tgbotapi.inmo.dev/docs/dev.inmo.tgbotapi.extensions.api.files/download-file.html) extension docs to get more available options

### Low level or `how does it work?`

You may download file with streams or with downloading into the memory first. On low level you should do several things. They are presented in next snippet:

```kotlin
val bot: TelegramBot;
val fileId: FileId;

val pathedFile: PathedFile = bot.execute(GetFile(fileId))

val downloadedBytes: ByteArray = bot.execute(DownloadFile(pathedFile.filePath))
```

In the snippet above we are getting file `PathedFile` by its `FileId` and use it to download file bytes into memory using `DownloadFile` request.

You may use almost the same way but with byte read channel allocator:

```kotlin
val bot: TelegramBot;
val fileId: FileId;

val pathedFile: PathedFile = bot.execute(GetFile(fileId))

val channelAllocator: ByteReadChannelAllocator = bot.execute(DownloadFileStream(pathedFile.filePath))

val byteReadChannel: ByteReadChannel = channelAllocator()
```

And then you may look into [ByteReadChannel](https://api.ktor.io/ktor-io/io.ktor.utils.io/-byte-read-channel/index.html) docs to get more info about what you can do with that.

> NOTE: **Several useful links**
>
> * [GetFile]("https://tgbotapi.inmo.dev/docs/dev.inmo.tgbotapi.requests.get/-get-file/index.html")
> * [PathedFile]("https://tgbotapi.inmo.dev/docs/dev.inmo.tgbotapi.types.files/-pathed-file/index.html")
> * [DownloadFile]("https://tgbotapi.inmo.dev/docs/dev.inmo.tgbotapi.requests/-download-file/index.html")
> * [DownloadFileStream]("https://tgbotapi.inmo.dev/docs/dev.inmo.tgbotapi.requests/-download-file-stream/index.html")


# Files sending

Of course, in most cases you must be sure that file have correct type.

## FileId and FileUrl

It is the most simple way to send any media in Telegram, but this way have several restrictions:

* The `FileId` which has retrieved for file should not (and probably will not too) equal to the `FileId` retrieved by some other bot
* There is a chance that the file id you are using will be expired with time

## Sending via file

> WARNING: **JS Restrictions**
> Sending via file is accessible from all supported platforms, but there is small note about `JS` - due to restrictions of work with streams and stream-like data (`JS` have no native support of files streaming) on this platform all the files will be loaded inside of RAM before the sending to the telegram services.


Sending via file is available throw the [MultipartFile](https://tgbotapi.inmo.dev/docs/dev.inmo.tgbotapi.requests.abstracts/-multipart-file/index.html). There are several wayt to get it:

* Simple creating via its constructor: `MultipartFile("filename.jpg") { /* here Input allocation */ }`
* Via [asMultiparFile](https://tgbotapi.inmo.dev/docs/dev.inmo.tgbotapi.requests.abstracts/as-multipart-file.html) extension applicable to any `ByteArray`, `ByteReadChannel`, `ByteReadChannelAllocator` or `File` (on any platform)

In most cases, sending via files looks like in the next snippet:

```kotlin
val file: File;

bot.sendDocument(chatId, file.asMultipartFile())
```