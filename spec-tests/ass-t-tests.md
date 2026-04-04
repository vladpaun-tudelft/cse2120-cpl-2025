import nl.tudelft.cpl.test.\*  
import nl.tudelft.cpl.language.Library.\*

class Tests extends CPLTestSuite {

    suite("Assignment 1") {  
          
        unitTest("Interp Num") {  
            val result \= interp("1")  
            assertResult(NumV(1))(result)  
            // or assert(result \== NumV(1)), but that gives less clear error messages  
        }

        unitTest("Interp True") {  
            val result \= interp("true")  
            assertResult(BoolV(true))(result)  
        }

        unitTest("Interp False") {  
            val result \= interp("false")  
            assertResult(BoolV(false))(result)  
        }

        unitTest("Interp Neg works") {  
            val result \= interp("-2")  
            assertResult(NumV(-2))(result)  
        }  
        unitTest("Interp Neg stops non-numbers") {  
            assertThrows\[InterpException\] {  
                interp("-true")  
            }  
        }  
        unitTest("Interp Neg stops non-numbers 2") {  
            assertThrows\[InterpException\] {  
                interp("-nil: Num")  
            }  
        }

        unitTest("Interp Add works") {  
            val result \= interp("2 \+ 3")  
            assertResult(NumV(5))(result)  
        }  
        unitTest("Interp Add stops non-numbers on the right") {  
            assertThrows\[InterpException\] {  
                interp("2 \+ true")  
            }  
        }  
        unitTest("Interp Add stops non-numbers on the left") {  
            assertThrows\[InterpException\] {  
                interp("nil: Num \+ 3")  
            }  
        }  
        unitTest("Interp Add stops two non-numbers") {  
            assertThrows\[InterpException\] {  
                interp("nil: Num \+ Num\[1, 2\]")  
            }  
        }

        unitTest("Interp Sub works") {  
            val result \= interp("4 \- 2")  
            assertResult(NumV(2))(result)  
        }  
        unitTest("Interp Sub stops non-numbers on the right") {  
            assertThrows\[InterpException\] {  
                interp("2 \- true")  
            }  
        }  
        unitTest("Interp Sub stops non-numbers on the left") {  
            assertThrows\[InterpException\] {  
                interp("nil: Num \- 3")  
            }  
        }  
        unitTest("Interp Sub stops two non-numbers") {  
            assertThrows\[InterpException\] {  
                interp("nil: Num \- Num\[1, 2\]")  
            }  
        }

        unitTest("Interp Mul works") {  
            val result \= interp("2 \* 3")  
            assertResult(NumV(6))(result)  
        }  
        unitTest("Interp Mul stops non-numbers on the right") {  
            assertThrows\[InterpException\] {  
                interp("2 \* true")  
            }  
        }  
        unitTest("Interp Mul stops non-numbers on the left") {  
            assertThrows\[InterpException\] {  
                interp("nil: Num \* 3")  
            }  
        }  
        unitTest("Interp Mul stops two non-numbers") {  
            assertThrows\[InterpException\] {  
                interp("nil: Num \* Num\[1, 2\]")  
            }  
        }

        unitTest("Interp EqNum works 1") {  
            val result \= interp("3 \== 3")  
            assertResult(BoolV(true))(result)  
        }  
        unitTest("Interp EqNum works 2") {  
            val result \= interp("2 \== 3")  
            assertResult(BoolV(false))(result)  
        }  
        unitTest("Interp EqNum works 3") {  
            val result \= interp("3 \== 2")  
            assertResult(BoolV(false))(result)  
        }  
        unitTest("Interp EqNum stops non-numbers on the right") {  
            assertThrows\[InterpException\] {  
                interp("2 \== true")  
            }  
        }  
        unitTest("Interp EqNum stops non-numbers on the left") {  
            assertThrows\[InterpException\] {  
                interp("nil: Num \== 3")  
            }  
        }  
        unitTest("Interp EqNum stops two non-numbers") {  
            assertThrows\[InterpException\] {  
                interp("nil: Num \== Num\[1, 2\]")  
            }  
        }

        unitTest("Interp LtNum works 1") {  
            val result \= interp("3 \< 4")  
            assertResult(BoolV(true))(result)  
        }  
        unitTest("Interp LtNum works 2") {  
            val result \= interp("3 \< 3")  
            assertResult(BoolV(false))(result)  
        }  
        unitTest("Interp LtNum works 3") {  
            val result \= interp("4 \< 3")  
            assertResult(BoolV(false))(result)  
        }  
        unitTest("Interp LtNum stops non-numbers on the right") {  
            assertThrows\[InterpException\] {  
                interp("2 \< true")  
            }  
        }  
        unitTest("Interp LtNum stops non-numbers on the left") {  
            assertThrows\[InterpException\] {  
                interp("nil: Num \< 3")  
            }  
        }  
        unitTest("Interp LtNum stops two non-numbers") {  
            assertThrows\[InterpException\] {  
                interp("nil: Num \< Num\[1, 2\]")  
            }  
        }

        unitTest("Interp GtNum works 1") {  
            val result \= interp("3 \> 4")  
            assertResult(BoolV(false))(result)  
        }  
        unitTest("Interp GtNum works 2") {  
            val result \= interp("3 \> 3")  
            assertResult(BoolV(false))(result)  
        }  
        unitTest("Interp GtNum works 3") {  
            val result \= interp("4 \> 3")  
            assertResult(BoolV(true))(result)  
        }  
        unitTest("Interp GtNum stops non-numbers on the right") {  
            assertThrows\[InterpException\] {  
                interp("2 \> true")  
            }  
        }  
        unitTest("Interp GtNum stops non-numbers on the left") {  
            assertThrows\[InterpException\] {  
                interp("nil: Num \> 3")  
            }  
        }  
        unitTest("Interp GtNum stops two non-numbers") {  
            assertThrows\[InterpException\] {  
                interp("nil: Num \> Num\[1, 2\]")  
            }  
        }

        unitTest("Interp Not works") {  
            val result \= interp("\!true")  
            assertResult(BoolV(false))(result)  
        }  
        unitTest("Interp Not works 2") {  
            val result \= interp("\!false")  
            assertResult(BoolV(true))(result)  
        }  
        unitTest("Interp Not stops non-bools") {  
            assertThrows\[InterpException\] {  
                interp("\!0")  
            }  
        }  
        unitTest("Interp Not stops non-bools 2") {  
            assertThrows\[InterpException\] {  
                interp("\!nil: Bool")  
            }  
        }

        unitTest("Interp And works 1") {  
            val result \= interp("true && true")  
            assertResult(BoolV(true))(result)  
        }  
        unitTest("Interp And works 2") {  
            val result \= interp("true && false")  
            assertResult(BoolV(false))(result)  
        }  
        unitTest("Interp And works 3") {  
            val result \= interp("false && true")  
            assertResult(BoolV(false))(result)  
        }  
        unitTest("Interp And works 4") {  
            val result \= interp("false && false")  
            assertResult(BoolV(false))(result)  
        }  
        unitTest("Interp And short circuit") {  
            val result \= interp("true && 3")  
            assertResult(NumV(3))(result)  
        }  
        unitTest("Interp And short circuit 2") {  
            val result \= interp("true && Num\[1, 2\]")  
            assertResult(ConsV(NumV(1), ConsV(NumV(2), NilV())))(result)  
        }  
        unitTest("Interp And lazy") {  
            val result \= interp("false && Num\[1, 2\]")  
            assertResult(BoolV(false))(result)  
        }  
        unitTest("Interp And stops non-bools on the left") {  
            assertThrows\[InterpException\] {  
                interp("nil: Bool && true")  
            }  
        }  
        unitTest("Interp And stops two non-bools") {  
            assertThrows\[InterpException\] {  
                interp("nil: Num && Num\[1, 2\]")  
            }  
        }

        unitTest("Interp Or works 1") {  
            val result \= interp("true || true")  
            assertResult(BoolV(true))(result)  
        }  
        unitTest("Interp Or works 2") {  
            val result \= interp("true || false")  
            assertResult(BoolV(true))(result)  
        }  
        unitTest("Interp Or works 3") {  
            val result \= interp("false || true")  
            assertResult(BoolV(true))(result)  
        }  
        unitTest("Interp Or works 4") {  
            val result \= interp("false || false")  
            assertResult(BoolV(false))(result)  
        }  
        unitTest("Interp Or short circuit") {  
            val result \= interp("false || 3")  
            assertResult(NumV(3))(result)  
        }  
        unitTest("Interp Or short circuit 2") {  
            val result \= interp("false || Num\[1, 2\]")  
            assertResult(ConsV(NumV(1), ConsV(NumV(2), NilV())))(result)  
        }  
        unitTest("Interp Or lazy") {  
            val result \= interp("true || Num\[1, 2\]")  
            assertResult(BoolV(true))(result)  
        }  
        unitTest("Interp Or stops non-bools on the left") {  
            assertThrows\[InterpException\] {  
                interp("nil: Bool || true")  
            }  
        }  
        unitTest("Interp Or stops two non-bools") {  
            assertThrows\[InterpException\] {  
                interp("nil: Num || Num\[1, 2\]")  
            }  
        }

        unitTest("Interp If works 1") {  
            val result \= interp("if true then 1 else 2")  
            assertResult(NumV(1))(result)  
        }  
        unitTest("Interp If works 2") {  
            val result \= interp("if false then 1 else 2")  
            assertResult(NumV(2))(result)  
        }  
        unitTest("Interp If executes only the right branch") {  
            val result \= interp("if true then 1 else (-true)")  
            assertResult(NumV(1))(result)  
        }  
        unitTest("Interp If executes only the right branch 2") {  
            val result \= interp("if false then (-true) else 2")  
            assertResult(NumV(2))(result)  
        }  
        unitTest("Interp If stops non-bool condition") {  
            assertThrows\[InterpException\] {  
                interp("if 1 then true else false")  
            }  
        }  
        unitTest("Interp If stops non-bool condition 2") {  
            assertThrows\[InterpException\] {  
                interp("if nil: Bool then true else false")  
            }  
        }

        unitTest("Interp Cond works 1") {  
            val result \= interp("cond true \-\> 1 | false \-\> true | false \-\> 2")  
            assertResult(NumV(1))(result)  
        }  
        unitTest("Interp Cond works 2") {  
            val result \= interp("cond false \-\> 1 | true \-\> true | false \-\> 2")  
            assertResult(BoolV(true))(result)  
        }  
        unitTest("Interp Cond works 3") {  
            val result \= interp("cond false \-\> 1 | false \-\> true | true \-\> 2")  
            assertResult(NumV(2))(result)  
        }  
        unitTest("Interp Cond takes first true branch") {  
            val result \= interp("cond true \-\> 1 | true \-\> true | true \-\> 2")  
            assertResult(NumV(1))(result)  
        }  
        unitTest("Interp Cond stops non-bool condition") {  
            assertThrows\[InterpException\] {  
                interp("cond false \-\> 1 | 0 \-\> true | true \-\> 2")  
            }  
        }  
        unitTest("Interp Cond stops non-true condition") {  
            assertThrows\[InterpException\] {  
                interp("cond false \-\> 1 | false \-\> true | false \-\> 2")  
            }  
        }

        unitTest("Interp CondE works 1") {  
            val result \= interp("cond true \-\> 1 | false \-\> true | else \-\> 2")  
            assertResult(NumV(1))(result)  
        }  
        unitTest("Interp CondE works 2") {  
            val result \= interp("cond false \-\> 1 | true \-\> true | else \-\> 2")  
            assertResult(BoolV(true))(result)  
        }  
        unitTest("Interp CondE works 3") {  
            val result \= interp("cond false \-\> 1 | false \-\> true | else \-\> 2")  
            assertResult(NumV(2))(result)  
        }  
        unitTest("Interp CondE takes first true branch") {  
            val result \= interp("cond true \-\> 1 | true \-\> true | else \-\> 2")  
            assertResult(NumV(1))(result)  
        }  
        unitTest("Interp CondE stops non-bool condition") {  
            assertThrows\[InterpException\] {  
                interp("cond false \-\> 1 | 0 \-\> true | else \-\> 2")  
            }  
        }

        unitTest("Interp Nil") {  
            val result \= interp("nil: Num")  
            assertResult(NilV())(result)  
        }

        unitTest("Interp Cons works 1") {  
            val result \= interp("1 :: 2")  
            assertResult(ConsV(NumV(1), NumV(2)))(result)  
        }  
        unitTest("Interp Cons works 2") {  
            val result \= interp("1 :: 2 :: nil: Num")  
            assertResult(ConsV(NumV(1), ConsV(NumV(2), NilV())))(result)  
        }

        unitTest("Interp List works 1") {  
            val result \= interp("Num\[1\]")  
            assertResult(ConsV(NumV(1), NilV()))(result)  
        }  
        unitTest("Interp List works 2") {  
            val result \= interp("Num\[1, 2\]")  
            assertResult(ConsV(NumV(1), ConsV(NumV(2), NilV())))(result)  
        }  
        unitTest("Interp List works 3") {  
            val result \= interp("Num\[\]")  
            assertResult(NilV())(result)  
        }

        unitTest("Interp Head works 1") {  
            val result \= interp("head Num\[1\]")  
            assertResult(NumV(1))(result)  
        }  
        unitTest("Interp Head works 2") {  
            val result \= interp("head Num\[2, 1\]")  
            assertResult(NumV(2))(result)  
        }  
        unitTest("Interp Head stops non-cons value") {  
            assertThrows\[InterpException\] {  
                interp("head 1")  
            }  
        }  
        unitTest("Interp Head stops non-cons value 2") {  
            assertThrows\[InterpException\] {  
                interp("head nil: Num")  
            }  
        }

        unitTest("Interp Tail works 1") {  
            val result \= interp("tail Num\[1\]")  
            assertResult(NilV())(result)  
        }  
        unitTest("Interp Tail works 2") {  
            val result \= interp("tail Num\[2, 1\]")  
            assertResult(ConsV(NumV(1), NilV()))(result)  
        }  
        unitTest("Interp Tail stops non-cons value") {  
            assertThrows\[InterpException\] {  
                interp("tail 1")  
            }  
        }  
        unitTest("Interp Tail stops non-cons value 2") {  
            assertThrows\[InterpException\] {  
                interp("tail nil: Num")  
            }  
        }

        unitTest("Interp IsNil works 1") {  
            val result \= interp("Num\[1\] is nil")  
            assertResult(BoolV(false))(result)  
        }  
        unitTest("Interp IsNil works 2") {  
            val result \= interp("Num\[\] is nil")  
            assertResult(BoolV(true))(result)  
        }  
        unitTest("Interp IsNil stops non-list value") {  
            assertThrows\[InterpException\] {  
                interp("1 is nil")  
            }  
        }  
        unitTest("Interp IsNil stops non-list value 2") {  
            assertThrows\[InterpException\] {  
                interp("true is nil")  
            }  
        }  
    }

