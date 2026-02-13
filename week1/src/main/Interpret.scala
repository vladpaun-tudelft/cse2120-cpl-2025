object Interpret {

  def interp(e: ExprC): Value = e match {
    case NumC(value: Int) => NumV(value)
    case TrueC() => BoolV(true)
    case FalseC() => BoolV(false)

    case AddC(l: ExprC, r: ExprC) => NumV(asNum(interp(l)) + asNum(interp(r)))
    case MulC(l: ExprC, r: ExprC) => NumV(asNum(interp(l)) * asNum(interp(r)))

    case EqNumC(l: ExprC, r: ExprC) => BoolV(asNum(interp(l)) == asNum(interp(r)))
    case LtNumC(l: ExprC, r: ExprC) => BoolV(asNum(interp(l)) < asNum(interp(r)))

    case IfC(c: ExprC, t: ExprC, f: ExprC) => if asBool(interp(c)) then interp(t) else interp(f)
    case UndefinedC() => throw InterpException("Undefined behavior")

    case NilC() => NilV()
    case ConsC(head: ExprC, tail: ExprC) => ConsV(interp(head), interp(tail))

    case HeadC(e: ExprC) => head(interp(e))
    case TailC(e: ExprC) => tail(interp(e))
    case IsNilC(e: ExprC) => BoolV(isNil(interp(e)))
    case IsListC(e: ExprC) => BoolV(isList(interp(e)))
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
