import munit.FunSuite

class Tests extends FunSuite {

  private def run(expr: ExprExt): Value = Interpret.interp(Desugar.desugar(expr), Nil)

  private def list(values: ExprExt*): ExprExt = ListExt(values.toList)

  test("base language still works") {
    assertEquals(run(AddExt(NumExt(1), NumExt(2))), NumV(3))
    assertEquals(run(IfExt(TrueExt(), NumExt(1), NumExt(2))), NumV(1))
    assertEquals(run(list(NumExt(1), NumExt(2))), ConsV(NumV(1), ConsV(NumV(2), NilV())))
  }

  test("identifiers and lets") {
    val simpleLet = LetExt(List(LetBind("x", NumExt(1))), IdExt("x"))
    val nestedLet =
      LetExt(
        List(LetBind("x", NumExt(3))),
        LetExt(
          List(LetBind("x", NumExt(1)), LetBind("y", IdExt("x"))),
          AddExt(IdExt("x"), IdExt("y"))
        )
      )

    assertEquals(run(simpleLet), NumV(1))
    assertEquals(run(nestedLet), NumV(4))
  }

  test("unbound identifiers throw") {
    intercept[InterpException](run(IdExt("x")))
    intercept[InterpException](run(LetExt(List(LetBind("y", NumExt(1))), IdExt("x"))))
  }

  test("lambda values become closures") {
    run(LambdaExt(Nil, NumExt(1))) match {
      case ClosureV(LambdaC(Nil, NumC(1)), Nil) => ()
      case other => fail(s"unexpected closure value: $other")
    }
  }

  test("application binds arguments and supports shadowing") {
    val firstArg = AppExt(LambdaExt(List("x", "y"), IdExt("x")), List(list(NumExt(1), NumExt(2)), NumExt(3)))
    val secondArg = AppExt(LambdaExt(List("x", "y"), IdExt("y")), List(list(NumExt(1), NumExt(2)), NumExt(3)))
    val shadowing =
      AppExt(
        AppExt(LambdaExt(List("x"), LambdaExt(List("x"), IdExt("x"))), List(NumExt(2))),
        List(NumExt(1))
      )

    assertEquals(run(firstArg), ConsV(NumV(1), ConsV(NumV(2), NilV())))
    assertEquals(run(secondArg), NumV(3))
    assertEquals(run(shadowing), NumV(1))
  }

  test("application checks arity") {
    intercept[InterpException](run(AppExt(LambdaExt(List("x"), IdExt("x")), List(NumExt(1), NumExt(2)))))
    intercept[InterpException](run(AppExt(LambdaExt(List("x", "y"), IdExt("x")), List(NumExt(1)))))
  }

  test("static scope") {
    val expr =
      LetExt(
        List(LetBind("x", NumExt(1))),
        LetExt(
          List(LetBind("f", LambdaExt(Nil, IdExt("x")))),
          LetExt(List(LetBind("x", NumExt(2))), AppExt(IdExt("f"), Nil))
        )
      )

    assertEquals(run(expr), NumV(1))
  }

  test("nested lambdas keep the correct bindings") {
    val keepOuter =
      AppExt(
        AppExt(LambdaExt(List("x"), LambdaExt(List("y"), IdExt("x"))), List(list(NumExt(1), NumExt(2)))),
        List(NumExt(3))
      )
    val keepInner =
      AppExt(
        AppExt(LambdaExt(List("x"), LambdaExt(List("y"), IdExt("y"))), List(list(NumExt(1), NumExt(2)))),
        List(NumExt(3))
      )

    assertEquals(run(keepOuter), ConsV(NumV(1), ConsV(NumV(2), NilV())))
    assertEquals(run(keepInner), NumV(3))
  }

  test("lambdas can be used to implement recursion via self application") {
    val recursiveSum =
      AppExt(
        LambdaExt(List("mk"), AppExt(IdExt("mk"), List(IdExt("mk"), NumExt(5)))),
        List(
          LambdaExt(
            List("self", "n"),
            IfExt(
              LtNumExt(IdExt("n"), NumExt(1)),
              NumExt(0),
              AddExt(
                IdExt("n"),
                AppExt(IdExt("self"), List(IdExt("self"), SubExt(IdExt("n"), NumExt(1))))
              )
            )
          )
        )
      )

    assertEquals(run(recursiveSum), NumV(15))
  }

  test("functions can pass lists through unchanged") {
    val expr = AppExt(LambdaExt(List("x"), IdExt("x")), List(list(NumExt(1), NumExt(2))))
    assertEquals(run(expr), ConsV(NumV(1), ConsV(NumV(2), NilV())))
  }
}
