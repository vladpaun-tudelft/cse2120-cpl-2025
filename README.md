# cse2120-cpl-2025

Local Scala workspace for TU Delft CSE2120 (Concepts of Programming Languages) assignments.

## Structure

- `week1`: Week 1-2 Paret interpreter/desugarer work.
- `week1/src/main/Library.scala`: Local AST/core/value/exception classes.
- `week1/src/main/Desugar.scala`: Desugarer.
- `week1/src/main/Interpret.scala`: Interpreter.
- `week1/src/test/Tests.scala`: Local tests with `munit`.

## Prerequisites

- Java 17+ (recommended)
- `sbt` (or use an IDE with sbt import)

## Run tests

From repo root:

```bash
sbt week1/test
```

Or open sbt shell:

```bash
sbt
> week1/test
```

## Add future weeks

Create a new folder like `week2` and add another subproject in `build.sbt` similar to `week1`.
