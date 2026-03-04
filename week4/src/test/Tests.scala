import nl.tudelft.cpl.test.*
import nl.tudelft.cpl.language.Library.*

class Tests extends CPLTestSuite {

    suite("Student tests") {
        // Your tests here
        unitTest("A simple number test") {
            val result = interp("1")
            assertResult(NumV(1))(result)
            // or assert(result == NumV(1)), but that gives less clear error messages
        }

        unitTest("simple +") {
            val result = interp("1 + 2")
            assertResult(NumV(3))(result)
        }

        unitTest("complex") {
            val result = interp(
                """
                  | (1 + 2) *
                  | (3 + 5) -
                  | 1
                  |""".stripMargin)
            assertResult(NumV(3*8-1))(result)
        }

        unitTest("simple let") {
            val result = interp(
                """
                  | let x = 1 in
                  |   x
                  |""".stripMargin
            )
            assertResult(NumV(1))(result)
        }

        unitTest("simple let rec") {
            val result = interp(
                """
                  | let rec x = 1 in
                  |   x
                  |""".stripMargin
            )
            assertResult(NumV(1))(result)
        }

        unitTest("expect exception") {
            assertThrows[InterpException] {
                interp("1 + true")
            }
        }
    }

    def desugar(expr: String): ExprC = {
        val parsed = CPLParser.parseUntyped(expr)
        Desugar.desugar(parsed)
    }

    def interp(expr: String): Value = {
        val desugared = desugar(expr)
        Interpret.interp(desugared, Nil, Nil)._1
    }

}
