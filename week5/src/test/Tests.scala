import nl.tudelft.cpl.test.*
import nl.tudelft.cpl.language.Library.*

class Tests extends CPLTestSuite {

  suite("Student tests") {
    unitTest("A simple number test") {
      val result = typeOf("1")
      assert(result == NumT())
    }
  }

  def typeOf(expr: String): Type = {
    val parsed = CPLParser.parse(expr)
    TypeCheck.typeOf(parsed, Nil)
  }

  def desugar(expr: String): ExprC = {
    val parsed = CPLParser.parse(expr)
    Desugar.desugar(parsed)
  }

  def interp(expr: String): Value = {
    val desugared = desugar(expr)
    Interpret.interp(desugared, Nil, Nil)._1
  }
}
