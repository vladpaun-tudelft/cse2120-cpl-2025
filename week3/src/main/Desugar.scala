object Desugar {

  def desugar(e: ExprExt): ExprC = e match {
    case NumExt(value: Int) => NumC(value)
    case TrueExt() => TrueC()
    case FalseExt() => FalseC()

    case NegExt(e: ExprExt) => MulC(NumC(-1), desugar(e))
    case AddExt(l: ExprExt, r: ExprExt) => AddC(desugar(l), desugar(r))
    case SubExt(l: ExprExt, r: ExprExt) => AddC(desugar(l), MulC(NumC(-1), desugar(r)))
    case MulExt(l: ExprExt, r: ExprExt) => MulC(desugar(l), desugar(r))

    case EqNumExt(l: ExprExt, r: ExprExt) => EqNumC(desugar(l), desugar(r))
    case LtNumExt(l: ExprExt, r: ExprExt) => LtNumC(desugar(l), desugar(r))
    case GtNumExt(l: ExprExt, r: ExprExt) => LtNumC(desugar(r), desugar(l))

    case NotExt(e: ExprExt) => IfC(desugar(e), FalseC(), TrueC())
    case AndExt(l: ExprExt, r: ExprExt) => IfC(desugar(l), desugar(r), FalseC())
    case OrExt(l: ExprExt, r: ExprExt) => IfC(desugar(l), TrueC(), desugar(r))

    case IfExt(c: ExprExt, t: ExprExt, f: ExprExt) => IfC(desugar(c), desugar(t), desugar(f))
    case CondExt(branches: List[CondBranch]) =>
      branches.foldRight(UndefinedC(): ExprC) {
        case (CondBranch(c, branchExpr), tail) => IfC(desugar(c), desugar(branchExpr), tail)
      }
    case CondEExt(branches: List[CondBranch], e: ExprExt) =>
      branches.foldRight(desugar(e)) {
        case (CondBranch(c, branchExpr), tail) => IfC(desugar(c), desugar(branchExpr), tail)
      }

    case NilExt() => NilC()
    case ConsExt(head: ExprExt, tail: ExprExt) => ConsC(desugar(head), desugar(tail))
    case ListExt(values: List[ExprExt]) => values.map(desugar).foldRight(NilC(): ExprC)(ConsC.apply)

    case HeadExt(e: ExprExt) => HeadC(desugar(e))
    case TailExt(e: ExprExt) => TailC(desugar(e))
    case IsNilExt(e: ExprExt) => IsNilC(desugar(e))
    case IsListExt(e: ExprExt) => IsListC(desugar(e))
  }
}
