# Behaviour Builder with FSM

Behaviour builder with FSM is based on the MicroUtils FSM. There are several important things in FSM:

* `State` - any object which implements [State](https://microutils.inmo.dev/micro_utils.dokka/dev.inmo.micro_utils.fsm.common/-state/index.html) interface
* `StateHandler` (or [CheckableHandlerHolder](https://microutils.inmo.dev/micro_utils.dokka/dev.inmo.micro_utils.fsm.common/-checkable-handler-holder/index.html)) - the handler of states
* [StatesMachine](https://microutils.inmo.dev/micro_utils.dokka/dev.inmo.micro_utils.fsm.common/-states-machine/index.html) - some machine which work with states and handlers
* [StatesManager](https://microutils.inmo.dev/micro_utils.dokka/dev.inmo.micro_utils.fsm.common/-states-manager/index.html) - simple manager that will solve which states to save and notify about states changes via its flows

`StatesMachine` have two methods:

* `start` which will start work of machine
* `startChain` which will add new state for handling

The most based way to create `StatesMachine` and register `StateHandler`s looks like in the next snippet:

```kotlin
buildFSM<TrafficLightState> {
  	strictlyOn<SomeState> {
      	// state handling
    }
}.start(CoroutineScope(...)).join()
```

> NOTE: **Full example**
> You may find full example of FSM usage in [the tests of FSM in MicroUtils](https://github.com/InsanusMokrassar/MicroUtils/blob/master/fsm/common/src/jvmTest/kotlin/PlayableMain.kt)


So, you must do next steps before you will launch your bot with FSM:

* Create your states. Remember that you may plan to save them, so it is likely you will need to serialize it there
* Create your handlers for your states. In most cases it is useful to use [CheckableHandlerHolder](https://microutils.inmo.dev/micro_utils.dokka/dev.inmo.micro_utils.fsm.common/-checkable-handler-holder/index.html) if you want to use standard states machine
* Solve which states managers to use (the most simple one is the [DefaultStatesManager](https://microutils.inmo.dev/micro_utils.dokka/dev.inmo.micro_utils.fsm.common.managers/-default-states-manager/index.html) with [InMemoryDefaultStatesManager](https://microutils.inmo.dev/micro_utils.dokka/dev.inmo.micro_utils.fsm.common.managers/-in-memory-default-states-manager-repo/index.html))

## Bot with FSM

There are several extensions for `TelegramBot` to create your bot with FSM:

* [buildBehaviourWithFSM](https://tgbotapi.inmo.dev/docs/dev.inmo.tgbotapi.extensions.behaviour_builder/build-behaviour-with-f-s-m.html)
	* [buildBehaviourWithFSMAndStartLongPolling](https://tgbotapi.inmo.dev/docs/dev.inmo.tgbotapi.extensions.behaviour_builder/build-behaviour-with-f-s-m-and-start-long-polling.html)
* [telegramBotWithBehaviourAndFSM](https://tgbotapi.inmo.dev/docs/dev.inmo.tgbotapi.extensions.behaviour_builder/telegram-bot-with-behaviour-and-f-s-m.html)
	* [telegramBotWithBehaviourAndFSMAndStartLongPolling
](https://tgbotapi.inmo.dev/docs/dev.inmo.tgbotapi.extensions.behaviour_builder/telegram-bot-with-behaviour-and-f-s-m-and-start-long-polling.html)

All of them will take as an callback some object with type [CustomBehaviourContextReceiver](https://tgbotapi.inmo.dev/docs/dev.inmo.tgbotapi.extensions.behaviour_builder/index.html#-1892390839%2FClasslikes%2F-1982836883) and will looks like in the next snippet:

```kotlin
telegramBotWithBehaviourAndFSMAndStartLongPolling<YourStateType>("BOT_TOKEN") {
    // here you may use any operations from BehaviourBuilder
    // here you may use any operations from BehaviourContextWithFSMBuilder like strictlyOn and others
}
```

## Examples

* [TelegramBotAPI-examples/FSMBot](https://github.com/InsanusMokrassar/TelegramBotAPI-examples/blob/master/FSMBot/src/main/kotlin/SimpleFSMBot.kt)
* [MicroUtils simple example in the tests](https://github.com/InsanusMokrassar/MicroUtils/blob/master/fsm/common/src/jvmTest/kotlin/PlayableMain.kt)