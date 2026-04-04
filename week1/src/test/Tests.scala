import munit.FunSuite

class Tests extends FunSuite {

  private def run(expr: ExprExt): Value = Interpret.interp(Desugar.desugar(expr))

  private def list(values: ExprExt*): ExprExt = ListExt(values.toList)

  test("literals and arithmetic") {
    assertEquals(run(NumExt(1)), NumV(1))
    assertEquals(run(TrueExt()), BoolV(true))
    assertEquals(run(FalseExt()), BoolV(false))
    assertEquals(run(NegExt(NumExt(2))), NumV(-2))
    assertEquals(run(AddExt(NumExt(2), NumExt(3))), NumV(5))
    assertEquals(run(SubExt(NumExt(4), NumExt(2))), NumV(2))
    assertEquals(run(MulExt(NumExt(2), NumExt(3))), NumV(6))
  }

  test("numeric operators reject non-numbers") {
    intercept[InterpException](run(NegExt(TrueExt())))
    intercept[InterpException](run(AddExt(NumExt(2), TrueExt())))
    intercept[InterpException](run(SubExt(NilExt(), NumExt(3))))
    intercept[InterpException](run(MulExt(NilExt(), list(NumExt(1), NumExt(2)))))
  }

  test("comparisons") {
    assertEquals(run(EqNumExt(NumExt(3), NumExt(3))), BoolV(true))
    assertEquals(run(EqNumExt(NumExt(3), NumExt(2))), BoolV(false))
    assertEquals(run(LtNumExt(NumExt(3), NumExt(4))), BoolV(true))
    assertEquals(run(LtNumExt(NumExt(4), NumExt(3))), BoolV(false))
    assertEquals(run(GtNumExt(NumExt(4), NumExt(3))), BoolV(true))
    assertEquals(run(GtNumExt(NumExt(3), NumExt(4))), BoolV(false))
  }

  test("comparisons reject non-numbers") {
    intercept[InterpException](run(EqNumExt(NumExt(2), TrueExt())))
    intercept[InterpException](run(LtNumExt(NilExt(), NumExt(3))))
    intercept[InterpException](run(GtNumExt(NilExt(), list(NumExt(1), NumExt(2)))))
  }

  test("boolean operators and laziness") {
    assertEquals(run(NotExt(TrueExt())), BoolV(false))
    assertEquals(run(NotExt(FalseExt())), BoolV(true))
    assertEquals(run(AndExt(TrueExt(), NumExt(3))), NumV(3))
    assertEquals(run(AndExt(FalseExt(), HeadExt(NilExt()))), BoolV(false))
    assertEquals(run(OrExt(FalseExt(), NumExt(3))), NumV(3))
    assertEquals(run(OrExt(TrueExt(), HeadExt(NilExt()))), BoolV(true))
  }

  test("boolean operators reject non-bools") {
    intercept[InterpException](run(NotExt(NilExt())))
    intercept[InterpException](run(AndExt(NilExt(), TrueExt())))
    intercept[InterpException](run(OrExt(NilExt(), list(NumExt(1), NumExt(2)))))
  }

  test("if only evaluates the selected branch") {
    assertEquals(run(IfExt(TrueExt(), NumExt(1), HeadExt(NilExt()))), NumV(1))
    assertEquals(run(IfExt(FalseExt(), HeadExt(NilExt()), NumExt(2))), NumV(2))
    intercept[InterpException](run(IfExt(NumExt(1), TrueExt(), FalseExt())))
  }

  test("cond without else uses the first true branch and fails when none match") {
    val cond = CondExt(
      List(
        CondBranch(FalseExt(), NumExt(0)),
        CondBranch(TrueExt(), TrueExt()),
        CondBranch(TrueExt(), NumExt(2))
      )
    )
    assertEquals(run(cond), BoolV(true))

    intercept[InterpException] {
      run(CondExt(List(CondBranch(FalseExt(), NumExt(1)), CondBranch(FalseExt(), NumExt(2)))))
    }
  }

  test("cond with else") {
    val cond = CondEExt(
      List(
        CondBranch(FalseExt(), NumExt(1)),
        CondBranch(FalseExt(), TrueExt())
      ),
      NumExt(2)
    )
    assertEquals(run(cond), NumV(2))
    intercept[InterpException](run(CondEExt(List(CondBranch(NumExt(0), NumExt(1))), NumExt(2))))
  }

  test("lists and cons") {
    assertEquals(run(NilExt()), NilV())
    assertEquals(run(ConsExt(NumExt(1), NumExt(2))), ConsV(NumV(1), NumV(2)))
    assertEquals(run(list(NumExt(1), NumExt(2))), ConsV(NumV(1), ConsV(NumV(2), NilV())))
    assertEquals(run(list()), NilV())
  }

  test("head, tail, isNil, isList") {
    assertEquals(run(HeadExt(list(NumExt(2), NumExt(1)))), NumV(2))
    assertEquals(run(TailExt(list(NumExt(2), NumExt(1)))), ConsV(NumV(1), NilV()))
    assertEquals(run(IsNilExt(list(NumExt(1)))), BoolV(false))
    assertEquals(run(IsNilExt(list())), BoolV(true))
    assertEquals(run(IsListExt(list(NumExt(1)))), BoolV(true))
    assertEquals(run(IsListExt(NilExt())), BoolV(true))
    assertEquals(run(IsListExt(NumExt(1))), BoolV(false))
  }

  test("list operators reject invalid inputs") {
    intercept[InterpException](run(HeadExt(NumExt(1))))
    intercept[InterpException](run(HeadExt(NilExt())))
    intercept[InterpException](run(TailExt(NumExt(1))))
    intercept[InterpException](run(TailExt(NilExt())))
    intercept[InterpException](run(IsNilExt(NumExt(1))))
    intercept[InterpException](run(IsNilExt(TrueExt())))
  }

  test("complex expression") {
    val expr =
      SubExt(
        MulExt(AddExt(NumExt(1), NumExt(2)), AddExt(NumExt(3), NumExt(5))),
        NumExt(1)
      )
    assertEquals(run(expr), NumV(23))
  }
}
