# docs

It is a documentation repository for the projects of [InsanusMokrassar](https://github.com/InsanusMokrassar).

## Running locally

### Install poetry

For the ease of dependencies management, this repository uses [Poetry](https://python-poetry.org), a Python project & dependencies manager.
In case you wish to interact with these docs locally, make sure to [install](https://python-poetry.org/docs/#installation) it first.

### Install dependencies

```bash
poetry install --no-root
```


### Build and run

In `Fleet` you may use [these configs](https://github.com/InsanusMokrassar/docs/blob/master/.fleet/run.json) to work with the repo.

To build or run it in vanilla terminal, use the following commands:

```bash
poetry run mkdocs build # Just build the site
```

```bash
poetry run mkdocs serve # Run a local server with the site
```
