object Interpret {

  def interp(e: ExprC, nv: PointerEnvironment, st: Store): (Value, Store) = e match {
      case NumC(value) => (NumV(value), st)
      case TrueC() => (BoolV(true), st)
      case FalseC() => (BoolV(false), st)

      case AddC(l, r) => numBinOp(l,r,nv,st)(_ + _)
      case MulC(l, r) => numBinOp(l,r,nv,st)(_ * _)

      case EqNumC(l, r) => numCmpOp(l,r,nv,st)(_ == _)
      case LtNumC(l, r) => numCmpOp(l,r,nv,st)(_ < _)

      case IfC(c, t, f) => {
          val (cv, st2) = interp(c,nv,st)
          if asBool(cv) then interp(t,nv,st2) else interp(f,nv,st2)
      }
      case UndefinedC() => throw InterpException("Undefined behavior")

      case NilC() => (NilV(),st)
      case ConsC(head, tail) => {
          val (headV, st2) = interp(head,nv,st)
          val (tailV, st3) = interp(tail,nv,st2)
          (ConsV(headV,tailV), st3)
      }

      case HeadC(e) => {
          val (v, st2) = interp(e, nv, st)
          (head(v), st2)
      }

      case TailC(e) => {
          val (v, st2) = interp(e, nv, st)
          (tail(v), st2)
      }

      case IsNilC(e) => {
          val (v, st2) = interp(e, nv, st)
          (isNil(v), st2)
      }

      case IsListC(e) => {
          val (v, st2) = interp(e, nv, st)
          (isList(v), st2)
      }

      case IdC(name) => (fetch(lookup(name, nv),st), st)
      case f:LambdaC => PointerClosureV(f,nv)
      case AppC(callee: ExprC, args: List[ExprC]) => {
        val (fv, st2) = interp(callee,nv,st)
        fv match {
          case PointerClosureV(LambdaC(params:List[String], body), enclosedEnv) => {
              //maybe check arity of params and args

              //interpret all args
              val (interpedArg, st3) = interp(arg, nv, st2);
              
              //enclose all params
              val newPointer = newLoc(st2)
              val extendedEnv = (param, newPointer) :: enclosedEnv
              val extendedStore = extendStore((newPointer, interpedArg), st3)

              //interpret body
              interp(body, extendedEnv, extendedStore)
          }
          case _ => throw InterpException("")
        }
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

  private def numBinOp(l: ExprC, r: ExprC, nv: PointerEnvironment, st1: Store)(op: (Int, Int) => Int): (Value, Store) = {
    val (lv, st2) = interp(l,nv,st1)
    val (rv, st3) = interp(r,nv,st2)
    (NumV(op(asNum(lv),asNum(rv))), st3)
  }
  private def numCmpOp(l: ExprC, r: ExprC, nv: PointerEnvironment, st1: Store)(op: (Int, Int) => Boolean): (Value, Store) = {
    val (lv, st2) = interp(l, nv, st1)
    val (rv, st3) = interp(r, nv, st2)
    (BoolV(op(asNum(lv), asNum(rv))), st3)
  }

  def add(myEnv: PointerEnvironment, name: String, value : Value): PointerEnvironment = (name, value) :: myEnv
  def take(myEnv: PointerEnvironment, name: String): Value = myEnv.collectFirst { case (`name`, value) => value }.getOrElse(throw MissingBindingException())
 
  case class MissingBindingException() extends InterpException("No matching binding was found in the environmnet")
  
  def lookup(name: String, nv: PointerEnvironment): Pointer = nv.collectFirst {case (`name`, value) => value}.getOrElse(throw InterpException("goo goo gaa gaa"))
  def update(loc: Pointer, st: Store, v: Value): Store = st.updated(if st.indexWhere((p,_) => p == loc) == -1 then throw InterpException("") else st.indexWhere((p,_) => p == loc), (loc,v))
  def fetch(loc: Pointer, st: Store): Value = st.collectFirst {case (`loc`, value) => value}.getOrElse(throw InterpException("gaa gaa goo goo"))
  def newLoc(store: Store): Pointer = Pointer(store.map {case (Pointer(i), _) => i}.maxOption.getOrElse(-1) + 1)
  def extendStore(b: (Pointer, Value), st: Store): Store = st :+ b

}