    suite("Assignment 3") {

        unitTest("Interp Id works") {  
            val result \= interp("let x \= 1 in x")  
            assertResult(NumV(1))(result)  
        }  
        unitTest("Interp Id works 2") {  
            val result \= interp("(lambda x: \[Num\] \-\> x)(Num\[1, 2\])")  
            assertResult(ConsV(NumV(1), ConsV(NumV(2), NilV())))(result)  
        }  
        unitTest("Interp Id stops unbound variable") {  
            assertThrows\[InterpException\] {  
                interp("x")  
            }  
        }  
        unitTest("Interp Id stops unbound variable 2") {  
            assertThrows\[InterpException\] {  
                interp("let y \= 1 in x")  
            }  
        }

        unitTest("Interp Lambda returns closure") {  
            val result \= interp("lambda \-\> 1")  
            assert(result.isInstanceOf\[PointerClosureV\])  
        }  
        unitTest("Interp Lambda returns closure 2") {  
            val result \= interp("lambda x: Num, y: Num \-\> 1")  
            assert(result.isInstanceOf\[PointerClosureV\])  
        }

        unitTest("Interp App works") {  
            val result \= interp("(lambda \-\> 2)()")  
            assertResult(NumV(2))(result)  
        }  
        unitTest("Interp App works 2") {  
            val result \= interp("(lambda x: Num \-\> x)(1)")  
            assertResult(NumV(1))(result)  
        }  
        unitTest("Interp App binds properly") {  
            val result \= interp("(lambda x: \[Num\], y: Num \-\> x)(Num\[1, 2\], 3)")  
            assertResult(ConsV(NumV(1), ConsV(NumV(2), NilV())))(result)  
        }  
        unitTest("Interp App binds properly 2") {  
            val result \= interp("(lambda x: \[Num\], y: Num \-\> y)(Num\[1, 2\], 3)")  
            assertResult(NumV(3))(result)  
        }  
        unitTest("Interp App nested binds properly") {  
            val result \= interp("(lambda x: \[Num\] \-\> lambda y: Num \-\> x)(Num\[1, 2\])(3)")  
            assertResult(ConsV(NumV(1), ConsV(NumV(2), NilV())))(result)  
        }  
        unitTest("Interp App nested binds properly 2") {  
            val result \= interp("(lambda x: \[Num\] \-\> lambda y: Num \-\> y)(Num\[1, 2\])(3)")  
            assertResult(NumV(3))(result)  
        }  
        unitTest("Interp App allows name shadowing") {  
            val result \= interp("(lambda x: Num \-\> lambda x: Num \-\> x)(2)(1)")  
            assertResult(NumV(1))(result)  
        }  
        unitTest("Interp App stops more arguments than parameters") {  
            assertThrows\[InterpException\] {  
                interp("(lambda x: Num \-\> x)(1, 2)")  
            }  
        }  
        unitTest("Interp App stops more parameters than arguments") {  
            assertThrows\[InterpException\] {  
                interp("(lambda x: Num, y: Num \-\> x)(1)")  
            }  
        }

        unitTest("Interp Let works") {  
            val result \= interp("let x \= 1 in 2")  
            assertResult(NumV(2))(result)  
        }  
        unitTest("Interp Let works 2") {  
            val result \= interp("let x \= 1 in x")  
            assertResult(NumV(1))(result)  
        }  
        unitTest("Interp Let binds properly") {  
            val result \= interp("let x \= 1, y \= 2 in x")  
            assertResult(NumV(1))(result)  
        }  
        unitTest("Interp Let binds properly 2") {  
            val result \= interp("let x \= 1, y \= 2 in y")  
            assertResult(NumV(2))(result)  
        }  
        unitTest("Interp Let nested binds properly") {  
            val result \= interp("let x \= 1 in let y \= 2 in x")  
            assertResult(NumV(1))(result)  
        }  
        unitTest("Interp Let nested binds properly 2") {  
            val result \= interp("let x \= 1 in let y \= 2 in y")  
            assertResult(NumV(2))(result)  
        }  
        unitTest("Interp Let allows name shadowing") {  
            val result \= interp("let x \= 2 in let x \= 1 in x")  
            assertResult(NumV(1))(result)  
        }  
    }

