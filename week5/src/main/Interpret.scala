object Interpret {

  def interp(e: ExprC, nv: PointerEnvironment, st: Store): (Value, Store) = e match {
    case NumC(value) => (NumV(value), st)
    case TrueC() => (BoolV(true), st)
    case FalseC() => (BoolV(false), st)

    case AddC(l, r) => numBinOp(l, r, nv, st)(_ + _)
    case MulC(l, r) => numBinOp(l, r, nv, st)(_ * _)

    case EqNumC(l, r) => numCmpOp(l, r, nv, st)(_ == _)
    case LtNumC(l, r) => numCmpOp(l, r, nv, st)(_ < _)

    case IfC(c, t, f) =>
      val (cv, st2) = interp(c, nv, st)
      if asBool(cv) then interp(t, nv, st2) else interp(f, nv, st2)
    case UndefinedC() => throw InterpException("Undefined behavior")

    case NilC() => (NilV(), st)
    case ConsC(head, tail) =>
      val (headV, st2) = interp(head, nv, st)
      val (tailV, st3) = interp(tail, nv, st2)
      (ConsV(headV, tailV), st3)

    case HeadC(e) =>
      val (v, st2) = interp(e, nv, st)
      (head(v), st2)
    case TailC(e) =>
      val (v, st2) = interp(e, nv, st)
      (tail(v), st2)
    case IsNilC(e) =>
      val (v, st2) = interp(e, nv, st)
      (isNil(v), st2)

    case IdC(name) => (fetch(lookup(name, nv), st), st)
    case f: LambdaC => (PointerClosureV(f, nv), st)
    case AppC(func, args) =>
      val (funcVal, st2) = interp(func, nv, st)
      funcVal match {
        case PointerClosureV(LambdaC(params, body), closureEnv) =>
          val (argsV, st3) = args.foldLeft((List.empty[Value], st2)) {
            case ((currArgsV, currStore), currArg) =>
              val (currArgV, newStore) = interp(currArg, nv, currStore)
              (currArgsV :+ currArgV, newStore)
          }
          val (extendedEnv, st4) = params.zip(argsV).foldLeft((closureEnv, st3)) {
            case ((currEnv, currStore), (param, argV)) =>
              val newLocation = newLoc(currStore)
              ((param, newLocation) :: currEnv, extendStore((newLocation, argV), currStore))
          }
          interp(body, extendedEnv, st4)
        case _ => throw InterpException("Attempted to call a non-function")
      }

    case SetC(name, value) =>
      val (v, st2) = interp(value, nv, st)
      (v, update(lookup(name, nv), st2, v))
    case SeqC(first, second) => interp(second, nv, interp(first, nv, st)._2)
    case UninitialisedC() => (UninitialisedV(), st)

    case BoxC(e) =>
      val (v, st2) = interp(e, nv, st)
      val loc = newLoc(st2)
      (BoxV(loc), extendStore((loc, v), st2))
    case UnboxC(b) =>
      val (bv, st2) = interp(b, nv, st)
      bv match {
        case BoxV(loc) => (fetch(loc, st2), st2)
        case _ => throw InterpException("pissballs")
      }
    case SetBoxC(b, e) =>
      val (bv, st2) = interp(b, nv, st)
      bv match {
        case BoxV(loc) =>
          val (v, st3) = interp(e, nv, st2)
          (v, update(loc, st3, v))
        case _ => throw InterpException("sssss")
      }

    case TupleC(exprs) =>
      val (exprVs, st2) = exprs.foldLeft((List.empty[Value], st)) {
        case ((currExprVs, currStore), e) =>
          val (v, newStore) = interp(e, nv, currStore)
          (currExprVs :+ v, newStore)
      }
      (TupleV(exprVs), st2)
    case ProjC(index, e) =>
      val (eV, st2) = interp(e, nv, st)
      eV match {
        case TupleV(values) if index < values.length && index >= 0 => (values(index), st2)
        case _ => throw InterpException("")
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

  private def numBinOp(l: ExprC, r: ExprC, nv: PointerEnvironment, st1: Store)(op: (Int, Int) => Int): (Value, Store) =
    val (lv, st2) = interp(l, nv, st1)
    val (rv, st3) = interp(r, nv, st2)
    (NumV(op(asNum(lv), asNum(rv))), st3)

  private def numCmpOp(l: ExprC, r: ExprC, nv: PointerEnvironment, st1: Store)(op: (Int, Int) => Boolean): (Value, Store) =
    val (lv, st2) = interp(l, nv, st1)
    val (rv, st3) = interp(r, nv, st2)
    (BoolV(op(asNum(lv), asNum(rv))), st3)

  def lookup(name: String, nv: PointerEnvironment): Pointer = nv.collectFirst { case (`name`, value) => value }.getOrElse(throw InterpException("goo goo gaa gaa"))
  def update(loc: Pointer, st: Store, v: Value): Store = st.updated(if st.indexWhere((p, _) => p == loc) == -1 then throw InterpException("") else st.indexWhere((p, _) => p == loc), (loc, v))
  def fetch(loc: Pointer, st: Store): Value = st.collectFirst { case (`loc`, value) => value }.getOrElse(throw InterpException("gaa gaa goo goo"))
  def newLoc(store: Store): Pointer = Pointer(store.map { case (Pointer(i), _) => i }.maxOption.getOrElse(-1) + 1)
  def extendStore(b: (Pointer, Value), st: Store): Store = st :+ b
}
