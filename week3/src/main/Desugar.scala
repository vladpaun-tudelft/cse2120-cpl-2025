
object Desugar {

  def desugar(e: ExprExt): ExprC = e match {
    case NumExt(value) => NumC(value)
    case TrueExt() => TrueC()
    case FalseExt() => FalseC()

    case NegExt(e) => MulC(NumC(-1), desugar(e))
    case AddExt(l, r) => AddC(desugar(l), desugar(r))
    case SubExt(l, r) => AddC(desugar(l), MulC(NumC(-1), desugar(r)))
    case MulExt(l, r) => MulC(desugar(l), desugar(r))

    case EqNumExt(l, r) => EqNumC(desugar(l), desugar(r))
    case LtNumExt(l, r) => LtNumC(desugar(l), desugar(r))
    case GtNumExt(l, r) => LtNumC(desugar(r), desugar(l))

    case NotExt(e) => IfC(desugar(e), FalseC(), TrueC())
    case AndExt(l, r) => IfC(desugar(l), desugar(r), FalseC())
    case OrExt(l, r) => IfC(desugar(l), TrueC(), desugar(r))

    case IfExt(c, t, f) => IfC(desugar(c), desugar(t), desugar(f))
    case CondExt(branches: List[CondBranch]) =>
      branches.foldRight(UndefinedC(): ExprC) {
        case (CondBranch(c, b), tail) => IfC(desugar(c), desugar(b), tail)
      }
    case CondEExt(branches, e) =>
      branches.foldRight(desugar(e)) {
        case (CondBranch(c, b), tail) => IfC(desugar(c), desugar(b), tail)
      }

    case NilExt() => NilC()
    case ConsExt(head, tail) => ConsC(desugar(head), desugar(tail))
    case ListExt(values) => values.map(desugar).foldRight(NilC(): ExprC)(ConsC.apply)

    case HeadExt(e) => HeadC(desugar(e))
    case TailExt(e) => TailC(desugar(e))
    case IsNilExt(e) => IsNilC(desugar(e))
    case IsListExt(e) => IsListC(desugar(e))

    case IdExt(name) => IdC(name)
    case LambdaExt(params, body) => LambdaC(params, desugar(body))
    case AppExt(callee, args) => AppC(desugar(callee), args.map(desugar))
    case LetExt(binds, body) => AppC(LambdaC(binds.map(_.name), desugar(body)),binds.map(b => desugar(b.value)))
  }
}