    suite("Assignment 4") {

        unitTest("Interp Set works") {  
            val result \= interp("let x \= 2 in x \= 3")  
            assertResult(NumV(3))(result)  
        }  
        unitTest("Interp Set works 2") {  
            val result \= interp("let x \= 2, y \= 1 in {x \= 3; y \= 4; x \+ y}")  
            assertResult(NumV(7))(result)  
        }

        unitTest("Interp Sequence works") {  
            val result \= interp("{4}")  
            assertResult(NumV(4))(result)  
        }  
        unitTest("Interp Sequence works 2") {  
            val result \= interp("let x \= 1 in {x; 4}")  
            assertResult(NumV(4))(result)  
        }  
        unitTest("Interp Sequence works 3") {  
            val result \= interp("let x \= 1 in {x \= 2; x \= 3; x \+ 1}")  
            assertResult(NumV(4))(result)  
        }

        unitTest("Interp LetRec works") {  
            val result \= interp("let rec x: Num \= 4 in x")  
            assertResult(NumV(4))(result)  
        }  
        unitTest("Interp LetRec works 2") {  
            val result \= interp("let rec x: Num \= 1, y: Num \= x in y")  
            assertResult(NumV(1))(result)  
        }  
        unitTest("Interp LetRec works 3") {  
            val result \= interp("let rec sum: (Num) \-\> Num \= lambda x: Num \-\> if x \< 1 then 0 else x \+ sum(x \- 1\) in sum(5)")  
            assertResult(NumV(15))(result)  
        }  
        unitTest("Interp LetRec works 4") {  
            val result \= interp(  
                """  
                let rec sum: (Num) \-\> Num \= lambda x: Num \-\> if x \< 1 then 0 else x \+ sum2(x \- 1),  
                        sum2: (Num) \-\> Num \= lambda x: Num \-\> if x \< 1 then 0 else x \+ sum(x \- 1\)   
                        in   
                            sum(5)  
                """)  
            assertResult(NumV(15))(result)  
        }  
        unitTest("Interp LetRec stops reverse variable passing") {  
            val result \= interp("let rec x: Num \= y, y: Num \= 1 in x")  
            assertResult(UninitialisedV())(result)  
        }

        unitTest("Interp Box works") {  
            val result \= interp("box 4")  
            assert(result.isInstanceOf\[BoxV\])  
        }  
        unitTest("Interp Box works 2") {  
            val result \= interp("let b \= box 0 in b")  
            assert(result.isInstanceOf\[BoxV\])  
        }

        unitTest("Interp Unbox works") {  
            val result \= interp("unbox (box 4)")  
            assertResult(NumV(4))(result)  
        }  
        unitTest("Interp Unbox works 2") {  
            val result \= interp("unbox (box true)")  
            assertResult(BoolV(true))(result)  
        }  
        unitTest("Interp Unbox works 3") {  
            val result \= interp("let b \= box 0 in unbox b")  
            assertResult(NumV(0))(result)  
        }  
        unitTest("Interp Unbox stops non-box value") {  
            assertThrows\[InterpException\] {  
                interp("unbox 1")  
            }  
        }

        unitTest("Interp SetBox works") {  
            val result \= interp("let b \= box 0 in set box b to 4")  
            assertResult(NumV(4))(result)  
        }  
        unitTest("Interp SetBox works 2") {  
            val result \= interp("set box (box true) to false")  
            assertResult(BoolV(false))(result)  
        }  
        unitTest("Interp SetBox stops non-box value before to") {  
            assertThrows\[InterpException\] {  
                interp("set box 1 to 2")  
            }  
        }

        unitTest("Interp Neg store chaining works") {  
            val result \= interp("let x \= 1 in {-(x \= 2); x}")  
            assertResult(NumV(2))(result)  
        }

        unitTest("Interp Add store chaining works") {  
            val result \= interp("let x \= 1 in (x \= 2\) \+ x")  
            assertResult(NumV(4))(result)  
        }  
        unitTest("Interp Add store chaining works 2") {  
            val result \= interp("let x \= 1 in {1 \+ (x \= 3); x}")  
            assertResult(NumV(3))(result)  
        }  
        unitTest("Interp Add store chaining works 3") {  
            val result \= interp("let x \= 1 in {(x \= 2\) \+ (x \= 3); x}")  
            assertResult(NumV(3))(result)  
        }

        unitTest("Interp Sub store chaining works") {  
            val result \= interp("let x \= 1 in (x \= 2\) \- x")  
            assertResult(NumV(0))(result)  
        }  
        unitTest("Interp Sub store chaining works 2") {  
            val result \= interp("let x \= 1 in {1 \- (x \= 3); x}")  
            assertResult(NumV(3))(result)  
        }  
        unitTest("Interp Sub store chaining works 3") {  
            val result \= interp("let x \= 1 in {(x \= 2\) \- (x \= 3); x}")  
            assertResult(NumV(3))(result)  
        }

        unitTest("Interp Mul store chaining works") {  
            val result \= interp("let x \= 1 in (x \= 2\) \* x")  
            assertResult(NumV(4))(result)  
        }  
        unitTest("Interp Mul store chaining works 2") {  
            val result \= interp("let x \= 1 in {1 \* (x \= 3); x}")  
            assertResult(NumV(3))(result)  
        }  
        unitTest("Interp Mul store chaining works 3") {  
            val result \= interp("let x \= 1 in {(x \= 2\) \* (x \= 3); x}")  
            assertResult(NumV(3))(result)  
        }

        unitTest("Interp EqNum store chaining works") {  
            val result \= interp("let x \= 1 in (x \= 2\) \== x")  
            assertResult(BoolV(true))(result)  
        }  
        unitTest("Interp EqNum store chaining works 2") {  
            val result \= interp("let x \= 1 in {1 \== (x \= 3); x}")  
            assertResult(NumV(3))(result)  
        }  
        unitTest("Interp EqNum store chaining works 3") {  
            val result \= interp("let x \= 1 in {(x \= 2\) \== (x \= 3); x}")  
            assertResult(NumV(3))(result)  
        }

        unitTest("Interp LtNum store chaining works") {  
            val result \= interp("let x \= 1 in (x \= 2\) \< x")  
            assertResult(BoolV(false))(result)  
        }  
        unitTest("Interp LtNum store chaining works 2") {  
            val result \= interp("let x \= 1 in {1 \< (x \= 3); x}")  
            assertResult(NumV(3))(result)  
        }  
        unitTest("Interp LtNum store chaining works 3") {  
            val result \= interp("let x \= 1 in {(x \= 2\) \< (x \= 3); x}")  
            assertResult(NumV(3))(result)  
        }

        unitTest("Interp GtNum store chaining works") {  
            val result \= interp("let x \= 1 in (x \= 2\) \> x")  
            assertResult(BoolV(false))(result)  
        }  
        unitTest("Interp GtNum store chaining works 2") {  
            val result \= interp("let x \= 1 in {1 \> (x \= 3); x}")  
            assertResult(NumV(3))(result)  
        }  
        unitTest("Interp GtNum store chaining works 3") {  
            val result \= interp("let x \= 1 in {(x \= 2\) \> (x \= 3); x}")  
            assertResult(NumV(3))(result)  
        }

        unitTest("Interp Not store chaining works") {  
            val result \= interp("let x \= false in {\!(x \= true); x}")  
            assertResult(BoolV(true))(result)  
        }

        unitTest("Interp And store chaining works") {  
            val result \= interp("let x \= false in (x \= true) && x")  
            assertResult(BoolV(true))(result)  
        }  
        unitTest("Interp And store chaining works 2") {  
            val result \= interp("let x \= false in {true && (x \= true); x}")  
            assertResult(BoolV(true))(result)  
        }  
        unitTest("Interp And store chaining works 3") {  
            val result \= interp("let x \= false in {(x \= true) && (x \= false); x}")  
            assertResult(BoolV(false))(result)  
        }

        unitTest("Interp Or store chaining works") {  
            val result \= interp("let x \= true in (x \= false) || x")  
            assertResult(BoolV(false))(result)  
        }  
        unitTest("Interp Or store chaining works 2") {  
            val result \= interp("let x \= false in {false || (x \= true); x}")  
            assertResult(BoolV(true))(result)  
        }  
        unitTest("Interp Or store chaining works 3") {  
            val result \= interp("let x \= true in {(x \= false) || (x \= true); x}")  
            assertResult(BoolV(true))(result)  
        }

        unitTest("Interp If store chaining works") {  
            val result \= interp("let x \= 1 in if {x \= 2; true} then x else x \= 3")  
            assertResult(NumV(2))(result)  
        }  
        unitTest("Interp If store chaining works 2") {  
            val result \= interp("let x \= 1 in if {x \= 2; false} then x else x")  
            assertResult(NumV(2))(result)  
        }  
        unitTest("Interp If store chaining works 3") {  
            val result \= interp("let x \= 1 in {if {x \= 2; false} then x \= 4 else x \= 3; x}")  
            assertResult(NumV(3))(result)  
        }  
        unitTest("Interp If store chaining works 4") {  
            val result \= interp("let x \= 1 in {if {x \= 2; true} then x \= 4 else x \= 3; x}")  
            assertResult(NumV(4))(result)  
        }

        unitTest("Interp Cond store chaining works") {  
            val result \= interp("let x \= 1 in cond {x \= 2; true} \-\> x | false \-\> x \= 3")  
            assertResult(NumV(2))(result)  
        }  
        unitTest("Interp Cond store chaining works 2") {  
            val result \= interp("let x \= 1 in cond {x \= 2; false} \-\> x | true \-\> x")  
            assertResult(NumV(2))(result)  
        }  
        unitTest("Interp Cond store chaining works 3") {  
            val result \= interp("let x \= 1 in {cond {x \= 2; false} \-\> x \= 4 | true \-\> x \= 3; x}")  
            assertResult(NumV(3))(result)  
        }  
        unitTest("Interp Cond store chaining works 4") {  
            val result \= interp("let x \= 1 in {cond {x \= 2; true} \-\> x \= 4 | false \-\> x \= 3; x}")  
            assertResult(NumV(4))(result)  
        }  
        unitTest("Interp Cond store chaining works 5") {  
            val result \= interp("let x \= 1 in {cond {x \= 2; false} \-\> x \= 4 | false \-\> x \= 3 | x \== 2 \-\> x \= 5; x}")  
            assertResult(NumV(5))(result)  
        }

        unitTest("Interp CondE store chaining works") {  
            val result \= interp("let x \= 1 in cond {x \= 2; true} \-\> x | else \-\> x \= 3")  
            assertResult(NumV(2))(result)  
        }  
        unitTest("Interp CondE store chaining works 2") {  
            val result \= interp("let x \= 1 in cond {x \= 2; false} \-\> x | else \-\> x")  
            assertResult(NumV(2))(result)  
        }  
        unitTest("Interp CondE store chaining works 3") {  
            val result \= interp("let x \= 1 in {cond {x \= 2; false} \-\> x \= 4 | else \-\> x \= 3; x}")  
            assertResult(NumV(3))(result)  
        }  
        unitTest("Interp CondE store chaining works 4") {  
            val result \= interp("let x \= 1 in {cond {x \= 2; true} \-\> x \= 4 | else \-\> x \= 3; x}")  
            assertResult(NumV(4))(result)  
        }  
        unitTest("Interp CondE store chaining works 5") {  
            val result \= interp("let x \= 1 in {cond {x \= 2; false} \-\> x \= 4 | false \-\> x \= 3 | else \-\> if x \== 2 then x \= 5 else 3; x}")  
            assertResult(NumV(5))(result)  
        }       

        unitTest("Interp Cons store chaining works") {  
            val result \= interp("let x \= 1 in {x \= 2; 1} :: x :: nil: Num")  
            assertResult(ConsV(NumV(1), ConsV(NumV(2), NilV())))(result)  
        }  
        unitTest("Interp Cons store chaining works 2") {  
            val result \= interp("let x \= 1 in {(x \= 2\) :: nil: Num; x}")  
            assertResult(NumV(2))(result)  
        }  
        unitTest("Interp Cons store chaining works 3") {  
            val result \= interp("let x \= 1 in {(x \= 2\) :: (x \= 3\) :: nil: Num; x}")  
            assertResult(NumV(3))(result)  
        }

        unitTest("Interp List store chaining works") {  
            val result \= interp("let x \= 1 in Num\[{x \= 2; 1}, x\]")  
            assertResult(ConsV(NumV(1), ConsV(NumV((2)), NilV())))(result)  
        }  
        unitTest("Interp List store chaining works 2") {  
            val result \= interp("let x \= 1 in {Num\[(x \= 2), 3\]; x}")  
            assertResult(NumV(2))(result)  
        }  
        unitTest("Interp List store chaining works 3") {  
            val result \= interp("let x \= 1 in {Num\[(x \= 2), (x \= 3)\]; x}")  
            assertResult(NumV(3))(result)  
        }

        unitTest("Interp Head store chaining works") {  
            val result \= interp("let x \= 1 in {head {x \= 2; Num\[1\]}; x}")  
            assertResult(NumV(2))(result)  
        }

        unitTest("Interp Tail store chaining works") {  
            val result \= interp("let x \= 1 in {tail {x \= 2; Num\[1\]}; x}")  
            assertResult(NumV(2))(result)  
        }

        unitTest("Interp IsNil store chaining works") {  
            val result \= interp("let x \= 1 in {{x \= 2; Num\[1\]} is nil; x}")  
            assertResult(NumV(2))(result)  
        }

        unitTest("Interp App store chaining works") {  
            val result \= interp("let x \= 0 in (lambda y: Num \-\> x)(x \= 2)")  
            assertResult(NumV(2))(result)  
        }  
        unitTest("Interp App store chaining works 2") {  
            val result \= interp("let x \= 0 in {(lambda y: Num \-\> x \= 3)(x \= 2); x}")  
            assertResult(NumV(3))(result)  
        }  
        unitTest("Interp App store chaining works 3") {  
            val result \= interp("let x \= 0 in ({x \= 1; lambda y: Num \-\> x})(if x \== 1 then x \= 2 else 3)")  
            assertResult(NumV(2))(result)  
        }  
        unitTest("Interp App store chaining works 4") {  
            val result \= interp("let x \= 0 in ({x \= 1; lambda y: Num, z: Num \-\> x})(if x \== 1 then x \= 2 else 3, if x \== 2 then x \= 4 else 5)")  
            assertResult(NumV(4))(result)  
        }  
        unitTest("Interp App store chaining works 5") {  
            val result \= interp("let x \= 0 in {({x \= 1; lambda y: Num, z: Num \-\> x \= 6})(if x \== 1 then x \= 2 else 3, if x \== 2 then x \= 4 else 5); x}")  
            assertResult(NumV(6))(result)  
        }

        unitTest("Interp Let store chaining works") {  
            val result \= interp("let x \= 0 in let y \= {x \= 1; 2} in x")  
            assertResult(NumV(1))(result)  
        }  
        unitTest("Interp Let store chaining works 2") {  
            val result \= interp("let x \= 0 in {let y \= 1 in x \= 2; x}")  
            assertResult(NumV(2))(result)  
        }  
        unitTest("Interp Let store chaining works 3") {  
            val result \= interp("let x \= 0 in {let y \= (x \= 1\) in x \= 2; x}")  
            assertResult(NumV(2))(result)  
        }

        unitTest("Interp LetRec store chaining works") {  
            val result \= interp("let x \= 0 in let rec y: Num \= {x \= 1; 2} in x")  
            assertResult(NumV(1))(result)  
        }  
        unitTest("Interp LetRec store chaining works 2") {  
            val result \= interp("let x \= 0 in {let rec y: Num \= 1 in x \= 2; x}")  
            assertResult(NumV(2))(result)  
        }  
        unitTest("Interp LetRec store chaining works 3") {  
            val result \= interp("let x \= 0 in {let rec y: Num \= (x \= 1\) in x \= 2; x}")  
            assertResult(NumV(2))(result)  
        }

        unitTest("Interp Box store chaining works") {  
            val result \= interp("let x \= 1 in {box {x \= 2; Num\[1\]}; x}")  
            assertResult(NumV(2))(result)  
        }

        unitTest("Interp Unbox store chaining works") {  
            val result \= interp("let x \= 1 in {unbox (box {x \= 2; Num\[1\]}); x}")  
            assertResult(NumV(2))(result)  
        }

        unitTest("Interp SetBox store chaining works") {  
            val result \= interp("let x \= box 0, y \= 0 in {set box {y \= 2; x} to y; y}")  
            assertResult(NumV(2))(result)  
        }  
        unitTest("Interp SetBox store chaining works 2") {  
            val result \= interp("let x \= box 0, y \= 0 in {set box {y \= 2; x} to y; unbox x}")  
            assertResult(NumV(2))(result)  
        }  
        unitTest("Interp SetBox store chaining works 3") {  
            val result \= interp("let x \= box 0, y \= 0 in {set box {y \= 2; x} to y \= 3; y}")  
            assertResult(NumV(3))(result)  
        }  
    }

