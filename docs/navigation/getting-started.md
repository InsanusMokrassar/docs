# Getting started (TBD)

[![Maven Central](https://img.shields.io/maven-central/v/dev.inmo/navigation.core?label=navigation&style=flat-square)](https://github.com/InsanusMokrassar/navigation)

Traditionally, you need to add dependency to your project. Currently, there are two types of artifacts:

* `Core` - only necessary tools for your projects
* `MVVM` - Model-View-ViewModel architecture tools + `Core` components

| Artifact | Purpose                                                     | Dependency                                                      |
|:--------:|-------------------------------------------------------------|-----------------------------------------------------------------|
|  `Core`  | Only necessary tools for your projects                      | `implementation "dev.inmo:navigation.core:$navigation_version"` |
|  `MVVM`  | Model-View-ViewModel architecture tools + `Core` components | `implementation "dev.inmo:navigation.mvvm:$navigation_version"` |

# Get started

After you have added your dependency, you should initialize navigation. There are several important things:

1. `Config` - it is an instance of any class which extending the `NavigationNodeDefaultConfig` in common case
2. `Factory` - usually object which may create a node or some required part for node

For example: lets imagine that we have a node `Main`. Here what should we do to create a node and make it workable in
navigation:

```kotlin
data class MainConfig(
    // this id will be used to search an html element by id in JS
    // and Fragment by tag in Android
    override val id: String = "main"
) : NavigationNodeDefaultConfig
```

Both `JS` and `Android` platforms require `ViewModel` for their `MVVM` node variants, but it can be common as well as
`MainConfig`:

```kotlin
class MainViewModel(
    node: NavigationNode<MainConfig, NavigationNodeDefaultConfig>
) : ViewModel(
    node
)
```

### JS part

```kotlin
// Core variant without MVVM or Compose
class MainNode(
    config: MainConfig,
    chain: NavigationChain<NavigationNodeDefaultConfig>,
) : JsNavigationNode<MainConfig, NavigationNodeDefaultConfig>(
    chain,
    config
) {
    // Some code
    // In htmlElementStateFlow will be found `HTMLElement` where node should be binded
}
// MVVM Compose variant
class MainNodeView(
    config: MainConfig,
    chain: NavigationChain<NavigationNodeDefaultConfig>,
) : View<MainConfig, MainViewModel>(
    config,
    chain
) {
    // Some code
    // In htmlElementStateFlow will be found `HTMLElement` where node should be binded
    
    @Composable
    override onDraw() {
        Text("Hello world")
    }
}

object MainNodeFactory : NavigationNodeFactory<NavigationNodeDefaultConfig> {
    override fun createNode(
        navigationChain: NavigationChain<NavigationNodeDefaultConfig>,
        config: NavigationNodeDefaultConfig
    ): NavigationNode<out NavigationNodeDefaultConfig, NavigationNodeDefaultConfig>? = if (config is MainConfig) {
        MainNode(config, navigationChain) // Or `MainNodeView(config, chain)` for MVVM
    } else {
        null
    }
}
```

---

Data below is under TBD

### Android

In Android there is one important note: you will not directly work with nodes. In fact it will be required to create
special `NodeFragment`:

```kotlin
// Core variant
class MainFragment : NodeFragment<MainConfig, NavigationNodeDefaultConfig>() {
    // Your code
    // Here will be available: node with type `AndroidFragmentNode`, config: `MainConfig`
}
// MVVM Variant
class MainViewFragment : ViewFragment<MainViewModel, MainConfig>() {
    // Will be available also `viewModel` via koin `lazyInject`
    override val viewModelClass
        get() = MainViewModel::class
}
```


Initialization is different on the platforms, so, lets take a look at each one.

## JS

In `JavaScript` it looks like:

```kotlin
initNavigation<NavigationNodeDefaultConfig>(
    ConfigHolder.Chain( // (1)
        ConfigHolder.Node( // (2)
            MainConfig(), // (3)
            null, // (4)
            listOf() // (5)
        ),
    ),
    configsRepo = CookiesNavigationConfigsRepo( // (6)
        Json {
            ignoreUnknownKeys = true
            serializersModule = SerializersModule {
                polymorphic(NavigationNodeDefaultConfig::class, MainConfig::class, MainConfig.serializer()) // (7)
            }
        },
        ConfigHolder.serializer(NavigationNodeDefaultConfig::class.serializer())
    ),
    dropRedundantChainsOnRestore = true, // (8)
    nodesFactory = MainNodeFactory, // (9)
)
```

1. Creating of default root chain config holder. It must be root chain because of the chain work with statuses changes
2. Creating of default root node config holder. This type contains config of the first node, its subnode and subchains lists
3. Default root config
4. Subnode of root node. In this case it is `null`, but can be any `ConfigHolder.Node`
5. Subchains of default root node
6. Configurations changes repo saver. By default it is `cookies` (`localStorage`) store
7. Register config for serialization to let configs repo serializer to know how to serialize `MainConfig`
8. Flag that the chains without any node will be dropped automatically
9. In fact here can be factory aggregator, for example:
```kotlin
val factories: List<NavigationNodeFactory<NavigationNodeDefaultConfig>>
NavigationNodeFactory<NavigationNodeDefaultConfig> { chainHolder, config ->
    factories.firstNotNullOfOrNull { it.createNode(chainHolder, config) }
}
```
