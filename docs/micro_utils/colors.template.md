group=dev.inmo
artifact=micro_utils.colors.common
package=$group.$artifact
central_package=$group/$artifact

# Colors [![Maven Central](https://maven-badges.herokuapp.com/maven-central/${central_package}/badge.svg)](https://maven-badges.herokuapp.com/maven-central/${central_package})

* `Group`: `$group`
* `ArtifactId`: `$artifact`

Adding dependency:

```groovy
implementation "$group:$artifact:latest"
```

> INFO:
>
> All the samples below will represent `HEXAColor` with `r==0xaa`, `g==0xff`, `b==0x00` and `a==0xff`

This package contains mainly one file: [HEXAColor](https://microutils.inmo.dev/micro_utils.dokka/dev.inmo.micro_utils.colors.common/-h-e-x-a-color/index.html). This file
contains unified color with HEXA format. It consumes `UInt` by default constructor and r/g/b/a parameters in other main constructors:

```kotlin
HEXAColor(0xaaff00ffu) // 0xRGBAu as UInt
HEXAColor(r = 0xaa, g = 0xff, b = 0x00, a = 0xff)
HEXAColor(r = 0xaa, g = 0xff, b = 0x00, aOfOne = 1f)
```

Besides, you may use one of converters:

```kotlin
HEXAColor.fromAhex(0xffaaff00u) // 0xARGBu as UInt
HEXAColor.parse("rgba(aa, ff, 00, ff)")
HEXAColor.parse("rgba(aa, ff, 00)")
HEXAColor.parse("#af0")
HEXAColor.parse("#af0f")
HEXAColor.parse("#aaff00")
HEXAColor.parse("#aaff00ff")
```


