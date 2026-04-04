import java.io.ByteArrayOutputStream
import java.io.PrintStream

import munit.FunSuite

class Tests extends FunSuite {

  private def run(expr: ExprExt): Value = Interpret.interp(Desugar.desugar(expr), Nil)

  private def list(values: ExprExt*): ExprExt = ListExt(values.toList)

  private def force(expr: ExprExt): Value = Interpret.force(run(expr))

  private def capture[A](body: => A): (A, String) = {
    val out = ByteArrayOutputStream()
    val value = Console.withOut(PrintStream(out))(body)
    (value, out.toString)
  }

  test("arithmetic is strict") {
    val (value, output) = capture(run(AddExt(PrintExt(NumExt(1)), NumExt(3))))
    assertEquals(value, NumV(4))
    assertEquals(output, "1\n")
  }

  test("lists are lazy until forced") {
    val (value, output) = capture(run(list(PrintExt(NumExt(1)), PrintExt(NumExt(2)))))
    val (forcedValue, forcedOutput) = capture(Interpret.force(value))
    assertEquals(output, "")
    assertEquals(forcedValue, ConsV(NumV(1), ConsV(NumV(2), NilV())))
    assertEquals(forcedOutput, "1\n2\n")
  }

  test("head and tail do not force elements eagerly") {
    val headExpr = HeadExt(list(PrintExt(NumExt(1)), PrintExt(NumExt(2))))
    val tailExpr = TailExt(list(PrintExt(NumExt(1)), PrintExt(NumExt(2))))

    val (headValue, headOutput) = capture(Interpret.strict(run(headExpr)))
    val (tailValue, tailOutput) = capture(Interpret.force(run(tailExpr)))

    assertEquals(headValue, NumV(1))
    assertEquals(headOutput, "1\n")
    assertEquals(tailValue, ConsV(NumV(2), NilV()))
    assertEquals(tailOutput, "2\n")
  }

  test("unused arguments stay lazy") {
    val expr = AppExt(LambdaExt(List("x"), NumExt(1)), List(PrintExt(NumExt(10))))
    val (value, output) = capture(run(expr))
    assertEquals(value, NumV(1))
    assertEquals(output, "")
  }

  test("forcing a used argument evaluates the thunk") {
    val expr = ForceExt(AppExt(LambdaExt(List("x"), IdExt("x")), List(PrintExt(NumExt(10)))))
    val (value, output) = capture(run(expr))
    assertEquals(value, NumV(10))
    assertEquals(output, "10\n")
  }

  test("if only evaluates the selected branch") {
    val chosen = IfExt(TrueExt(), NumExt(1), PrintExt(NumExt(2)))
    val skipped = IfExt(FalseExt(), PrintExt(NumExt(1)), NumExt(2))

    val (chosenValue, chosenOutput) = capture(force(chosen))
    val (skippedValue, skippedOutput) = capture(force(skipped))

    assertEquals(chosenValue, NumV(1))
    assertEquals(chosenOutput, "")
    assertEquals(skippedValue, NumV(2))
    assertEquals(skippedOutput, "")
  }

  test("force recursively evaluates lazy lists") {
    val expr = ForceExt(list(NumExt(1), AddExt(NumExt(1), NumExt(1)), AddExt(NumExt(2), NumExt(1))))
    assertEquals(run(expr), ConsV(NumV(1), ConsV(NumV(2), ConsV(NumV(3), NilV()))))
  }

  test("let rec supports reverse references under laziness") {
    val expr =
      ForceExt(
        LetRecExt(
          List(LetBind("x", IdExt("y")), LetBind("y", NumExt(1))),
          IdExt("x")
        )
      )
    assertEquals(run(expr), NumV(1))
  }

  test("let rec handles recursion") {
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
    assertEquals(force(sum), NumV(15))
  }

  test("print returns the printed value") {
    val (value, output) = capture(Interpret.force(run(PrintExt(list(NumExt(1), NumExt(2))))))
    assertEquals(value, ConsV(NumV(1), ConsV(NumV(2), NilV())))
    assertEquals(output, "[1 [2 []]]\n")
  }

  test("printing functions uses the closure marker") {
    val (value, output) = capture(run(PrintExt(LambdaExt(List("x"), NumExt(1)))))
    value match {
      case _: MutClosureV => ()
      case other => fail(s"expected closure, got $other")
    }
    assertEquals(output, "<closure>\n")
  }

  test("print inside skipped code does not run") {
    val expr =
      AppExt(
        LambdaExt(List("x"), IfExt(TrueExt(), IdExt("x"), PrintExt(NumExt(20)))),
        List(PrintExt(NumExt(10)))
      )

    val (value, output) = capture(run(expr))
    value match {
      case _: ThunkV => ()
      case other => fail(s"expected thunk, got $other")
    }
    assertEquals(output, "")
  }
}
