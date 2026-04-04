import munit.FunSuite

class Tests extends FunSuite {

  private def typeOf(expr: ExprExt): Type = TypeCheck.typeOf(expr, Nil)

  private def run(expr: ExprExt): Value = Interpret.interp(Desugar.desugar(expr), Nil, Nil)._1

  private def numList(values: Int*): ExprExt = TypedListExt(values.toList.map(NumExt.apply), NumT())

  test("typeOf covers the arithmetic core") {
    assertEquals(typeOf(AddExt(NumExt(1), NumExt(2))), NumT())
    assertEquals(typeOf(LtNumExt(NumExt(3), NumExt(4))), BoolT())
    intercept[TypeException](typeOf(AddExt(NumExt(1), TrueExt())))
  }

  test("typed lists") {
    assertEquals(typeOf(numList(1, 2)), ListT(NumT()))
    assertEquals(typeOf(HeadExt(numList(1, 2))), NumT())
    assertEquals(typeOf(TailExt(numList(1, 2))), ListT(NumT()))
    intercept[TypeException](typeOf(TypedListExt(List(TrueExt(), NumExt(1)), NumT())))
  }

  test("typed functions and application") {
    val identity = TypedLambdaExt(List(TypedParam("x", NumT())), IdExt("x"))
    val listIdentity = TypedLambdaExt(List(TypedParam("x", ListT(NumT()))), IdExt("x"))

    assertEquals(typeOf(identity), FunT(List(NumT()), NumT()))
    assertEquals(typeOf(AppExt(identity, List(NumExt(1)))), NumT())
    assertEquals(typeOf(AppExt(listIdentity, List(numList(1, 2)))), ListT(NumT()))
    intercept[TypeException](typeOf(AppExt(identity, List(TrueExt()))))
  }

  test("let rec type checks") {
    val sum =
      TypedLetRecExt(
        List(
          TypedLetBind(
            "sum",
            FunT(List(NumT()), NumT()),
            TypedLambdaExt(
              List(TypedParam("x", NumT())),
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

    assertEquals(typeOf(sum), NumT())
    intercept[TypeException](typeOf(TypedLetRecExt(List(TypedLetBind("x", BoolT(), NumExt(1))), IdExt("x"))))
  }

  test("references type check") {
    assertEquals(typeOf(BoxExt(NumExt(4))), RefT(NumT()))
    assertEquals(typeOf(UnboxExt(BoxExt(NumExt(4)))), NumT())
    assertEquals(typeOf(LetExt(List(LetBind("b", BoxExt(NumExt(0)))), SetBoxExt(IdExt("b"), NumExt(4)))), NumT())
    intercept[TypeException](typeOf(SetBoxExt(BoxExt(NumExt(1)), TrueExt())))
  }

  test("tuples and projection type check") {
    val tuple = TupleExt(List(NumExt(1), numList(2, 3)))
    assertEquals(typeOf(tuple), TupleT(List(NumT(), ListT(NumT()))))
    assertEquals(typeOf(ProjExt(0, tuple)), NumT())
    assertEquals(typeOf(ProjExt(1, tuple)), ListT(NumT()))
    intercept[TypeException](typeOf(ProjExt(2, TupleExt(List(NumExt(1), NumExt(2))))))
  }

  test("interpreter handles typed recursion") {
    val sum =
      TypedLetRecExt(
        List(
          TypedLetBind(
            "sum",
            FunT(List(NumT()), NumT()),
            TypedLambdaExt(
              List(TypedParam("x", NumT())),
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

    assertEquals(run(sum), NumV(15))
  }

  test("interpreter handles references and sequences") {
    val mutation =
      LetExt(
        List(LetBind("x", NumExt(2)), LetBind("y", NumExt(1))),
        SequenceExt(List(SetExt("x", NumExt(3)), SetExt("y", NumExt(4)), AddExt(IdExt("x"), IdExt("y"))))
      )
    val refs =
      LetExt(
        List(LetBind("b", BoxExt(NumExt(0)))),
        SequenceExt(List(SetBoxExt(IdExt("b"), NumExt(4)), UnboxExt(IdExt("b"))))
      )

    assertEquals(run(mutation), NumV(7))
    assertEquals(run(refs), NumV(4))
  }

  test("interpreter handles tuples and projections") {
    val tuple = TupleExt(List(NumExt(1), numList(2, 3)))
    assertEquals(run(ProjExt(0, tuple)), NumV(1))
    assertEquals(run(ProjExt(1, tuple)), ConsV(NumV(2), ConsV(NumV(3), NilV())))
  }
}
