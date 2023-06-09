# Keyboards

In the telegram system there are two types of keyboards:

| Reply | Inline |
|:-:|:-:|
| [![Reply keyboard](https://core.telegram.org/file/811140880/1/jS-YSVkDCNQ/b397dfcefc6da0dc70)](https://core.telegram.org/bots#keyboards) | [![Inline keyboard](https://core.telegram.org/file/811140659/1/RRJyulbtLBY/ea6163411c7eb4f4dc)](https://core.telegram.org/bots#inline-keyboards-and-on-the-fly-updating) |
| Keyboard for each user in the chat | Keyboard linked to the certain message |

Low-level way to create keyboard looks like in the next snippet:

```kotlin
ReplyKeyboardMarkup(
    matrix {
        row {
            add(SimpleKeyboardButton("Simple text"))
            // ...
        }
        // ...
    }
)
```

In case you wish to create inline keyboard, it will look like the same as for reply keyboard. But there is another way. The next snippet will create the same keyboard as on the screenshots above:

```kotlin
// reply keyboard
replyKeyboard {
    row {
        simpleButton("7")
        simpleButton("8")
        simpleButton("9")
        simpleButton("*")
    }
    row {
        simpleButton("4")
        simpleButton("5")
        simpleButton("6")
        simpleButton("/")
    }
    row {
        simpleButton("1")
        simpleButton("2")
        simpleButton("3")
        simpleButton("-")
    }
    row {
        simpleButton("0")
        simpleButton(".")
        simpleButton("=")
        simpleButton("+")
    }
}

// inline keyboard
inlineKeyboard {
    row {
        dataButton("Get random music", "random")
    }
    row {
        urlButton("Send music to friends", "https://some.link")
    }
}
```