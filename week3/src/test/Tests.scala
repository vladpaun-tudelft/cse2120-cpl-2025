import munit.FunSuite

class Tests extends FunSuite {

  private def run(expr: ExprExt): Value = Interpret.interp(Desugar.desugar(expr))

  test("A simple number test") {
    assertEquals(run(NumExt(1)), NumV(1))
  }

  test("simple +") {
    assertEquals(run(AddExt(NumExt(1), NumExt(2))), NumV(3))
  }

  test("complex") {
    val expr = SubExt(MulExt(AddExt(NumExt(1), NumExt(2)), AddExt(NumExt(3), NumExt(5))), NumExt(1))
    assertEquals(run(expr), NumV(23))
  }

  test("simple let") {
    val expr = LetExt(
      List(LetBind("x", NumExt(1))),
      IdExt("x")
    )
    assertEquals(run(expr), NumV(1))
  }
}
