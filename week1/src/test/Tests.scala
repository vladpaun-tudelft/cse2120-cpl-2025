import munit.FunSuite

class Tests extends FunSuite {

  private def run(expr: ExprExt): Value = Interpret.interp(Desugar.desugar(expr))

  test("simple number") {
    assertEquals(run(NumExt(1)), NumV(1))
  }

  test("simple plus") {
    assertEquals(run(AddExt(NumExt(1), NumExt(2))), NumV(3))
  }

  test("complex arithmetic") {
    val expr = SubExt(MulExt(AddExt(NumExt(1), NumExt(2)), AddExt(NumExt(3), NumExt(5))), NumExt(1))
    assertEquals(run(expr), NumV(23))
  }

  test("adding non-numbers throws") {
    intercept[InterpException] {
      run(AddExt(NumExt(1), TrueExt()))
    }
  }

  test("cond with else") {
    val expr = CondEExt(
      List(
        CondBranch(LtNumExt(NumExt(1), NumExt(0)), NumExt(0))
      ),
      NumExt(2)
    )
    assertEquals(run(expr), NumV(2))
  }

  test("list literal desugars to nested cons") {
    val expr = ListExt(List(AddExt(NumExt(1), NumExt(1)), AddExt(NumExt(2), NumExt(2))))
    assertEquals(run(expr), ConsV(NumV(2), ConsV(NumV(4), NilV())))
  }
}
