import java.io.ByteArrayOutputStream
import java.io.PrintStream

import munit.FunSuite

class Tests extends FunSuite {

  private def run(expr: ExprExt): Value = Interpret.interp(Desugar.desugar(expr), Nil)

  private def capture(expr: ExprExt): (Value, String) = {
    val out = ByteArrayOutputStream()
    val value = Console.withOut(PrintStream(out)) {
      run(expr)
    }
    (value, out.toString)
  }

  test("simple number") {
    assertEquals(run(NumExt(1)), NumV(1))
  }

  test("simple plus") {
    assertEquals(run(AddExt(NumExt(1), NumExt(2))), NumV(3))
  }

  test("print returns its value") {
    val (value, output) = capture(PrintExt(AddExt(NumExt(1), NumExt(2))))
    assertEquals(value, NumV(3))
    assertEquals(output, "3\n")
  }

  test("forcing a constructed list evaluates nested thunks") {
    val expr = ForceExt(
      AppExt(
        LambdaExt(List("x", "y"), ConsExt(IdExt("x"), IdExt("y"))),
        List(NumExt(1), NilExt())
      )
    )
    assertEquals(run(expr), ConsV(NumV(1), NilV()))
  }
}
