# cse2120-cpl-2025

Local Scala workspace for TU Delft CSE2120 (Concepts of Programming Languages) assignments.

This repo is organized as a set of independent sbt subprojects, one per assignment stage. Each `week*` folder contains its own AST, desugarer, interpreter, and tests. The projects are intentionally separate because the language grows week by week and the runtime model changes across assignments.

## Overview

- `week1`: core untyped expression language with arithmetic, booleans, conditionals, and lists
- `week3`: adds identifiers, functions, application, closures, and `let`
- `week4`: adds mutable state with environments/stores, sequencing, `set`, `let rec`, and boxes
- `week5`: adds static types, a type checker, typed recursion, references, and tuples
- `week6`: switches evaluation to a lazy model with thunks, `force`, lazy lists, and `print`

The root project aggregates:

- `week1`
- `week3`
- `week4`
- `week5`
- `week6`

## Repo Layout

- `build.sbt`: root build and subproject definitions
- `spec-tests/`: imported reference test suites from the course side
- `week*/src/main/Library.scala`: AST, core values, runtime data structures, and exceptions for that week
- `week*/src/main/Desugar.scala`: desugaring from surface AST to core AST
- `week*/src/main/Interpret.scala`: interpreter/runtime semantics
- `week5/src/main/TypeCheck.scala`: static type checker for the typed assignment
- `week*/src/test/Tests.scala`: local `munit` suites for each week

## Week-by-Week Notes

### Week 1

Implements the base language:

- numeric and boolean literals
- unary negation and arithmetic
- numeric comparisons
- boolean operators via desugaring
- `if`, `cond`, `cond ... else`
- list literals and list operations

Key files:

- `week1/src/main/Library.scala`
- `week1/src/main/Desugar.scala`
- `week1/src/main/Interpret.scala`

### Week 3

Extends the base language with lexical scope and first-class functions:

- identifiers
- lambdas and closures
- function application
- `let` as syntactic sugar over lambda application

Runtime model:

- plain value environment `List[(String, Value)]`
- closures capture the environment present at function creation time

### Week 4

Moves from a pure environment model to mutable state:

- pointer environment plus store
- assignment with `set`
- sequencing
- `let rec` through uninitialized placeholders plus mutation
- boxes, unboxing, and box mutation

Runtime model:

- environment maps names to locations
- store maps locations to values
- evaluation threads store updates through subexpressions

### Week 5

Adds a typed surface language:

- `Num`, `Bool`, list, function, reference, and tuple types
- typed lambdas
- typed recursive bindings
- static checks for operators and control flow
- type checking for references and tuples

This week has both:

- desugaring/interpreting for execution
- `TypeCheck.typeOf` for static analysis

### Week 6

Changes the interpreter to a lazy evaluation model:

- arguments are stored as thunks
- lists are lazy
- `force` recursively evaluates thunk-backed values
- `print` observes the current value by forcing as needed
- `let rec` becomes a natural fit for lazy self-reference

Runtime model:

- mutable bindings (`MutBind`)
- thunks memoize once forced
- closures capture mutable environments

## Tests

All weeks use `munit`.

Run all tests:

```bash
sbt week1/test week3/test week4/test week5/test week6/test
```

Run one week:

```bash
sbt week4/test
```

Open the sbt shell:

```bash
sbt
> week5/test
```

## About `spec-tests/`

The files in `spec-tests/` are imported reference suites from the course side. They are useful as a source of expected behavior, but they are not direct drop-in test files for this repo.

Reason:

- some imported suites assume course parser/test infrastructure such as `CPLParser`, `CPLTestSuite`, and `nl.tudelft.cpl.*` packages
- this local repo does not contain that full external harness
- the local `week*/src/test/Tests.scala` files therefore adapt those specs to the code that actually exists here

In practice:

- `week1`, `week3`, and `week6` tests are AST-based `munit` suites
- `week4` and `week5` are also documented and tested locally rather than relying on missing external parser packages

## Prerequisites

- Java 17+ recommended
- sbt 1.x
- Scala 3.3.1 is configured in the build

## Working Style

This repo is best treated as a progression of interpreter implementations rather than one reusable shared library:

- AST definitions intentionally differ between weeks
- each week can change semantic choices independently
- tests should be added to the matching week folder, not the root

## Adding Another Week

To add another stage:

1. Create a new folder such as `week7`
2. Add `src/main` and `src/test`
3. Define a new subproject in `build.sbt`
4. Add it to the root aggregate
5. Keep its runtime and tests self-contained