    suite("Assignment 5") {

        unitTest("Interp Tuple works") {  
            val result \= interp("(1, Num\[2, 1\])")  
            assertResult(TupleV(List(NumV(1), ConsV(NumV(2), ConsV(NumV(1), NilV())))))(result)  
        }  
        unitTest("Interp Tuple works 2") {  
            val result \= interp("()")  
            assertResult(TupleV(List()))(result)  
        }  
        unitTest("Interp Tuple store chaining works") {  
            val result \= interp("let x \= 1 in ({x \= 2; 1}, x)")  
            assertResult(TupleV(List(NumV(1), NumV(2))))(result)  
        }  
        unitTest("Interp Tuple store chaining works 2") {  
            val result \= interp("let x \= 1 in {(x \= 2, 3); x}")  
            assertResult(NumV(2))(result)  
        }  
        unitTest("Interp Tuple store chaining works 3") {  
            val result \= interp("let x \= 1 in {(x \= 2, x \= 3); x}")  
            assertResult(NumV(3))(result)  
        }

        unitTest("Interp Proj works") {  
            val result \= interp("(1, Num\[2, 1\])\[0\]")  
            assertResult(NumV(1))(result)  
        }  
        unitTest("Interp Proj works 2") {  
            val result \= interp("(1, Num\[2, 1\])\[1\]")  
            assertResult(ConsV(NumV(2), ConsV(NumV(1), NilV())))(result)  
        }  
        unitTest("Interp Proj stops non-tuple value") {  
            assertThrows\[InterpException\] {  
                interp("1\[0\]")  
            }  
        }  
        unitTest("Interp Proj stops index above length") {  
            assertThrows\[InterpException\] {  
                interp("(1, 2)\[2\]")  
            }  
        }  
        unitTest("Interp Proj store chaining works") {  
            val result \= interp("let x \= 1 in {(x \= 2, 3)\[1\]; x}")  
            assertResult(NumV(2))(result)  
        }  
          
        unitTest("TypeOf Num") {  
            val result \= typeOf("1")  
            assertResult(NumT())(result)  
        }

        unitTest("TypeOf True") {  
            val result \= typeOf("true")  
            assertResult(BoolT())(result)  
        }

        unitTest("TypeOf False") {  
            val result \= typeOf("false")  
            assertResult(BoolT())(result)  
        }

        unitTest("TypeOf Neg works") {  
            val result \= typeOf("-2")  
            assertResult(NumT())(result)  
        }  
        unitTest("TypeOf Neg stops non-numbers") {  
            assertThrows\[TypeException\] {  
                typeOf("-true")  
            }  
        }  
        unitTest("TypeOf Neg stops non-numbers 2") {  
            assertThrows\[TypeException\] {  
                typeOf("-nil: Num")  
            }  
        }

        unitTest("TypeOf Add works") {  
            val result \= typeOf("2 \+ 3")  
            assertResult(NumT())(result)  
        }  
        unitTest("TypeOf Add stops non-numbers on the right") {  
            assertThrows\[TypeException\] {  
                typeOf("2 \+ true")  
            }  
        }  
        unitTest("TypeOf Add stops non-numbers on the left") {  
            assertThrows\[TypeException\] {  
                typeOf("nil: Num \+ 3")  
            }  
        }  
        unitTest("TypeOf Add stops two non-numbers") {  
            assertThrows\[TypeException\] {  
                typeOf("nil: Num \+ Num\[1, 2\]")  
            }  
        }

        unitTest("TypeOf Sub works") {  
            val result \= typeOf("4 \- 2")  
            assertResult(NumT())(result)  
        }  
        unitTest("TypeOf Sub stops non-numbers on the right") {  
            assertThrows\[TypeException\] {  
                typeOf("2 \- true")  
            }  
        }  
        unitTest("TypeOf Sub stops non-numbers on the left") {  
            assertThrows\[TypeException\] {  
                typeOf("nil: Num \- 3")  
            }  
        }  
        unitTest("TypeOf Sub stops two non-numbers") {  
            assertThrows\[TypeException\] {  
                typeOf("nil: Num \- Num\[1, 2\]")  
            }  
        }

        unitTest("TypeOf Mul works") {  
            val result \= typeOf("2 \* 3")  
            assertResult(NumT())(result)  
        }  
        unitTest("TypeOf Mul stops non-numbers on the right") {  
            assertThrows\[TypeException\] {  
                typeOf("2 \* true")  
            }  
        }  
        unitTest("TypeOf Mul stops non-numbers on the left") {  
            assertThrows\[TypeException\] {  
                typeOf("nil: Num \* 3")  
            }  
        }  
        unitTest("TypeOf Mul stops two non-numbers") {  
            assertThrows\[TypeException\] {  
                typeOf("nil: Num \* Num\[1, 2\]")  
            }  
        }

        unitTest("TypeOf EqNum works 1") {  
            val result \= typeOf("3 \== 3")  
            assertResult(BoolT())(result)  
        }  
        unitTest("TypeOf EqNum works 2") {  
            val result \= typeOf("2 \== 3")  
            assertResult(BoolT())(result)  
        }  
        unitTest("TypeOf EqNum works 3") {  
            val result \= typeOf("3 \== 2")  
            assertResult(BoolT())(result)  
        }  
        unitTest("TypeOf EqNum stops non-numbers on the right") {  
            assertThrows\[TypeException\] {  
                typeOf("2 \== true")  
            }  
        }  
        unitTest("TypeOf EqNum stops non-numbers on the left") {  
            assertThrows\[TypeException\] {  
                typeOf("nil: Num \== 3")  
            }  
        }  
        unitTest("TypeOf EqNum stops two non-numbers") {  
            assertThrows\[TypeException\] {  
                typeOf("nil: Num \== Num\[1, 2\]")  
            }  
        }

        unitTest("TypeOf LtNum works 1") {  
            val result \= typeOf("3 \< 4")  
            assertResult(BoolT())(result)  
        }  
        unitTest("TypeOf LtNum works 2") {  
            val result \= typeOf("3 \< 3")  
            assertResult(BoolT())(result)  
        }  
        unitTest("TypeOf LtNum works 3") {  
            val result \= typeOf("4 \< 3")  
            assertResult(BoolT())(result)  
        }  
        unitTest("TypeOf LtNum stops non-numbers on the right") {  
            assertThrows\[TypeException\] {  
                typeOf("2 \< true")  
            }  
        }  
        unitTest("TypeOf LtNum stops non-numbers on the left") {  
            assertThrows\[TypeException\] {  
                typeOf("nil: Num \< 3")  
            }  
        }  
        unitTest("TypeOf LtNum stops two non-numbers") {  
            assertThrows\[TypeException\] {  
                typeOf("nil: Num \< Num\[1, 2\]")  
            }  
        }

        unitTest("TypeOf GtNum works 1") {  
            val result \= typeOf("3 \> 4")  
            assertResult(BoolT())(result)  
        }  
        unitTest("TypeOf GtNum works 2") {  
            val result \= typeOf("3 \> 3")  
            assertResult(BoolT())(result)  
        }  
        unitTest("TypeOf GtNum works 3") {  
            val result \= typeOf("4 \> 3")  
            assertResult(BoolT())(result)  
        }  
        unitTest("TypeOf GtNum stops non-numbers on the right") {  
            assertThrows\[TypeException\] {  
                typeOf("2 \> true")  
            }  
        }  
        unitTest("TypeOf GtNum stops non-numbers on the left") {  
            assertThrows\[TypeException\] {  
                typeOf("nil: Num \> 3")  
            }  
        }  
        unitTest("TypeOf GtNum stops two non-numbers") {  
            assertThrows\[TypeException\] {  
                typeOf("nil: Num \> Num\[1, 2\]")  
            }  
        }

        unitTest("TypeOf Not works") {  
            val result \= typeOf("\!true")  
            assertResult(BoolT())(result)  
        }  
        unitTest("TypeOf Not works 2") {  
            val result \= typeOf("\!false")  
            assertResult(BoolT())(result)  
        }  
        unitTest("TypeOf Not stops non-bools") {  
            assertThrows\[TypeException\] {  
                typeOf("\!0")  
            }  
        }  
        unitTest("TypeOf Not stops non-bools 2") {  
            assertThrows\[TypeException\] {  
                typeOf("\!nil: Bool")  
            }  
        }

        unitTest("TypeOf And works 1") {  
            val result \= typeOf("true && true")  
            assertResult(BoolT())(result)  
        }  
        unitTest("TypeOf And works 2") {  
            val result \= typeOf("true && false")  
            assertResult(BoolT())(result)  
        }  
        unitTest("TypeOf And works 3") {  
            val result \= typeOf("false && true")  
            assertResult(BoolT())(result)  
        }  
        unitTest("TypeOf And works 4") {  
            val result \= typeOf("false && false")  
            assertResult(BoolT())(result)  
        }  
        unitTest("TypeOf And stops non-bools on the left") {  
            assertThrows\[TypeException\] {  
                typeOf("nil: Bool && true")  
            }  
        }  
        unitTest("TypeOf And stops non-bools on the right") {  
            assertThrows\[TypeException\] {  
                typeOf("true && 3")  
            }  
        }  
        unitTest("TypeOf And stops two non-bools") {  
            assertThrows\[TypeException\] {  
                typeOf("nil: Num && Num\[1, 2\]")  
            }  
        }

        unitTest("TypeOf Or works 1") {  
            val result \= typeOf("true || true")  
            assertResult(BoolT())(result)  
        }  
        unitTest("TypeOf Or works 2") {  
            val result \= typeOf("true || false")  
            assertResult(BoolT())(result)  
        }  
        unitTest("TypeOf Or works 3") {  
            val result \= typeOf("false || true")  
            assertResult(BoolT())(result)  
        }  
        unitTest("TypeOf Or works 4") {  
            val result \= typeOf("false || false")  
            assertResult(BoolT())(result)  
        }  
        unitTest("TypeOf Or stops non-bools on the right") {  
            assertThrows\[TypeException\] {  
                typeOf("true || 3")  
            }  
        }  
        unitTest("TypeOf Or stops non-bools on the left") {  
            assertThrows\[TypeException\] {  
                typeOf("nil: Bool || true")  
            }  
        }  
        unitTest("TypeOf Or stops two non-bools") {  
            assertThrows\[TypeException\] {  
                typeOf("nil: Num || Num\[1, 2\]")  
            }  
        }

        unitTest("TypeOf If works 1") {  
            val result \= typeOf("if true then 1 else 2")  
            assertResult(NumT())(result)  
        }  
        unitTest("TypeOf If works 2") {  
            val result \= typeOf("if false then 1 else 2")  
            assertResult(NumT())(result)  
        }  
        unitTest("TypeOf If stops mismatched branch types") {  
            assertThrows\[TypeException\] {  
                typeOf("if true then true else 1")  
            }  
        }  
        unitTest("TypeOf If stops mismatched branch types 2") {  
            assertThrows\[TypeException\] {  
                typeOf("if false then true else Bool\[true\]")  
            }  
        }  
        unitTest("TypeOf If stops non-bool condition") {  
            assertThrows\[TypeException\] {  
                typeOf("if 1 then true else false")  
            }  
        }  
        unitTest("TypeOf If stops non-bool condition 2") {  
            assertThrows\[TypeException\] {  
                typeOf("if nil: Bool then true else false")  
            }  
        }

        unitTest("TypeOf Cond works 1") {  
            val result \= typeOf("cond true \-\> 1 | false \-\> 3 | false \-\> 2")  
            assertResult(NumT())(result)  
        }  
        unitTest("TypeOf Cond works 2") {  
            val result \= typeOf("cond false \-\> true | true \-\> true | false \-\> false")  
            assertResult(BoolT())(result)  
        }  
        unitTest("TypeOf Cond works 3") {  
            val result \= typeOf("cond false \-\> 1 | false \-\> 3 | true \-\> 2")  
            assertResult(NumT())(result)  
        }  
        unitTest("TypeOf Cond stops mismatched branch types") {  
            assertThrows\[TypeException\] {  
                typeOf("cond false \-\> 1 | true \-\> true | true \-\> 2")  
            }  
        }  
        unitTest("TypeOf Cond stops mismatched branch types 2") {  
            assertThrows\[TypeException\] {  
                typeOf("cond false \-\> 1 | true \-\> 1 | true \-\> true")  
            }  
        }  
        unitTest("TypeOf Cond stops non-bool condition") {  
            assertThrows\[TypeException\] {  
                typeOf("cond false \-\> 1 | 0 \-\> 1 | true \-\> 2")  
            }  
        }

        unitTest("TypeOf CondE works 1") {  
            val result \= typeOf("cond true \-\> 1 | false \-\> 3 | else \-\> 2")  
            assertResult(NumT())(result)  
        }  
        unitTest("TypeOf CondE works 2") {  
            val result \= typeOf("cond false \-\> true | true \-\> true | else \-\> false")  
            assertResult(BoolT())(result)  
        }  
        unitTest("TypeOf CondE works 3") {  
            val result \= typeOf("cond false \-\> 1 | false \-\> 3 | else \-\> 2")  
            assertResult(NumT())(result)  
        }  
        unitTest("TypeOf CondE stops mismatched branch types") {  
            assertThrows\[TypeException\] {  
                typeOf("cond false \-\> 1 | true \-\> true | else \-\> 2")  
            }  
        }  
        unitTest("TypeOf CondE stops mismatched branch types 2") {  
            assertThrows\[TypeException\] {  
                typeOf("cond false \-\> 1 | true \-\> 1 | else \-\> true")  
            }  
        }  
        unitTest("TypeOf CondE stops non-bool condition") {  
            assertThrows\[TypeException\] {  
                typeOf("cond false \-\> 1 | 0 \-\> 1 | else \-\> 2")  
            }  
        }

        unitTest("TypeOf Nil") {  
            val result \= typeOf("nil: Num")  
            assertResult(ListT(NumT()))(result)  
        }

        unitTest("TypeOf Cons works") {  
            val result \= typeOf("1 :: 2 :: nil: Num")  
            assertResult(ListT(NumT()))(result)  
        }  
        unitTest("TypeOf Cons stops non-list tail") {  
            assertThrows\[TypeException\] {  
                typeOf("1 :: 2")  
            }  
        }  
        unitTest("TypeOf Cons stops mismatched entry types") {  
            assertThrows\[TypeException\] {  
                typeOf("1 :: true :: nil: Bool")  
            }  
        }

        unitTest("TypeOf List works 1") {  
            val result \= typeOf("Num\[1\]")  
            assertResult(ListT(NumT()))(result)  
        }  
        unitTest("TypeOf List works 2") {  
            val result \= typeOf("Num\[1, 2\]")  
            assertResult(ListT(NumT()))(result)  
        }  
        unitTest("TypeOf List works 3") {  
            val result \= typeOf("Num\[\]")  
            assertResult(ListT(NumT()))(result)  
        }  
        unitTest("TypeOf List stops mismatched entry types") {  
            assertThrows\[TypeException\] {  
                typeOf("Num\[true, 1\]")  
            }  
        }

        unitTest("TypeOf Head works 1") {  
            val result \= typeOf("head Num\[1\]")  
            assertResult(NumT())(result)  
        }  
        unitTest("TypeOf Head works 2") {  
            val result \= typeOf("head Num\[2, 1\]")  
            assertResult(NumT())(result)  
        }  
        unitTest("TypeOf Head works 3") {  
            val result \= typeOf("head Num\[\]")  
            assertResult(NumT())(result)  
        }  
        unitTest("TypeOf Head stops non-list value") {  
            assertThrows\[TypeException\] {  
                typeOf("head 1")  
            }  
        }

        unitTest("TypeOf Tail works 1") {  
            val result \= typeOf("tail Num\[1\]")  
            assertResult(ListT(NumT()))(result)  
        }  
        unitTest("TypeOf Tail works 2") {  
            val result \= typeOf("tail Num\[2, 1\]")  
            assertResult(ListT(NumT()))(result)  
        }  
        unitTest("TypeOf Tail works 3") {  
            val result \= typeOf("tail Num\[\]")  
            assertResult(ListT(NumT()))(result)  
        }  
        unitTest("TypeOf Tail stops non-list value") {  
            assertThrows\[TypeException\] {  
                typeOf("tail 1")  
            }  
        }

        unitTest("TypeOf IsNil works 1") {  
            val result \= typeOf("Num\[1\] is nil")  
            assertResult(BoolT())(result)  
        }  
        unitTest("TypeOf IsNil works 2") {  
            val result \= typeOf("Num\[\] is nil")  
            assertResult(BoolT())(result)  
        }  
        unitTest("TypeOf IsNil stops non-list value") {  
            assertThrows\[TypeException\] {  
                typeOf("1 is nil")  
            }  
        }  
        unitTest("TypeOf IsNil stops non-list value 2") {  
            assertThrows\[TypeException\] {  
                typeOf("true is nil")  
            }  
        }

        unitTest("TypeOf Id works") {  
            val result \= typeOf("let x \= 1 in x")  
            assertResult(NumT())(result)  
        }  
        unitTest("TypeOf Id works 2") {  
            val result \= typeOf("(lambda x: \[Num\] \-\> x)(Num\[1, 2\])")  
            assertResult(ListT(NumT()))(result)  
        }  
        unitTest("TypeOf Id stops unbound variable") {  
            assertThrows\[TypeException\] {  
                typeOf("x")  
            }  
        }  
        unitTest("TypeOf Id stops unbound variable 2") {  
            assertThrows\[TypeException\] {  
                typeOf("let y \= 1 in x")  
            }  
        }

        unitTest("TypeOf Lambda works") {  
            val result \= typeOf("lambda \-\> 1")  
            assertResult(FunT(List(), NumT()))(result)  
        }  
        unitTest("TypeOf Lambda works 2") {  
            val result \= typeOf("lambda x: Num, y: \[Num\] \-\> 1")  
            assertResult(FunT(List(NumT(), ListT(NumT())), NumT()))(result)  
        }

        unitTest("TypeOf App works") {  
            val result \= typeOf("(lambda \-\> 2)()")  
            assertResult(NumT())(result)  
        }  
        unitTest("TypeOf App works 2") {  
            val result \= typeOf("(lambda x: Num \-\> x)(1)")  
            assertResult(NumT())(result)  
        }  
        unitTest("TypeOf App binds properly") {  
            val result \= typeOf("(lambda x: \[Num\], y: Num \-\> x)(Num\[1, 2\], 3)")  
            assertResult(ListT(NumT()))(result)  
        }  
        unitTest("TypeOf App binds properly 2") {  
            val result \= typeOf("(lambda x: \[Num\], y: Num \-\> y)(Num\[1, 2\], 3)")  
            assertResult(NumT())(result)  
        }  
        unitTest("TypeOf App nested binds properly") {  
            val result \= typeOf("(lambda x: \[Num\] \-\> lambda y: Num \-\> x)(Num\[1, 2\])(3)")  
            assertResult(ListT(NumT()))(result)  
        }  
        unitTest("TypeOf App nested binds properly 2") {  
            val result \= typeOf("(lambda x: \[Num\] \-\> lambda y: Num \-\> y)(Num\[1, 2\])(3)")  
            assertResult(NumT())(result)  
        }  
        unitTest("TypeOf App allows name shadowing") {  
            val result \= typeOf("(lambda x: Num \-\> lambda x: Bool \-\> x)(2)(true)")  
            assertResult(BoolT())(result)  
        }  
        unitTest("TypeOf App stops more arguments than parameters") {  
            assertThrows\[TypeException\] {  
                typeOf("(lambda x: Num \-\> x)(1, 2)")  
            }  
        }  
        unitTest("TypeOf App stops more parameters than arguments") {  
            assertThrows\[TypeException\] {  
                typeOf("(lambda x: Num, y: Num \-\> x)(1)")  
            }  
        }  
        unitTest("TypeOf App stops mismatched parameters and arguments") {  
            assertThrows\[TypeException\] {  
                typeOf("(lambda x: Num, y: Num \-\> x)(1, true)")  
            }  
        }

        unitTest("TypeOf Let works") {  
            val result \= typeOf("let x \= 1 in 2")  
            assertResult(NumT())(result)  
        }  
        unitTest("TypeOf Let works 2") {  
            val result \= typeOf("let x \= 1 in x")  
            assertResult(NumT())(result)  
        }  
        unitTest("TypeOf Let binds properly") {  
            val result \= typeOf("let x \= 1, y \= true in x")  
            assertResult(NumT())(result)  
        }  
        unitTest("TypeOf Let binds properly 2") {  
            val result \= typeOf("let x \= true, y \= 2 in y")  
            assertResult(NumT())(result)  
        }  
        unitTest("TypeOf Let nested binds properly") {  
            val result \= typeOf("let x \= 1 in let y \= true in x")  
            assertResult(NumT())(result)  
        }  
        unitTest("TypeOf Let nested binds properly 2") {  
            val result \= typeOf("let x \= true in let y \= 2 in y")  
            assertResult(NumT())(result)  
        }  
        unitTest("TypeOf Let allows name shadowing") {  
            val result \= typeOf("let x \= true in let x \= 1 in x")  
            assertResult(NumT())(result)  
        }

        unitTest("TypeOf Set works") {  
            val result \= typeOf("let x \= 2 in x \= 3")  
            assertResult(NumT())(result)  
        }  
        unitTest("TypeOf Set works 2") {  
            val result \= typeOf("let x \= 2, y \= 1 in {x \= 3; y \= 4; x \+ y}")  
            assertResult(NumT())(result)  
        }  
        unitTest("TypeOf Set stops mismatched type setting") {  
            assertThrows\[TypeException\] {  
                typeOf("let x \= 1 in x \= true")  
            }  
        }

        unitTest("TypeOf Sequence works") {  
            val result \= typeOf("{4}")  
            assertResult(NumT())(result)  
        }  
        unitTest("TypeOf Sequence works 2") {  
            val result \= typeOf("let x \= 1 in {x; 4}")  
            assertResult(NumT())(result)  
        }  
        unitTest("TypeOf Sequence works 3") {  
            val result \= typeOf("let x \= 1 in {x \= 2; x \= 3; x \+ 1}")  
            assertResult(NumT())(result)  
        }

        unitTest("TypeOf LetRec works") {  
            val result \= typeOf("let rec x: Num \= 4 in x")  
            assertResult(NumT())(result)  
        }  
        unitTest("TypeOf LetRec works 2") {  
            val result \= typeOf("let rec x: Num \= 1, y: Num \= x in y")  
            assertResult(NumT())(result)  
        }  
        unitTest("TypeOf LetRec works 3") {  
            val result \= typeOf("let rec sum: (Num) \-\> Num \= lambda x: Num \-\> if x \< 1 then 0 else x \+ sum(x \- 1\) in sum(5)")  
            assertResult(NumT())(result)  
        }  
        unitTest("TypeOf LetRec works 4") {  
            val result \= typeOf(  
                """  
                let rec sum: (Num) \-\> Num \= lambda x: Num \-\> if x \< 1 then 0 else x \+ sum2(x \- 1),  
                        sum2: (Num) \-\> Num \= lambda x: Num \-\> if x \< 1 then 0 else x \+ sum(x \- 1\)   
                        in   
                            sum(5)  
                """)  
            assertResult(NumT())(result)  
        }  
        unitTest("TypeOf LetRec allows reverse variable passing") {  
            val result \= typeOf("let rec x: Num \= y, y: Num \= 1 in x")  
            assertResult(NumT())(result)  
        }  
        unitTest("TypeOf LetRec stops parameters") {  
            assertThrows\[TypeException\] {  
                typeOf("let rec x: Bool \= 1 in x")  
            }  
        }

        unitTest("TypeOf Box works") {  
            val result \= typeOf("box 4")  
            assertResult(RefT(NumT()))(result)  
        }  
        unitTest("TypeOf Box works 2") {  
            val result \= typeOf("let b \= box 0 in b")  
            assertResult(RefT(NumT()))(result)  
        }

        unitTest("TypeOf Unbox works") {  
            val result \= typeOf("unbox (box 4)")  
            assertResult(NumT())(result)  
        }  
        unitTest("TypeOf Unbox works 2") {  
            val result \= typeOf("unbox (box true)")  
            assertResult(BoolT())(result)  
        }  
        unitTest("TypeOf Unbox works 3") {  
            val result \= typeOf("let b \= box 0 in unbox b")  
            assertResult(NumT())(result)  
        }  
        unitTest("TypeOf Unbox stops non-Ref argument") {  
            assertThrows\[TypeException\] {  
                typeOf("unbox 1")  
            }  
        }

        unitTest("TypeOf SetBox works") {  
            val result \= typeOf("let b \= box 0 in set box b to 4")  
            assertResult(NumT())(result)  
        }  
        unitTest("TypeOf SetBox works 2") {  
            val result \= typeOf("set box (box true) to false")  
            assertResult(BoolT())(result)  
        }  
        unitTest("TypeOf SetBox stops non-Ref type before to") {  
            assertThrows\[TypeException\] {  
                typeOf("set box 1 to 2")  
            }  
        }  
        unitTest("TypeOf SetBox stops mismatched set type") {  
            assertThrows\[TypeException\] {  
                typeOf("set box (box 1\) to true")  
            }  
        }

        unitTest("TypeOf Tuple works") {  
            val result \= typeOf("(1, Num\[2, 1\])")  
            assertResult(TupleT(List(NumT(), ListT(NumT()))))(result)  
        }  
        unitTest("TypeOf Tuple works 2") {  
            val result \= typeOf("()")  
            assertResult(TupleT(List()))(result)  
        }

        unitTest("TypeOf Proj works") {  
            val result \= typeOf("(1, Num\[2, 1\])\[0\]")  
            assertResult(NumT())(result)  
        }  
        unitTest("TypeOf Proj works 2") {  
            val result \= typeOf("(1, Num\[2, 1\])\[1\]")  
            assertResult(ListT(NumT()))(result)  
        }  
        unitTest("TypeOf Proj stops non-tuple value") {  
            assertThrows\[TypeException\] {  
                typeOf("1\[0\]")  
            }  
        }  
        unitTest("TypeOf Proj stops index above length") {  
            assertThrows\[TypeException\] {  
                typeOf("(1, 2)\[2\]")  
            }  
        }  
    }

