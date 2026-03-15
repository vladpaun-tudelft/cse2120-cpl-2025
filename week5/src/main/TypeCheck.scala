object TypeCheck {

  def typeOf(e: ExprExt, nv: TEnvironment): Type = e match {
    case NumExt(_) => NumT()
    case TrueExt() => BoolT()
    case FalseExt() => BoolT()

    case NegExt(e) => if typeOf(e, nv) == NumT() then NumT() else throw TypeException("not a number")
    case AddExt(l, r) => if typeOf(l, nv) == NumT() && typeOf(r, nv) == NumT() then NumT() else throw TypeException("not a number")
    case SubExt(l, r) => if typeOf(l, nv) == NumT() && typeOf(r, nv) == NumT() then NumT() else throw TypeException("not a number")
    case MulExt(l, r) => if typeOf(l, nv) == NumT() && typeOf(r, nv) == NumT() then NumT() else throw TypeException("not a number")

    case EqNumExt(l, r) => if typeOf(l, nv) == NumT() && typeOf(r, nv) == NumT() then BoolT() else throw TypeException("not a number")
    case LtNumExt(l, r) => if typeOf(l, nv) == NumT() && typeOf(r, nv) == NumT() then BoolT() else throw TypeException("not a number")
    case GtNumExt(l, r) => if typeOf(l, nv) == NumT() && typeOf(r, nv) == NumT() then BoolT() else throw TypeException("not a number")

    case NotExt(e) => if typeOf(e, nv) == BoolT() then BoolT() else throw TypeException("not a boolean")
    case AndExt(l, r) => if typeOf(l, nv) == BoolT() && typeOf(r, nv) == BoolT() then BoolT() else throw TypeException("not a number")
    case OrExt(l, r) => if typeOf(l, nv) == BoolT() && typeOf(r, nv) == BoolT() then BoolT() else throw TypeException("not a number")

    case IfExt(c, t, f) =>
      if typeOf(c, nv) != BoolT() then throw TypeException("")
      else
        val u = typeOf(t, nv)
        if typeOf(f, nv) == u then u else throw TypeException("faaah")
    case CondExt(branches) => branches.foldLeft(typeOf(branches.head.e, nv)) { case (ty, CondBranch(c, e)) => if typeOf(c, nv) == BoolT() && typeOf(e, nv) == ty then ty else throw TypeException("") }
    case CondEExt(branches, e) => branches.foldLeft(typeOf(e, nv)) { case (ty, CondBranch(c, e)) => if typeOf(c, nv) == BoolT() && typeOf(e, nv) == ty then ty else throw TypeException("") }

    case TypedNilExt(ty) => ListT(ty)
    case ConsExt(head, tail) =>
      val u = typeOf(head, nv)
      if typeOf(tail, nv) == ListT(u) then ListT(u) else throw TypeException("")
    case TypedListExt(values, ty) => ListT(values.foldLeft(ty) { case (_, value) => if typeOf(value, nv) == ty then ty else throw TypeException("") })

    case HeadExt(e) => typeOf(e, nv) match {
      case ListT(t) => t
      case _ => throw TypeException("")
    }
    case TailExt(e) => typeOf(e, nv) match {
      case ListT(t) => ListT(t)
      case _ => throw TypeException("")
    }
    case IsNilExt(e) => typeOf(e, nv) match {
      case ListT(_) => BoolT()
      case _ => throw TypeException("")
    }

    case IdExt(name) => lookup(name, nv)
    case TypedLambdaExt(params, body) => FunT(params.map(_.ty), typeOf(body, params.map(p => (p.name, p.ty)) ++ nv))
    case AppExt(callee, args) => typeOf(callee, nv) match {
      case FunT(paramTypes, retType) => if args.map(a => typeOf(a, nv)) == paramTypes then retType else throw TypeException("")
      case _ => throw TypeException("")
    }
    case LetExt(binds, body) => typeOf(body, binds.map(b => (b.name, typeOf(b.value, nv))) ++ nv)

    case SetExt(name, value) =>
      val u = typeOf(value, nv)
      if lookup(name, nv) == u then u else throw TypeException("")
    case SequenceExt(exprs) => exprs.foldLeft(BoolT(): Type) { case (_, expr) => typeOf(expr, nv) }
    case TypedLetRecExt(binds, body) =>
      val newEnv = binds.map(b => (b.name, b.ty)) ++ nv
      binds.foreach(b => if typeOf(b.value, newEnv) != b.ty then throw TypeException(""))
      typeOf(body, newEnv)

    case BoxExt(e) => RefT(typeOf(e, nv))
    case UnboxExt(b) => typeOf(b, nv) match {
      case RefT(ty) => ty
      case _ => throw TypeException("")
    }
    case SetBoxExt(b, e) => typeOf(b, nv) match {
      case RefT(ty) if ty == typeOf(e, nv) => ty
      case _ => throw TypeException("")
    }

    case TupleExt(values) => TupleT(values.map(e => typeOf(e, nv)))
    case ProjExt(index, e) => typeOf(e, nv) match {
      case TupleT(l: List[Type]) if index < l.length && index >= 0 => l(index)
      case _ => throw TypeException("")
    }
  }

  def lookup(name: String, nv: TEnvironment): Type = nv.collectFirst { case (`name`, ty) => ty }.getOrElse(throw TypeException("goo goo gaa gaa"))
}
