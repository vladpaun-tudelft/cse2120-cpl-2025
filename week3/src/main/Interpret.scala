object Interpret {

  def interp(e: ExprC, nv: Environment): Value = e match {
    case NumC(value) => NumV(value)
    case TrueC() => BoolV(true)
    case FalseC() => BoolV(false)

    case AddC(l, r) => numBinOp(l,r,nv)(_ + _)
    case MulC(l, r) => numBinOp(l,r,nv)(_ * _)

    case EqNumC(l, r) => numCmpOp(l,r,nv)(_ == _)
    case LtNumC(l, r) => numCmpOp(l,r,nv)(_ < _)

    case IfC(c, t, f) => if asBool(interp(c,nv)) then interp(t,nv) else interp(f,nv)
    case UndefinedC() => throw InterpException("Undefined behavior")

    case NilC() => NilV()
    case ConsC(head, tail) => ConsV(interp(head,nv), interp(tail,nv))

    case HeadC(e) => head(interp(e,nv))
    case TailC(e) => tail(interp(e,nv))
    case IsNilC(e) => isNil(interp(e,nv))
    case IsListC(e) => isList(interp(e,nv))

    case IdC(name) => take(nv,name)
    case f:LambdaC => ClosureV(f,nv)
    case AppC(calee, args) => interp(calee,nv) match {
      case ClosureV(LambdaC(params, body), oldNv) =>
        if params.length != args.length then throw InterpException("Mismatch in params and args")
        else interp(body, params.zip(args.map(a => interp(a,nv))) ++ oldNv)
      case _ => throw InterpException("Attempted to call smth that is not a function")
    }
  }

  private def asNum(v: Value): Int = v match {
    case NumV(n) => n
    case _ => throw InterpException("Not a number")
  }

  private def asBool(v: Value): Boolean = v match {
    case BoolV(b) => b
    case _ => throw InterpException("Not a boolean")
  }

  private def head(v: Value): Value = v match {
    case ConsV(h, _) => h
    case _ => throw InterpException("Expected a Cons")
  }

  private def tail(v: Value): Value = v match {
    case ConsV(_, t) => t
    case _ => throw InterpException("Expected a Cons")
  }

  private def isNil(v: Value): Value = v match {
    case NilV() => BoolV(true)
    case ConsV(_, _) => BoolV(false)
    case _ => throw InterpException("Not a list")
  }

  private def isList(v: Value): Value = v match {
    case NilV() | ConsV(_, _) => BoolV(true)
    case _ => BoolV(false)
  }

  private def numBinOp(l: ExprC, r: ExprC, nv: Environment)(op: (Int, Int) => Int): Value = NumV(op(asNum(interp(l,nv)), asNum(interp(r,nv))))
  private def numCmpOp(l: ExprC, r: ExprC, nv: Environment)(op: (Int, Int) => Boolean): Value = BoolV(op(asNum(interp(l,nv)), asNum(interp(r,nv))))

  def add(myEnv: Environment, name: String, value : Value): Environment = (name, value) :: myEnv
  def take(myEnv: Environment, name: String): Value = myEnv.collectFirst { case (`name`, value) => value }.getOrElse(throw MissingBindingException())
  def update(myEnv: Environment, otherEnv: Environment): Environment = myEnv.map {case (name, value) => (name, otherEnv.find(_._1 == name).map(_._2).getOrElse(value))}
  def extend(myEnv: Environment, otherEnv: Environment): Environment = myEnv ++ otherEnv.filter {case (name, _) => !myEnv.exists(_._1 == name)}
  case class MissingBindingException() extends InterpException("No matching binding was found in the environmnet")
}