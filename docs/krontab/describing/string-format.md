# String format

As in `crontab` util, this library have almost the same format of string:

|  | Seconds | Minutes | Hours | Days of months | Months | Years | Timezone Offset | Week days | Milliseconds |
| --: | :-: | :-: | :-: | :-: | :-: | :-: | :-: | :-: | :-: |
| Range | 0..59 | 0..59 | 0..23 | 0..30 | 0..11 | Any `Int` | Any `Int` | 0..6 | 0..999 |
| Suffix | - | - | - | - | - | - | `o` | `w` | `ms` |
| Optional | ❌ | ❌ | ❌ | ❌ | ❌ | ✅ | ✅ | ✅ | ✅ |
| [Full syntax](#bkmrk-supported-syntax) support | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ❌ | ✅ | ✅ |
| Position | 0 | 1 | 2 | 3 | 4 | Any after months | Any after months | Any after months | Any after months |
| Examples | `0`, `*/15`, `30` | `0`, `*/15`, `30` | `0`, `*/15`, `22` | `0`, `*/15`, `30` | `0`, `*/5`, `11` | `0`, `*/15`, `30` | `60o` (UTC+1) | `0w`, `*/2w`, `4w` | `0ms`, `*/150ms`, `300ms` |

Example with almost same description:

```
/-------------------- (0-59) ············ Seconds
| /------------------ (0-59) ············ Minutes
| | /---------------- (0-23) ············ Hours
| | | /-------------- (0-30) ············ Days of months
| | | | /------------ (0-11) ············ Months
| | | | | /---------- (optional, any int) Year
| | | | | | /-------- (optional) ········ Timezone offset
| | | | | | |  /----- (optional, 0-6) ··· Week days
| | | | | | |  |  /-- (optional, 0-999) · Milliseconds (0 by default)
* * * * * * 0o *w 0ms
```

Years, timezone, week days and milliseconds are optional settings. Next snippets are equal:

```
*/15 * * * *
*/15 * * * * * // with year
*/15 * * * * * 0ms // with year and milliseconds
```

### Supported syntax

Currently the library support next syntax for date/time elements:

* `{int}-{int}` - ranges
* `{int}/{int}` - start/step
* `*/{int}` - every {int}
* `{int}` - just at the time
* `{other_element},{other_element}` - listing
* `F` or `f` - first possible value
* `L` or `l` - last possible value (last day of month, for example)

#### Ranges

Ranges are working like common `rangeTo` (or `..`) in kotlin:

```
0-5 * * * *
```

In the example above scheduler will trigger every second from the beginning of the minute up to fifth second of minute.

#### Start/Step

Start/step is a little bit more complicated syntax. It means `start from the first element, repeat triggering every second element`. Examples:

```
5/15 * * * *
```

Means that each minute starting from fifth second it will repeat triggering every fifteenth second: `5, 20, 35, 50`.

#### Every

Every is more simple syntax and could be explained as a shortcut for `0/{int}`. Example:

```
*/15 * * * *
```

Means that each minute it will repeat triggering every fifteenth second: `0, 15, 30, 45`.

#### Just at the time

The most simple syntax. It means, that scheduler will call triggering every time when element was reached:

```
15 * * * *
```

Means that each minute scheduler will call triggering at the fifteenth second.

#### Listing

All the previous elements can be combined with listing. Lets just see several examples:

```
0,10 * * * *
```

Will trigger every minute at the `0` and `10` seconds (see [Just at the time](#just-at-the-time))

```
0-5,10 * * * *
```

Will trigger every minute from `0` to `5` seconds and at the `10` seconds (see [Ranges](#ranges))

### Examples

* `0/5 * * * *` for every five seconds triggering
* `0/5,L * * * *` for every five seconds triggering and on 59 second
* `0/15 30 * * *` for every 15th seconds in a half of each hour
* `0/15 30 * * * 500ms` for every 15th seconds in a half of each hour when milliseconds equal to 500
* `1 2 3 F,4,L 5` for triggering in near first second of second minute of third hour of first, fifth and last days of may
* `1 2 3 F,4,L 5 60o` for triggering in near first second of second minute of third hour of first, fifth and last days of may with timezone UTC+01:00
* `1 2 3 F,4,L 5 60o 0-2w` for triggering in near first second of second minute of third hour of first, fifth and last days of may in case if it will be in Sunday-Tuesday week days with timezone UTC+01:00
* `1 2 3 F,4,L 5 2021` for triggering in near first second of second minute of third hour of first, fifth and last days of may of 2021st year
* `1 2 3 F,4,L 5 2021 60o` for triggering in near first second of second minute of third hour of first, fifth and last days of may of 2021st year with timezone UTC+01:00
* `1 2 3 F,4,L 5 2021 60o 0-2w` for triggering in near first second of second minute of third hour of first, fifth and last days of may of 2021st year if it will be in Sunday-Tuesday week days with timezone UTC+01:00
* `1 2 3 F,4,L 5 2021 60o 0-2w 500ms` for triggering in near first second of second minute of third hour of first, fifth and last days of may of 2021st year if it will be in Sunday-Tuesday week days with timezone UTC+01:00 when milliseconds will be equal to 500