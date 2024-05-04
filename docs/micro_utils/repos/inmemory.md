# In memory

In memory realizations contains several simple variants:

* [MapCRUDRepo](https://microutils.inmo.dev/micro_utils.dokka/dev.inmo.micro_utils.repos/-map-c-r-u-d-repo/index.html)
* [MapKeyValueRepo](https://microutils.inmo.dev/micro_utils.dokka/dev.inmo.micro_utils.repos/-map-key-value-repo/index.html)
* [MapKeyValuesRepo](https://microutils.inmo.dev/micro_utils.dokka/dev.inmo.micro_utils.repos/-map-key-values-repo/index.html)

Each realization contains `Write` and `Read` parents.

There are several important moments:

## [WriteMapCRUDRepo](https://microutils.inmo.dev/micro_utils.dokka/dev.inmo.micro_utils.repos/-write-map-c-r-u-d-repo/index.html) (and [MapCRUDRepo](https://microutils.inmo.dev/micro_utils.dokka/dev.inmo.micro_utils.repos/-map-c-r-u-d-repo/index.html))

It is an abstract repo with abstract functions: `updateObject` and `createObject`. Both functions require from realization to create `Registered` variant from incoming data and
both will be called within repo [locker.withWriteLock](https://microutils.inmo.dev/micro_utils.dokka/dev.inmo.micro_utils.coroutines/with-write-lock.html).

You may use functions [MapCRUDRepo](https://microutils.inmo.dev/micro_utils.dokka/dev.inmo.micro_utils.repos/-map-c-r-u-d-repo.html) with
passing the callbacks `updateObject` and `createObject` which will be used in their realizations of default [MapCRUDRepo](https://microutils.inmo.dev/micro_utils.dokka/dev.inmo.micro_utils.repos/-map-c-r-u-d-repo/index.html)
variant
