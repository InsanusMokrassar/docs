# docs

It is documentation repository for the projects of [InsanusMokrassar](https://github.com/InsanusMokrassar). In case you wish to interact with that lib localy, you will need to install dependnecies and mkdocs:

```bash
pip install mkdocs markdown-callouts mkdocs-autorefs mkdocs-include-dir-to-nav mkdocs-material pymdown-extensions
```

**The snippet above can be outdated. See [publish workflow](https://github.com/InsanusMokrassar/docs/blob/master/.github/workflows/publish.yml) to be sure about building steps**

## Running and build

In `Fleet` you may use [these configs](https://github.com/InsanusMokrassar/docs/blob/master/.fleet/run.json). Localy there are several common options for build:

```bash
mkdocs build
```

^ Will build mkdocs

```bash
mkdocs serve
```

^ Will continuously build __until first failure in build__