    suite("Compound") {

        unitTest("Interp complex") {  
            val result \= interp(  
                """  
                  | (1 \+ 2\) \*  
                  | (3 \+ 5\) \-  
                  | 1  
                  |""".stripMargin)  
            assertResult(NumV(3\*8-1))(result)  
        }

        unitTest("Interp static scope") {  
            val result \= interp(  
                """  
                  | let x \= 1 in (lambda x: Num \-\>  
                  |   x)(2)  
                  |""".stripMargin  
            )  
            assertResult(NumV(2))(result)  
        }

        unitTest("Interp let has no access to current binds") {  
            val result \= interp(  
                """  
                  let x \= 3 in let x \= 1, y \= x in x \+ y  
                  """.stripMargin  
            )  
            assertResult(NumV(4))(result)  
        }

        unitTest("Interp no args lambda") {  
            val result \= interp(  
                """  
                  let x \= lambda \-\> 1 in x()  
                  """.stripMargin  
            )  
            assertResult(NumV(1))(result)  
        }

        unitTest("Interp store chaining with app") {  
            val result \= interp(  
                """  
                  let x \= 0 in  
                    let s \= {x \= 1;lambda z: Num \-\> lambda y: Num \-\> x}  
                    in s(if x \== 1 then x \= 2 else x \= 5)(if x \== 2 then x \= 3 else 4\)  
                  """.stripMargin  
            )  
            assertResult(NumV(3))(result)  
        }

        unitTest("Interp no args lambda desugared") {  
            val result \= interp(  
                """  
                  (lambda x: () \-\> Num \-\> x())(lambda \-\> 1\)  
                  """.stripMargin  
            )  
            assertResult(NumV(1))(result)  
        }  
          
        unitTest("Interp let stack") {  
            val result \= interp(  
                """  
                  let x \= 17 in   
    let y \= x \+ 3 in  
        let z \= x \+ y \+ 22 in  
            let x \= z \* 2 in  
                x  
                  """.stripMargin  
            )  
            assertResult(NumV(118))(result)  
        }  
        unitTest("Interp merge recursive") {  
            val result \= interp(  
                """  
                let rec merge: (\[Num\], \[Num\]) \-\> \[Num\] \=   
                    lambda x: \[Num\], y: \[Num\] \-\>   
                        if (x is nil) then y else  
                        (  
                            if (y is nil) then x else  
                            (  
                                if(head x \< head y) then   
                                    (head x) :: merge((tail x), y)  
                                else   
                                    (head y) :: merge(x, (tail y))  
                            )  
                        )  
                in  
                    merge(Num\[1, 2, 5\], Num\[3, 4\])  
                  """.stripMargin  
            )  
            assertResult(ConsV(NumV(1),ConsV(NumV(2),ConsV(NumV(3),ConsV(NumV(4),ConsV(NumV(5),NilV()))))))(result)  
        }

        unitTest("Interp mergesort recursive") {  
            val result \= interp(  
                """  
                let rec merge: (\[Num\], \[Num\]) \-\> \[Num\] \=   
                    lambda x: \[Num\], y: \[Num\] \-\>   
                        if (x is nil) then y else  
                        (  
                            if (y is nil) then x else  
                            (  
                                if(head x \< head y) then   
                                    (head x) :: merge((tail x), y)  
                                else   
                                    (head y) :: merge(x, (tail y))  
                            )  
                        ),  
                len: (\[Num\]) \-\> Num \= lambda x: \[Num\] \-\>  
                    if(x is nil) then 0 else  
                        (1 \+ len(tail x)),  
                divBy2Helper: (Num) \-\> (Num) \-\> Num \=   
                    lambda x: Num \-\> lambda target: Num \-\>  
                        if(x \* 2 \== target) then  
                            x   
                        else if (x \* 2 \> target) then   
                            x \- 1   
                        else   
                            divBy2Helper(x \+ 1)(target),  
                divBy2: (Num) \-\> Num \= divBy2Helper(0),  
                digFirst: (\[Num\], Num) \-\> \[Num\] \= lambda x: \[Num\], n: Num \-\>   
                        if(n \== 1\) then (Num\[head x\]) else (head x) :: digFirst(tail x, n \- 1),  
                digLast: (\[Num\], Num) \-\> \[Num\] \= lambda x: \[Num\], n: Num \-\>   
                        if(n \== 1\) then (tail x) else digLast(tail x, n \- 1),  
                mergeSort: (\[Num\]) \-\> \[Num\] \= lambda x: \[Num\] \-\>  
                        let length \= len(x) in  
                        if (length \< 2\) then x else  
                        let halfLen \= divBy2(length) in  
                        let left \= digFirst(x, halfLen), right \= digLast(x, halfLen) in  
                        let newLeft \= mergeSort(left), newRight \= mergeSort(right) in  
                        merge(newLeft, newRight)  
        in  
            mergeSort(Num\[83, 12, 47, 95, 6, 71, 29, 54, 18, 90, 3, 64, 41, 77, 22,  
            58, 99, 14, 36, 85, 1, 73, 49, 27, 60, 9, 92, 33, 68, 20, 56, 11, 80,  
            44, 97, 5, 39, 74, 24, 63, 88, 16, 52, 30, 70, 8, 94, 45, 2, 79, 34,  
            61, 21, 87, 13, 50, 98, 25, 66, 4, 82, 37, 59, 19, 75, 28, 91, 46, 10,  
            72, 35, 84, 7, 62, 26, 93, 40, 57, 15, 76, 31, 89, 23, 65, 17, 100, 43,  
            69, 32, 81, 48, 96, 38, 53, 78, 55, 42, 67, 86, 51\])  
                  """.stripMargin)  
              
            assertResult(ConsV(NumV(1),ConsV(NumV(2),ConsV(NumV(3),ConsV(NumV(4),ConsV(NumV(5),ConsV(NumV(6),ConsV(NumV(7),ConsV(NumV(8),ConsV(NumV(9),ConsV(NumV(10),ConsV(NumV(11),ConsV(NumV(12),ConsV(NumV(13),ConsV(NumV(14),ConsV(NumV(15),ConsV(NumV(16),ConsV(NumV(17),ConsV(NumV(18),ConsV(NumV(19),ConsV(NumV(20),ConsV(NumV(21),ConsV(NumV(22),ConsV(NumV(23),ConsV(NumV(24),ConsV(NumV(25),ConsV(NumV(26),ConsV(NumV(27),ConsV(NumV(28),ConsV(NumV(29),ConsV(NumV(30),ConsV(NumV(31),ConsV(NumV(32),ConsV(NumV(33),ConsV(NumV(34),ConsV(NumV(35),ConsV(NumV(36),ConsV(NumV(37),ConsV(NumV(38),ConsV(NumV(39),ConsV(NumV(40),ConsV(NumV(41),ConsV(NumV(42),ConsV(NumV(43),ConsV(NumV(44),ConsV(NumV(45),ConsV(NumV(46),ConsV(NumV(47),ConsV(NumV(48),ConsV(NumV(49),ConsV(NumV(50),ConsV(NumV(51),ConsV(NumV(52),ConsV(NumV(53),ConsV(NumV(54),ConsV(NumV(55),ConsV(NumV(56),ConsV(NumV(57),ConsV(NumV(58),ConsV(NumV(59),ConsV(NumV(60),ConsV(NumV(61),ConsV(NumV(62),ConsV(NumV(63),ConsV(NumV(64),ConsV(NumV(65),ConsV(NumV(66),ConsV(NumV(67),ConsV(NumV(68),ConsV(NumV(69),ConsV(NumV(70),ConsV(NumV(71),ConsV(NumV(72),ConsV(NumV(73),ConsV(NumV(74),ConsV(NumV(75),ConsV(NumV(76),ConsV(NumV(77),ConsV(NumV(78),ConsV(NumV(79),ConsV(NumV(80),ConsV(NumV(81),ConsV(NumV(82),ConsV(NumV(83),ConsV(NumV(84),ConsV(NumV(85),ConsV(NumV(86),ConsV(NumV(87),ConsV(NumV(88),ConsV(NumV(89),ConsV(NumV(90),ConsV(NumV(91),ConsV(NumV(92),ConsV(NumV(93),ConsV(NumV(94),ConsV(NumV(95),ConsV(NumV(96),ConsV(NumV(97),ConsV(NumV(98),ConsV(NumV(99),ConsV(NumV(100),NilV())))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))(result)  
        }

        unitTest("Interp sum recursive") {  
            val result \= interp(  
                """  
                let rec sum: (\[Num\]) \-\> Num \=  
                    lambda x: \[Num\] \-\>  
                        if (x is nil) then 0 else  
                        (head x) \+ sum(tail x)  
                in  
                    sum(Num\[1, 2, 5\])  
                """.stripMargin  
            )  
            assertResult(NumV(8))(result)  
        }

        unitTest("Interp recursive data") {  
            val result \= interp(  
                """  
    let b \= box 0 in  
        {set box b to b; unbox (unbox (unbox (unbox b)))}  
          
                  """.stripMargin  
            )  
            assert(result.isInstanceOf\[BoxV\])  
        }

        unitTest("TypeOf complex") {  
            val result \= typeOf(  
                """  
                  | (1 \+ 2\) \*  
                  | (3 \+ 5\) \-  
                  | 1  
                  |""".stripMargin)  
            assertResult(NumT())(result)  
        }

        unitTest("TypeOf static scope") {  
            val result \= typeOf(  
                """  
                  | let x \= 1 in (lambda x: Num \-\>  
                  |   x)(2)  
                  |""".stripMargin  
            )  
            assertResult(NumT())(result)  
        }

        unitTest("TypeOf let has no access to current binds") {  
            val result \= typeOf(  
                """  
                  let x \= 3 in let x \= 1, y \= x in x \+ y  
                  """.stripMargin  
            )  
            assertResult(NumT())(result)  
        }

        unitTest("TypeOf no args lambda") {  
            val result \= typeOf(  
                """  
                  let x \= lambda \-\> 1 in x()  
                  """.stripMargin  
            )  
            assertResult(NumT())(result)  
        }

        unitTest("TypeOf store chaining with app") {  
            val result \= typeOf(  
                """  
                  let x \= 0 in  
                    let s \= {x \= 1;lambda z: Num \-\> lambda y: Num \-\> x}  
                    in s(if x \== 1 then x \= 2 else x \= 5)(if x \== 2 then x \= 3 else 4\)  
                  """.stripMargin  
            )  
            assertResult(NumT())(result)  
        }

        unitTest("TypeOf no args lambda desugared") {  
            val result \= typeOf(  
                """  
                  (lambda x: () \-\> Num \-\> x())(lambda \-\> 1\)  
                  """.stripMargin  
            )  
            assertResult(NumT())(result)  
        }  
          
        unitTest("TypeOf let stack") {  
            val result \= typeOf(  
                """  
                  let x \= 17 in   
    let y \= x \+ 3 in  
        let z \= x \+ y \+ 22 in  
            let x \= z \* 2 in  
                x  
                  """.stripMargin  
            )  
            assertResult(NumT())(result)  
        }  
        unitTest("TypeOf merge recursive") {  
            val result \= typeOf(  
                """  
                let rec merge: (\[Num\], \[Num\]) \-\> \[Num\] \=   
                    lambda x: \[Num\], y: \[Num\] \-\>   
                        if (x is nil) then y else  
                        (  
                            if (y is nil) then x else  
                            (  
                                if(head x \< head y) then   
                                    (head x) :: merge((tail x), y)  
                                else   
                                    (head y) :: merge(x, (tail y))  
                            )  
                        )  
                in  
                    merge(Num\[1, 2, 5\], Num\[3, 4\])  
                  """.stripMargin  
            )  
            assertResult(ListT(NumT()))(result)  
        }

        unitTest("TypeOf mergesort recursive") {  
            val result \= typeOf(  
                """  
                let rec merge: (\[Num\], \[Num\]) \-\> \[Num\] \=   
                    lambda x: \[Num\], y: \[Num\] \-\>   
                        if (x is nil) then y else  
                        (  
                            if (y is nil) then x else  
                            (  
                                if(head x \< head y) then   
                                    (head x) :: merge((tail x), y)  
                                else   
                                    (head y) :: merge(x, (tail y))  
                            )  
                        ),  
                len: (\[Num\]) \-\> Num \= lambda x: \[Num\] \-\>  
                    if(x is nil) then 0 else  
                        (1 \+ len(tail x)),  
                divBy2Helper: (Num) \-\> (Num) \-\> Num \=   
                    lambda x: Num \-\> lambda target: Num \-\>  
                        if(x \* 2 \== target) then  
                            x   
                        else if (x \* 2 \> target) then   
                            x \- 1   
                        else   
                            divBy2Helper(x \+ 1)(target),  
                divBy2: (Num) \-\> Num \= divBy2Helper(0),  
                digFirst: (\[Num\], Num) \-\> \[Num\] \= lambda x: \[Num\], n: Num \-\>   
                        if(n \== 1\) then (Num\[head x\]) else (head x) :: digFirst(tail x, n \- 1),  
                digLast: (\[Num\], Num) \-\> \[Num\] \= lambda x: \[Num\], n: Num \-\>   
                        if(n \== 1\) then (tail x) else digLast(tail x, n \- 1),  
                mergeSort: (\[Num\]) \-\> \[Num\] \= lambda x: \[Num\] \-\>  
                        let length \= len(x) in  
                        if (length \< 2\) then x else  
                        let halfLen \= divBy2(length) in  
                        let left \= digFirst(x, halfLen), right \= digLast(x, halfLen) in  
                        let newLeft \= mergeSort(left), newRight \= mergeSort(right) in  
                        merge(newLeft, newRight)  
        in  
            mergeSort(Num\[83, 12, 47, 95, 6, 71, 29, 54, 18, 90, 3, 64, 41, 77, 22,  
            58, 99, 14, 36, 85, 1, 73, 49, 27, 60, 9, 92, 33, 68, 20, 56, 11, 80,  
            44, 97, 5, 39, 74, 24, 63, 88, 16, 52, 30, 70, 8, 94, 45, 2, 79, 34,  
            61, 21, 87, 13, 50, 98, 25, 66, 4, 82, 37, 59, 19, 75, 28, 91, 46, 10,  
            72, 35, 84, 7, 62, 26, 93, 40, 57, 15, 76, 31, 89, 23, 65, 17, 100, 43,  
            69, 32, 81, 48, 96, 38, 53, 78, 55, 42, 67, 86, 51\])  
                  """.stripMargin)  
              
            assertResult(ListT(NumT()))(result)  
        }

        unitTest("TypeOf sum recursive") {  
            val result \= typeOf(  
                """  
                let rec sum: (\[Num\]) \-\> Num \=  
                    lambda x: \[Num\] \-\>  
                        if (x is nil) then 0 else  
                        (head x) \+ sum(tail x)  
                in  
                    sum(Num\[1, 2, 5\])  
                """.stripMargin  
            )  
            assertResult(NumT())(result)  
        }  
    }

    def typeOf(expr: String): Type \= {  
        val parsed \= CPLParser.parse(expr)  
        TypeCheck.typeOf(parsed, Nil)  
    }

    def desugar(expr: String): ExprC \= {  
        val parsed \= CPLParser.parse(expr)  
        Desugar.desugar(parsed)  
    }

    def interp(expr: String): Value \= {  
        val desugared \= desugar(expr)  
        Interpret.interp(desugared, Nil, Nil).\_1  
    }

}

