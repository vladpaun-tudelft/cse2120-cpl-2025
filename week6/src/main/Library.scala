import java.util.UUID.randomUUID

trait ExprExt

case class NumExt(value: Int) extends ExprExt
case class TrueExt() extends ExprExt
case class FalseExt() extends ExprExt

case class NegExt(e: ExprExt) extends ExprExt
case class AddExt(l: ExprExt, r: ExprExt) extends ExprExt
case class SubExt(l: ExprExt, r: ExprExt) extends ExprExt
case class MulExt(l: ExprExt, r: ExprExt) extends ExprExt

case class EqNumExt(l: ExprExt, r: ExprExt) extends ExprExt
case class LtNumExt(l: ExprExt, r: ExprExt) extends ExprExt
case class GtNumExt(l: ExprExt, r: ExprExt) extends ExprExt

case class NotExt(e: ExprExt) extends ExprExt
case class AndExt(l: ExprExt, r: ExprExt) extends ExprExt
case class OrExt(l: ExprExt, r: ExprExt) extends ExprExt

case class IfExt(c: ExprExt, t: ExprExt, f: ExprExt) extends ExprExt
case class CondExt(branches: List[CondBranch]) extends ExprExt
case class CondEExt(branches: List[CondBranch], e: ExprExt) extends ExprExt
case class CondBranch(c: ExprExt, e: ExprExt)

case class NilExt() extends ExprExt
case class ConsExt(head: ExprExt, tail: ExprExt) extends ExprExt
case class ListExt(values: List[ExprExt]) extends ExprExt

case class HeadExt(e: ExprExt) extends ExprExt
case class TailExt(e: ExprExt) extends ExprExt
case class IsNilExt(e: ExprExt) extends ExprExt
case class IsListExt(e: ExprExt) extends ExprExt

case class IdExt(name: String) extends ExprExt
case class LambdaExt(params: List[String], body: ExprExt) extends ExprExt
case class AppExt(callee: ExprExt, args: List[ExprExt]) extends ExprExt
case class LetExt(binds: List[LetBind], body: ExprExt) extends ExprExt
case class LetRecExt(binds: List[LetBind], body: ExprExt) extends ExprExt
case class LetBind(name: String, value: ExprExt)

case class ForceExt(e: ExprExt) extends ExprExt

case class PrintExt(e: ExprExt) extends ExprExt

trait ExprC

case class NumC(value: Int) extends ExprC
case class TrueC() extends ExprC
case class FalseC() extends ExprC

case class AddC(l: ExprC, r: ExprC) extends ExprC
case class MulC(l: ExprC, r: ExprC) extends ExprC

case class EqNumC(l: ExprC, r: ExprC) extends ExprC
case class LtNumC(l: ExprC, r: ExprC) extends ExprC

case class IfC(c: ExprC, t: ExprC, f: ExprC) extends ExprC
case class UndefinedC() extends ExprC

case class NilC() extends ExprC
case class ConsC(head: ExprC, tail: ExprC) extends ExprC

case class HeadC(e: ExprC) extends ExprC
case class TailC(e: ExprC) extends ExprC
case class IsNilC(e: ExprC) extends ExprC
case class IsListC(e: ExprC) extends ExprC

case class IdC(name: String) extends ExprC
case class LambdaC(params: List[String], body: ExprC) extends ExprC
case class AppC(callee: ExprC, args: List[ExprC]) extends ExprC
case class LetRecC(binds: List[LetRecBind], body: ExprC) extends ExprC
case class LetRecBind(name: String, value: ExprC)

case class ForceC(e: ExprC) extends ExprC

case class PrintC(e: ExprC) extends ExprC

trait Value

case class NumV(value: Int) extends Value
case class BoolV(value: Boolean) extends Value
case class MutClosureV(f: LambdaC, nv: MutEnvironment) extends Value {
  override def toString: String = s"<closure: $f>"
  override def hashCode(): Int = randomUUID().hashCode()
}
case class ThunkV(var value: Either[(ExprC, MutEnvironment), Value]) extends Value {
  override def toString: String = value match {
    case Left((e, _)) => s"ThunkV($e, <env>)"
    case Right(v) => s"ThunkV($v)"
  }
  override def hashCode(): Int = randomUUID().hashCode()
}
case class UninitialisedV() extends Value

sealed trait ListV extends Value
case class NilV() extends ListV
case class ConsV(head: Value, tail: Value) extends ListV

case class MutBind(name: String, var value: Value)
type MutEnvironment = List[MutBind]

case class InterpException(message: String) extends RuntimeException(message)
