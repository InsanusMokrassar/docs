# Resources

**Package**: `dev.inmo:micro_utils.resources`

This package aimed to make some multiplatform support for resources of your application. As for now, __there is only
support for strings__. Sample:

```kotlin
object Translations {
    val someVariable = buildStringResource(
        "Sample default string"
    ) {
        IetfLang.German variant lazy "Beispiel für eine Standardzeichenkette"
    }
}
```

In this case, you will be able to use it with next logic:

```kotlin
Translation.someVariable.translation(IetfLang.French) // "Sample default string" as default one
Translation.someVariable.translation(IetfLang.German) // "Beispiel für eine Standardzeichenkette" as available variant
Translation.someVariable.translation(IetfLang.German.DE) // "Beispiel für eine Standardzeichenkette" as available parent variant
```

## Additional opportunities on Android platform

On Android you may use `Configuration` (as well as `Resources` or `Context`) to get translation for current locale. For
example:

```kotlin
val context: Context = // context retrieving

context.translation(Translation.someVariable)
```

## Additional opportunities on JVM platform

On JVM platform you usually may use `Locale.getDefault()` to get `Locale` object and pass it to `translation` extension:

```kotlin
Translation.someVariable.translation(Locale.getDefault())
Translation.someVariable.translation() // Locale.getDefault() hidden
```
