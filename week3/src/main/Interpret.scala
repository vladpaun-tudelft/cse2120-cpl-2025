object Interpret {

  private def numBinOp(l: ExprC, r: ExprC)(op: (Int, Int) => Int): Value =
    NumV(op(asNum(interp(l)), asNum(interp(r))))

  private def numCmpOp(l: ExprC, r: ExprC)(op: (Int, Int) => Boolean): Value =
    BoolV(op(asNum(interp(l)), asNum(interp(r))))

  def interp(e: ExprC): Value = e match {
    case NumC(value) => NumV(value)
    case TrueC() => BoolV(true)
    case FalseC() => BoolV(false)

    case AddC(l, r) => numBinOp(l, r)(_ + _)
    case MulC(l, r) => numBinOp(l, r)(_ * _)

    case EqNumC(l, r) => numCmpOp(l, r)(_ == _)
    case LtNumC(l, r) => numCmpOp(l, r)(_ < _)

    case IfC(c, t, f) => if asBool(interp(c)) then interp(t) else interp(f)
    case UndefinedC() => throw InterpException("Undefined behavior")

    case NilC() => NilV()
    case ConsC(head, tail) => ConsV(interp(head), interp(tail))

    case HeadC(e) => head(interp(e))
    case TailC(e) => tail(interp(e))
    case IsNilC(e) => BoolV(isNil(interp(e)))
    case IsListC(e) => BoolV(isList(interp(e)))
  }

  def asNum(v: Value): Int = v match {
    case NumV(n) => n
    case _ => throw InterpException("Not a number")
  }

  def asBool(v: Value): Boolean = v match {
    case BoolV(b) => b
    case _ => throw InterpException("Not a boolean")
  }

  def head(v: Value): Value = v match {
    case ConsV(h, _) => h
    case _ => throw InterpException("Expected a Cons")
  }

  def tail(v: Value): Value = v match {
    case ConsV(_, t) => t
    case _ => throw InterpException("Expected a Cons")
  }

  def isNil(v: Value): Boolean = v match {
    case NilV() => true
    case ConsV(_, _) => false
    case _ => throw InterpException("Not a list")
  }

  def isList(v: Value): Boolean = v match {
    case NilV() => true
    case ConsV(_, _) => true
    case _ => false
  }
}
