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
    case GtNumExt(l, r) => AppC(LambdaC(List("x", "y"), LtNumC(IdC("y"), IdC("x"))), List(desugar(l), desugar(r)))

    case NotExt(e) => IfC(desugar(e), FalseC(), TrueC())
    case AndExt(l, r) => IfC(desugar(l), desugar(r), FalseC())
    case OrExt(l, r) => IfC(desugar(l), TrueC(), desugar(r))

    case IfExt(c, t, f) => IfC(desugar(c), desugar(t), desugar(f))
    case CondExt(branches: List[CondBranch]) => branches.foldRight(UndefinedC(): ExprC) { case (CondBranch(c, b), tail) => IfC(desugar(c), desugar(b), tail) }
    case CondEExt(branches, e) => branches.foldRight(desugar(e)) { case (CondBranch(c, b), tail) => IfC(desugar(c), desugar(b), tail) }

    case TypedNilExt(_) => NilC()
    case ConsExt(head, tail) => ConsC(desugar(head), desugar(tail))
    case TypedListExt(values, _) => values.map(desugar).foldRight(NilC(): ExprC)(ConsC.apply)

    case HeadExt(e) => HeadC(desugar(e))
    case TailExt(e) => TailC(desugar(e))
    case IsNilExt(e) => IsNilC(desugar(e))

    case IdExt(name) => IdC(name)
    case TypedLambdaExt(params, body) => LambdaC(params.map(p => p.name), desugar(body))
    case AppExt(callee, args) => AppC(desugar(callee), args.map(desugar))
    case LetExt(binds, body) => AppC(LambdaC(binds.map(_.name), desugar(body)), binds.map(b => desugar(b.value)))

    case SetExt(name, value) => SetC(name, desugar(value))
    case SequenceExt(exprs) => exprs.map(desugar).reduceRight(SeqC.apply)
    case TypedLetRecExt(binds, body) => AppC(LambdaC(binds.map(_.name), binds.map(b => SetC(b.name, desugar(b.value))).foldRight(desugar(body): ExprC)(SeqC.apply)), binds.map(_ => UninitialisedC()))

    case BoxExt(e) => BoxC(desugar(e))
    case UnboxExt(b) => UnboxC(desugar(b))
    case SetBoxExt(b, e) => SetBoxC(desugar(b), desugar(e))

    case TupleExt(values) => TupleC(values.map(v => desugar(v)))
    case ProjExt(index, e) => ProjC(index, desugar(e))
  }
}
