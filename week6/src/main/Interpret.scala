object Interpret {

  def interp(e: ExprC, nv: MutEnvironment): Value = e match {
    case NumC(value) => NumV(value)
    case TrueC() => BoolV(true)
    case FalseC() => BoolV(false)

    case AddC(l, r) => numBinOp(l, r, nv)(_ + _)
    case MulC(l, r) => numBinOp(l, r, nv)(_ * _)
    case EqNumC(l, r) => numCmpOp(l, r, nv)(_ == _)
    case LtNumC(l, r) => numCmpOp(l, r, nv)(_ < _)

    case IfC(c, t, f) =>
      if asBool(strict(interp(c, nv))) then interp(t, nv) else interp(f, nv)
    case UndefinedC() => throw InterpException("Undefined behavior")

    case NilC() => NilV()
    case ConsC(head, tail) => ConsV(ThunkV(Left((head, nv))), ThunkV(Left((tail, nv))))

    case HeadC(e) =>
      strict(interp(e, nv)) match {
        case ConsV(h, _) => h
        case _ => throw InterpException("Expected a Cons")
      }
    case TailC(e) =>
      strict(interp(e, nv)) match {
        case ConsV(_, t) => t
        case _ => throw InterpException("Expected a Cons")
      }
    case IsNilC(e) =>
      strict(interp(e, nv)) match {
        case NilV() => BoolV(true)
        case ConsV(_, _) => BoolV(false)
        case _ => throw InterpException("Not a list")
      }
    case IsListC(e) =>
      strict(interp(e, nv)) match {
        case NilV() | ConsV(_, _) => BoolV(true)
        case _ => BoolV(false)
      }

    case IdC(name) =>
      nv.collectFirst { case MutBind(`name`, value) => value }
        .getOrElse(throw InterpException(s"Unbound identifier: $name"))
    case f: LambdaC => MutClosureV(f, nv)
    case AppC(callee, args) =>
      strict(interp(callee, nv)) match {
        case MutClosureV(LambdaC(params, body), oldNv) =>
          if params.length != args.length then {
            throw InterpException("Mismatch in params and args")
          } else {
            val argBinds = params.zip(args.map(arg => ThunkV(Left((arg, nv))))).map {
              case (name, value) => MutBind(name, value)
            }
            interp(body, argBinds ++ oldNv)
          }
        case _ => throw InterpException("Attempted to call something that is not a function")
      }

    case LetRecC(binds, body) =>
      val recBinds = binds.map(bind => MutBind(bind.name, UninitialisedV()))
      val recEnv = recBinds ++ nv
      binds.zip(recBinds).foreach { case (LetRecBind(_, expr), mb) =>
        mb.value = ThunkV(Left((expr, recEnv)))
      }
      interp(body, recEnv)

    case ForceC(e) => force(interp(e, nv))
    case PrintC(e) =>
      val v = interp(e, nv)
      printValue(v)
      println()
      strict(v)
  }

  def strict(v: Value): Value = v match {
    case ThunkV(Right(memoized)) => memoized
    case thunk @ ThunkV(Left((expr, env))) =>
      val res = strict(interp(expr, env))
      thunk.value = Right(res)
      res
    case other => other
  }

  def force(v: Value): Value = strict(v) match {
    case ConsV(head, tail) => ConsV(force(head), force(tail))
    case other => other
  }

  def printValue(v: Value): Unit = strict(v) match {
    case NumV(n) => print(n)
    case BoolV(b) => print(b)
    case NilV() => print("[]")
    case _: MutClosureV => print("<closure>")
    case UninitialisedV() => print("<uninitialized>")
    case ConsV(head, tail) =>
      print("[")
      printValue(head)
      print(" ")
      printValue(tail)
      print("]")
  }

  private def asNum(v: Value): Int = v match {
    case NumV(n) => n
    case _ => throw InterpException("Not a number")
  }

  private def asBool(v: Value): Boolean = v match {
    case BoolV(b) => b
    case _ => throw InterpException("Not a boolean")
  }

  private def numBinOp(l: ExprC, r: ExprC, nv: MutEnvironment)(op: (Int, Int) => Int): Value =
    NumV(op(asNum(strict(interp(l, nv))), asNum(strict(interp(r, nv)))))

  private def numCmpOp(l: ExprC, r: ExprC, nv: MutEnvironment)(op: (Int, Int) => Boolean): Value =
    BoolV(op(asNum(strict(interp(l, nv))), asNum(strict(interp(r, nv)))))
}
