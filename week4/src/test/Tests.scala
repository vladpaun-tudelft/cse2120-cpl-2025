import munit.FunSuite

class Tests extends FunSuite {

  private def run(expr: ExprExt): Value = Interpret.interp(Desugar.desugar(expr), Nil, Nil)._1

  private def list(values: ExprExt*): ExprExt = ListExt(values.toList)

  test("arithmetic and let") {
    val expr =
      LetExt(
        List(LetBind("x", NumExt(1))),
        SubExt(
          MulExt(AddExt(IdExt("x"), NumExt(2)), AddExt(NumExt(3), NumExt(5))),
          NumExt(1)
        )
      )
    assertEquals(run(expr), NumV(23))
  }

  test("mutation and sequencing") {
    val expr =
      LetExt(
        List(LetBind("x", NumExt(2)), LetBind("y", NumExt(1))),
        SequenceExt(List(SetExt("x", NumExt(3)), SetExt("y", NumExt(4)), AddExt(IdExt("x"), IdExt("y"))))
      )
    assertEquals(run(expr), NumV(7))
  }

  test("store effects thread through arithmetic") {
    val leftThenRead =
      LetExt(List(LetBind("x", NumExt(1))), AddExt(SetExt("x", NumExt(2)), IdExt("x")))
    val bothSides =
      LetExt(
        List(LetBind("x", NumExt(1))),
        SequenceExt(List(AddExt(SetExt("x", NumExt(2)), SetExt("x", NumExt(3))), IdExt("x")))
      )

    assertEquals(run(leftThenRead), NumV(4))
    assertEquals(run(bothSides), NumV(3))
  }

  test("store effects thread through conditionals") {
    val expr =
      LetExt(
        List(LetBind("x", NumExt(1))),
        SequenceExt(
          List(
            IfExt(
              SequenceExt(List(SetExt("x", NumExt(2)), TrueExt())),
              SetExt("x", NumExt(4)),
              SetExt("x", NumExt(3))
            ),
            IdExt("x")
          )
        )
      )
    assertEquals(run(expr), NumV(4))
  }

  test("let rec") {
    val sum =
      LetRecExt(
        List(
          LetBind(
            "sum",
            LambdaExt(
              List("x"),
              IfExt(
                LtNumExt(IdExt("x"), NumExt(1)),
                NumExt(0),
                AddExt(IdExt("x"), AppExt(IdExt("sum"), List(SubExt(IdExt("x"), NumExt(1)))))
              )
            )
          )
        ),
        AppExt(IdExt("sum"), List(NumExt(5)))
      )
    val reverseReference =
      LetRecExt(List(LetBind("x", IdExt("y")), LetBind("y", NumExt(1))), IdExt("x"))

    assertEquals(run(sum), NumV(15))
    assertEquals(run(reverseReference), UninitialisedV())
  }

  test("boxes") {
    run(BoxExt(NumExt(4))) match {
      case BoxV(_) => ()
      case other => fail(s"expected a box, got $other")
    }

    assertEquals(run(UnboxExt(BoxExt(NumExt(4)))), NumV(4))
    assertEquals(run(LetExt(List(LetBind("b", BoxExt(NumExt(0)))), SetBoxExt(IdExt("b"), NumExt(4)))), NumV(4))
    intercept[InterpException](run(SetBoxExt(NumExt(1), NumExt(2))))
  }

  test("static scope") {
    val expr =
      LetExt(
        List(LetBind("x", NumExt(1))),
        LetExt(
          List(LetBind("f", LambdaExt(List("y"), IdExt("x")))),
          LetExt(List(LetBind("x", NumExt(2))), AppExt(IdExt("f"), List(NumExt(0))))
        )
      )
    assertEquals(run(expr), NumV(1))
  }

  test("lists still work with stateful evaluation") {
    val expr =
      LetExt(
        List(LetBind("x", NumExt(1))),
        SequenceExt(
          List(
            HeadExt(SequenceExt(List(SetExt("x", NumExt(2)), list(NumExt(1))))),
            IdExt("x")
          )
        )
      )

    assertEquals(run(ConsExt(NumExt(1), NumExt(2))), ConsV(NumV(1), NumV(2)))
    assertEquals(run(expr), NumV(2))
  }
}
