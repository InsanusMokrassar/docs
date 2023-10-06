# Opportunities out of the box

There are several important opportunities out of the box:

* Database access
* Config access
* Bot setup
* Json format
* Bot itself

## Database access

You may access database in your plugin via koin in `setupBotPlugin` or as parameter in `setupDI`:

```kotlin
object YourPlugin : Plugin {
    // ...
    override fun Module.setupDI(
        database: Database, // database
        params: JsonObject
    ) {
        // ...
    }
}
```

It is simple [Exposed](https://github.com/JetBrains/Exposed) database and you may use it in your tables.

## Config access

As you may see, in the `setupDI` function we also have `params` parameter with source configuration json. In case
you wish to declare and work with your own config in plugin, you may use next snippet:

```kotlin
object YourPlugin : Plugin {
    @Serializable
    data class MyConfig(
        val param1: String,
        val param2: Int,
    )
    // ...
    override fun Module.setupDI(
        database: Database, // database
        params: JsonObject
    ) {
        single {
            get<Json>().decodeFromJsonElement(MyConfig.serializer(), params) // register from root (1)
        }
        // OR
        single {
            get<Json>().decodeFromJsonElement(MyConfig.serializer(), params["yourplugin"]!!) // register from field "yourplugin" (2)
        }
        // ...
    }

    override suspend fun BehaviourContext.setupBotPlugin(
        koin: Koin
    ) {
        koin.get<MyConfig>() // getting of registered config
    }
}
```

1. In this case your config will looks like:
```json
{
  "params1": "SomeString",
  "params2": 42
}
```
2. In this case your config will looks like:
```json
{
  "yourplugin": {
    "params1": "SomeString",
    "params2": 42
  }
}
```

## Bot setup

Out of the box you may setup several things in bot:

<div class="annotate" markdown>

* `StatesManager<State>` (1)
* `DefaultStatesManagerRepo<State>` (2)
* Any amount of `OnStartContextsConflictResolver` (3)
* Any amount of `OnUpdateContextsConflictResolver` (4)
* `StateHandlingErrorHandler<State>` (5)

</div>

1. For this use next code in `setupDI`:
```kotlin
// Your plugin
    override fun Module.setupDI(database: Database, params: JsonObject) {
        single<StatesManager<State>> {
            // Your StatesManager<State> initialization
        }
    }
```
2. For this use next code in `setupDI`:
```kotlin
// Your plugin
    override fun Module.setupDI(database: Database, params: JsonObject) {
        single<DefaultStatesManagerRepo<State>> {
            // Your DefaultStatesManagerRepo<State> initialization
        }
    }
```
3. You may declare any amount of `OnStartContextsConflictResolver`. `PlaguBot` will take first non-null result of
resolvers from DI and use in default states manager. To declare, use next snippet:
```kotlin
// Your plugin
    override fun Module.setupDI(database: Database, params: JsonObject) {
        singleWithRandomQualifier<OnStartContextsConflictResolver> {
            OnStartContextsConflictResolver { old, new ->
                // null|true|false
            }
        }
    }
```
4. You may declare any amount of `OnUpdateContextsConflictResolver`. `PlaguBot` will take first non-null result of
resolvers from DI and use in default states manager. To declare, use next snippet:
```kotlin
// Your plugin
    override fun Module.setupDI(database: Database, params: JsonObject) {
        singleWithRandomQualifier<OnUpdateContextsConflictResolver> {
            OnUpdateContextsConflictResolver { old, new, currentStateOnContext ->
                // null|true|false
            }
        }
    }
```
5. You may declare only one `StateHandlingErrorHandler<State>`. This handler will be called each time when some state
will be handled with exception and may return `null` or new state instead old one:
```kotlin
// Your plugin
    override fun Module.setupDI(database: Database, params: JsonObject) {
        single<StateHandlingErrorHandler<State>> {
            StateHandlingErrorHandler<State> { state, throwable ->
                // null or State
            }
        }
    }
```
