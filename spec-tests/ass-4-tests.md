import nl.tudelft.cpl.test.\*  
import nl.tudelft.cpl.language.Library.\*

class Tests extends CPLTestSuite {

    suite("Assignment 1") {  
         
        unitTest("Num") {  
            val result \= interp("1")  
            assertResult(NumV(1))(result)  
            // or assert(result \== NumV(1)), but that gives less clear error messages  
        }

        unitTest("True") {  
            val result \= interp("true")  
            assertResult(BoolV(true))(result)  
        }

        unitTest("False") {  
            val result \= interp("false")  
            assertResult(BoolV(false))(result)  
        }

        unitTest("Neg works") {  
            val result \= interp("-2")  
            assertResult(NumV(-2))(result)  
        }  
        unitTest("Neg stops non-numbers") {  
            assertThrows\[InterpException\] {  
                interp("-true")  
            }  
        }  
        unitTest("Neg stops non-numbers 2") {  
            assertThrows\[InterpException\] {  
                interp("-nil")  
            }  
        }

        unitTest("Add works") {  
            val result \= interp("2 \+ 3")  
            assertResult(NumV(5))(result)  
        }  
        unitTest("Add stops non-numbers on the right") {  
            assertThrows\[InterpException\] {  
                interp("2 \+ true")  
            }  
        }  
        unitTest("Add stops non-numbers on the left") {  
            assertThrows\[InterpException\] {  
                interp("nil \+ 3")  
            }  
        }  
        unitTest("Add stops two non-numbers") {  
            assertThrows\[InterpException\] {  
                interp("nil \+ \[1, 2\]")  
            }  
        }

        unitTest("Sub works") {  
            val result \= interp("4 \- 2")  
            assertResult(NumV(2))(result)  
        }  
        unitTest("Sub stops non-numbers on the right") {  
            assertThrows\[InterpException\] {  
                interp("2 \- true")  
            }  
        }  
        unitTest("Sub stops non-numbers on the left") {  
            assertThrows\[InterpException\] {  
                interp("nil \- 3")  
            }  
        }  
        unitTest("Sub stops two non-numbers") {  
            assertThrows\[InterpException\] {  
                interp("nil \- \[1, 2\]")  
            }  
        }

        unitTest("Mul works") {  
            val result \= interp("2 \* 3")  
            assertResult(NumV(6))(result)  
        }  
        unitTest("Mul stops non-numbers on the right") {  
            assertThrows\[InterpException\] {  
                interp("2 \* true")  
            }  
        }  
        unitTest("Mul stops non-numbers on the left") {  
            assertThrows\[InterpException\] {  
                interp("nil \* 3")  
            }  
        }  
        unitTest("Mul stops two non-numbers") {  
            assertThrows\[InterpException\] {  
                interp("nil \* \[1, 2\]")  
            }  
        }

        unitTest("EqNum works 1") {  
            val result \= interp("3 \== 3")  
            assertResult(BoolV(true))(result)  
        }  
        unitTest("EqNum works 2") {  
            val result \= interp("2 \== 3")  
            assertResult(BoolV(false))(result)  
        }  
        unitTest("EqNum works 3") {  
            val result \= interp("3 \== 2")  
            assertResult(BoolV(false))(result)  
        }  
        unitTest("EqNum stops non-numbers on the right") {  
            assertThrows\[InterpException\] {  
                interp("2 \== true")  
            }  
        }  
        unitTest("EqNum stops non-numbers on the left") {  
            assertThrows\[InterpException\] {  
                interp("nil \== 3")  
            }  
        }  
        unitTest("EqNum stops two non-numbers") {  
            assertThrows\[InterpException\] {  
                interp("nil \== \[1, 2\]")  
            }  
        }

        unitTest("LtNum works 1") {  
            val result \= interp("3 \< 4")  
            assertResult(BoolV(true))(result)  
        }  
        unitTest("LtNum works 2") {  
            val result \= interp("3 \< 3")  
            assertResult(BoolV(false))(result)  
        }  
        unitTest("LtNum works 3") {  
            val result \= interp("4 \< 3")  
            assertResult(BoolV(false))(result)  
        }  
        unitTest("LtNum stops non-numbers on the right") {  
            assertThrows\[InterpException\] {  
                interp("2 \< true")  
            }  
        }  
        unitTest("LtNum stops non-numbers on the left") {  
            assertThrows\[InterpException\] {  
                interp("nil \< 3")  
            }  
        }  
        unitTest("LtNum stops two non-numbers") {  
            assertThrows\[InterpException\] {  
                interp("nil \< \[1, 2\]")  
            }  
        }

        unitTest("GtNum works 1") {  
            val result \= interp("3 \> 4")  
            assertResult(BoolV(false))(result)  
        }  
        unitTest("GtNum works 2") {  
            val result \= interp("3 \> 3")  
            assertResult(BoolV(false))(result)  
        }  
        unitTest("GtNum works 3") {  
            val result \= interp("4 \> 3")  
            assertResult(BoolV(true))(result)  
        }  
        unitTest("GtNum stops non-numbers on the right") {  
            assertThrows\[InterpException\] {  
                interp("2 \> true")  
            }  
        }  
        unitTest("GtNum stops non-numbers on the left") {  
            assertThrows\[InterpException\] {  
                interp("nil \> 3")  
            }  
        }  
        unitTest("GtNum stops two non-numbers") {  
            assertThrows\[InterpException\] {  
                interp("nil \> \[1, 2\]")  
            }  
        }

        unitTest("Not works") {  
            val result \= interp("\!true")  
            assertResult(BoolV(false))(result)  
        }  
        unitTest("Not works 2") {  
            val result \= interp("\!false")  
            assertResult(BoolV(true))(result)  
        }  
        unitTest("Not stops non-bools") {  
            assertThrows\[InterpException\] {  
                interp("\!0")  
            }  
        }  
        unitTest("Not stops non-bools 2") {  
            assertThrows\[InterpException\] {  
                interp("\!nil")  
            }  
        }

        unitTest("And works 1") {  
            val result \= interp("true && true")  
            assertResult(BoolV(true))(result)  
        }  
        unitTest("And works 2") {  
            val result \= interp("true && false")  
            assertResult(BoolV(false))(result)  
        }  
        unitTest("And works 3") {  
            val result \= interp("false && true")  
            assertResult(BoolV(false))(result)  
        }  
        unitTest("And works 4") {  
            val result \= interp("false && false")  
            assertResult(BoolV(false))(result)  
        }  
        unitTest("And short circuit") {  
            val result \= interp("true && 3")  
            assertResult(NumV(3))(result)  
        }  
        unitTest("And short circuit 2") {  
            val result \= interp("true && \[1, 2\]")  
            assertResult(ConsV(NumV(1), ConsV(NumV(2), NilV())))(result)  
        }  
        unitTest("And lazy") {  
            val result \= interp("false && \[1, 2\]")  
            assertResult(BoolV(false))(result)  
        }  
        unitTest("And stops non-bools on the left") {  
            assertThrows\[InterpException\] {  
                interp("nil && true")  
            }  
        }  
        unitTest("And stops two non-bools") {  
            assertThrows\[InterpException\] {  
                interp("nil && \[1, 2\]")  
            }  
        }

        unitTest("Or works 1") {  
            val result \= interp("true || true")  
            assertResult(BoolV(true))(result)  
        }  
        unitTest("Or works 2") {  
            val result \= interp("true || false")  
            assertResult(BoolV(true))(result)  
        }  
        unitTest("Or works 3") {  
            val result \= interp("false || true")  
            assertResult(BoolV(true))(result)  
        }  
        unitTest("Or works 4") {  
            val result \= interp("false || false")  
            assertResult(BoolV(false))(result)  
        }  
        unitTest("Or short circuit") {  
            val result \= interp("false || 3")  
            assertResult(NumV(3))(result)  
        }  
        unitTest("Or short circuit 2") {  
            val result \= interp("false || \[1, 2\]")  
            assertResult(ConsV(NumV(1), ConsV(NumV(2), NilV())))(result)  
        }  
        unitTest("Or lazy") {  
            val result \= interp("true || \[1, 2\]")  
            assertResult(BoolV(true))(result)  
        }  
        unitTest("Or stops non-bools on the left") {  
            assertThrows\[InterpException\] {  
                interp("nil || true")  
            }  
        }  
        unitTest("Or stops two non-bools") {  
            assertThrows\[InterpException\] {  
                interp("nil || \[1, 2\]")  
            }  
        }

        unitTest("If works 1") {  
            val result \= interp("if true then 1 else 2")  
            assertResult(NumV(1))(result)  
        }  
        unitTest("If works 2") {  
            val result \= interp("if false then 1 else 2")  
            assertResult(NumV(2))(result)  
        }  
        unitTest("If executes only the right branch") {  
            val result \= interp("if true then 1 else (-true)")  
            assertResult(NumV(1))(result)  
        }  
        unitTest("If executes only the right branch 2") {  
            val result \= interp("if false then (-true) else 2")  
            assertResult(NumV(2))(result)  
        }  
        unitTest("If stops non-bool condition") {  
            assertThrows\[InterpException\] {  
                interp("if 1 then true else false")  
            }  
        }  
        unitTest("If stops non-bool condition 2") {  
            assertThrows\[InterpException\] {  
                interp("if nil then true else false")  
            }  
        }

        unitTest("Cond works 1") {  
            val result \= interp("cond true \-\> 1 | false \-\> true | false \-\> 2")  
            assertResult(NumV(1))(result)  
        }  
        unitTest("Cond works 2") {  
            val result \= interp("cond false \-\> 1 | true \-\> true | false \-\> 2")  
            assertResult(BoolV(true))(result)  
        }  
        unitTest("Cond works 3") {  
            val result \= interp("cond false \-\> 1 | false \-\> true | true \-\> 2")  
            assertResult(NumV(2))(result)  
        }  
        unitTest("Cond takes first true branch") {  
            val result \= interp("cond true \-\> 1 | true \-\> true | true \-\> 2")  
            assertResult(NumV(1))(result)  
        }  
        unitTest("Cond ends if true branch found") {  
            val result \= interp("cond false \-\> 1 | true \-\> true | \[1, 2\] \-\> 2")  
            assertResult(BoolV(true))(result)  
        }  
        unitTest("Cond stops non-bool condition") {  
            assertThrows\[InterpException\] {  
                interp("cond false \-\> 1 | 0 \-\> true | true \-\> 2")  
            }  
        }  
        unitTest("Cond stops non-true condition") {  
            assertThrows\[InterpException\] {  
                interp("cond false \-\> 1 | false \-\> true | false \-\> 2")  
            }  
        }

        unitTest("CondE works 1") {  
            val result \= interp("cond true \-\> 1 | false \-\> true | else \-\> 2")  
            assertResult(NumV(1))(result)  
        }  
        unitTest("CondE works 2") {  
            val result \= interp("cond false \-\> 1 | true \-\> true | else \-\> 2")  
            assertResult(BoolV(true))(result)  
        }  
        unitTest("CondE works 3") {  
            val result \= interp("cond false \-\> 1 | false \-\> true | else \-\> 2")  
            assertResult(NumV(2))(result)  
        }  
        unitTest("CondE takes first true branch") {  
            val result \= interp("cond true \-\> 1 | true \-\> true | else \-\> 2")  
            assertResult(NumV(1))(result)  
        }  
        unitTest("CondE ends if true branch found") {  
            val result \= interp("cond false \-\> 1 | true \-\> true | \[1, 2\] \-\> 2 | else \-\> 2")  
            assertResult(BoolV(true))(result)  
        }  
        unitTest("CondE stops non-bool condition") {  
            assertThrows\[InterpException\] {  
                interp("cond false \-\> 1 | 0 \-\> true | else \-\> 2")  
            }  
        }

        unitTest("Nil") {  
            val result \= interp("nil")  
            assertResult(NilV())(result)  
        }

        unitTest("Cons works 1") {  
            val result \= interp("1 :: 2")  
            assertResult(ConsV(NumV(1), NumV(2)))(result)  
        }  
        unitTest("Cons works 2") {  
            val result \= interp("1 :: 2 :: nil")  
            assertResult(ConsV(NumV(1), ConsV(NumV(2), NilV())))(result)  
        }

        unitTest("List works 1") {  
            val result \= interp("\[1\]")  
            assertResult(ConsV(NumV(1), NilV()))(result)  
        }  
        unitTest("List works 2") {  
            val result \= interp("\[1, 2\]")  
            assertResult(ConsV(NumV(1), ConsV(NumV(2), NilV())))(result)  
        }  
        unitTest("List works 3") {  
            val result \= interp("\[\]")  
            assertResult(NilV())(result)  
        }

        unitTest("Head works 1") {  
            val result \= interp("head \[1\]")  
            assertResult(NumV(1))(result)  
        }  
        unitTest("Head works 2") {  
            val result \= interp("head \[2, 1\]")  
            assertResult(NumV(2))(result)  
        }  
        unitTest("Head stops non-cons value") {  
            assertThrows\[InterpException\] {  
                interp("head 1")  
            }  
        }  
        unitTest("Head stops non-cons value 2") {  
            assertThrows\[InterpException\] {  
                interp("head nil")  
            }  
        }

        unitTest("Tail works 1") {  
            val result \= interp("tail \[1\]")  
            assertResult(NilV())(result)  
        }  
        unitTest("Tail works 2") {  
            val result \= interp("tail \[2, 1\]")  
            assertResult(ConsV(NumV(1), NilV()))(result)  
        }  
        unitTest("Tail stops non-cons value") {  
            assertThrows\[InterpException\] {  
                interp("tail 1")  
            }  
        }  
        unitTest("Tail stops non-cons value 2") {  
            assertThrows\[InterpException\] {  
                interp("tail nil")  
            }  
        }

        unitTest("IsNil works 1") {  
            val result \= interp("\[1\] is nil")  
            assertResult(BoolV(false))(result)  
        }  
        unitTest("IsNil works 2") {  
            val result \= interp("\[\] is nil")  
            assertResult(BoolV(true))(result)  
        }  
        unitTest("IsNil stops non-list value") {  
            assertThrows\[InterpException\] {  
                interp("1 is nil")  
            }  
        }  
        unitTest("IsNil stops non-list value 2") {  
            assertThrows\[InterpException\] {  
                interp("true is nil")  
            }  
        }

        unitTest("IsList works 1") {  
            val result \= interp("\[1\] is list")  
            assertResult(BoolV(true))(result)  
        }  
        unitTest("IsList works 2") {  
            val result \= interp("nil is list")  
            assertResult(BoolV(true))(result)  
        }  
        unitTest("IsList works 3") {  
            val result \= interp("1 is list")  
            assertResult(BoolV(false))(result)  
        }  
    }

    suite("Assignment 3") {

        unitTest("Id works") {  
            val result \= interp("let x \= 1 in x")  
            assertResult(NumV(1))(result)  
        }  
        unitTest("Id works 2") {  
            val result \= interp("(lambda x \-\> x)(\[1, 2\])")  
            assertResult(ConsV(NumV(1), ConsV(NumV(2), NilV())))(result)  
        }  
        unitTest("Id stops unbound variable") {  
            assertThrows\[InterpException\] {  
                interp("x")  
            }  
        }  
        unitTest("Id stops unbound variable 2") {  
            assertThrows\[InterpException\] {  
                interp("let y \= 1 in x")  
            }  
        }

        unitTest("Lambda returns closure") {  
            val result \= interp("lambda \-\> 1")  
            assert(result.isInstanceOf\[PointerClosureV\])  
        }  
        unitTest("Lambda returns closure 2") {  
            val result \= interp("lambda x, y \-\> 1")  
            assert(result.isInstanceOf\[PointerClosureV\])  
        }

        unitTest("App works") {  
            val result \= interp("(lambda \-\> 2)()")  
            assertResult(NumV(2))(result)  
        }  
        unitTest("App works 2") {  
            val result \= interp("(lambda x \-\> x)(1)")  
            assertResult(NumV(1))(result)  
        }  
        unitTest("App binds properly") {  
            val result \= interp("(lambda x, y \-\> x)(\[1, 2\], 3)")  
            assertResult(ConsV(NumV(1), ConsV(NumV(2), NilV())))(result)  
        }  
        unitTest("App binds properly 2") {  
            val result \= interp("(lambda x, y \-\> y)(\[1, 2\], 3)")  
            assertResult(NumV(3))(result)  
        }  
        unitTest("App nested binds properly") {  
            val result \= interp("(lambda x \-\> lambda y \-\> x)(\[1, 2\])(3)")  
            assertResult(ConsV(NumV(1), ConsV(NumV(2), NilV())))(result)  
        }  
        unitTest("App nested binds properly 2") {  
            val result \= interp("(lambda x \-\> lambda y \-\> y)(\[1, 2\])(3)")  
            assertResult(NumV(3))(result)  
        }  
        unitTest("App allows name shadowing") {  
            val result \= interp("(lambda x \-\> lambda x \-\> x)(2)(1)")  
            assertResult(NumV(1))(result)  
        }  
        unitTest("App stops more arguments than parameters") {  
            assertThrows\[InterpException\] {  
                interp("(lambda x \-\> x)(1, 2)")  
            }  
        }  
        unitTest("App stops more parameters than arguments") {  
            assertThrows\[InterpException\] {  
                interp("(lambda x, y \-\> x)(1)")  
            }  
        }

        unitTest("Let works") {  
            val result \= interp("let x \= 1 in 2")  
            assertResult(NumV(2))(result)  
        }  
        unitTest("Let works 2") {  
            val result \= interp("let x \= 1 in x")  
            assertResult(NumV(1))(result)  
        }  
        unitTest("Let binds properly") {  
            val result \= interp("let x \= 1, y \= 2 in x")  
            assertResult(NumV(1))(result)  
        }  
        unitTest("Let binds properly 2") {  
            val result \= interp("let x \= 1, y \= 2 in y")  
            assertResult(NumV(2))(result)  
        }  
        unitTest("Let nested binds properly") {  
            val result \= interp("let x \= 1 in let y \= 2 in x")  
            assertResult(NumV(1))(result)  
        }  
        unitTest("Let nested binds properly 2") {  
            val result \= interp("let x \= 1 in let y \= 2 in y")  
            assertResult(NumV(2))(result)  
        }  
        unitTest("Let allows name shadowing") {  
            val result \= interp("let x \= 2 in let x \= 1 in x")  
            assertResult(NumV(1))(result)  
        }  
    }

    suite("Assignment 4") {

        unitTest("Set works") {  
            val result \= interp("let x \= 2 in x \= 3")  
            assertResult(NumV(3))(result)  
        }  
        unitTest("Set works 2") {  
            val result \= interp("let x \= 2, y \= 1 in {x \= 3; y \= 4; x \+ y}")  
            assertResult(NumV(7))(result)  
        }

        unitTest("Sequence works") {  
            val result \= interp("{4}")  
            assertResult(NumV(4))(result)  
        }  
        unitTest("Sequence works 2") {  
            val result \= interp("let x \= 1 in {x; 4}")  
            assertResult(NumV(4))(result)  
        }  
        unitTest("Sequence works 3") {  
            val result \= interp("let x \= 1 in {x \= 2; x \= 3; x \+ 1}")  
            assertResult(NumV(4))(result)  
        }

        unitTest("LetRec works") {  
            val result \= interp("let rec x \= 4 in x")  
            assertResult(NumV(4))(result)  
        }  
        unitTest("LetRec works 2") {  
            val result \= interp("let rec x \= 1, y \= x in y")  
            assertResult(NumV(1))(result)  
        }  
        unitTest("LetRec works 3") {  
            val result \= interp("let rec sum \= lambda x \-\> if x \< 1 then 0 else x \+ sum(x \- 1\) in sum(5)")  
            assertResult(NumV(15))(result)  
        }  
        unitTest("LetRec works 4") {  
            val result \= interp(  
                """  
                let rec sum \= lambda x \-\> if x \< 1 then 0 else x \+ sum2(x \- 1),  
                        sum2 \= lambda x \-\> if x \< 1 then 0 else x \+ sum(x \- 1\)  
                        in  
                            sum(5)  
                """)  
            assertResult(NumV(15))(result)  
        }  
        unitTest("LetRec stops reverse variable passing") {  
            val result \= interp("let rec x \= y, y \= 1 in x")  
            assertResult(UninitialisedV())(result)  
        }

        unitTest("Box works") {  
            val result \= interp("box 4")  
            assert(result.isInstanceOf\[BoxV\])  
        }  
        unitTest("Box works 2") {  
            val result \= interp("let b \= box 0 in b")  
            assert(result.isInstanceOf\[BoxV\])  
        }

        unitTest("Unbox works") {  
            val result \= interp("unbox (box 4)")  
            assertResult(NumV(4))(result)  
        }  
        unitTest("Unbox works 2") {  
            val result \= interp("unbox (box true)")  
            assertResult(BoolV(true))(result)  
        }  
        unitTest("Unbox works 3") {  
            val result \= interp("let b \= box 0 in unbox b")  
            assertResult(NumV(0))(result)  
        }  
        unitTest("Unbox stops non-box value") {  
            assertThrows\[InterpException\] {  
                interp("unbox 1")  
            }  
        }

        unitTest("SetBox works") {  
            val result \= interp("let b \= box 0 in set box b to 4")  
            assertResult(NumV(4))(result)  
        }  
        unitTest("SetBox works 2") {  
            val result \= interp("set box (box true) to false")  
            assertResult(BoolV(false))(result)  
        }  
        unitTest("SetBox stops non-box value before to") {  
            assertThrows\[InterpException\] {  
                interp("set box 1 to 2")  
            }  
        }

        unitTest("Neg store chaining works") {  
            val result \= interp("let x \= 1 in {-(x \= 2); x}")  
            assertResult(NumV(2))(result)  
        }

        unitTest("Add store chaining works") {  
            val result \= interp("let x \= 1 in (x \= 2\) \+ x")  
            assertResult(NumV(4))(result)  
        }  
        unitTest("Add store chaining works 2") {  
            val result \= interp("let x \= 1 in {1 \+ (x \= 3); x}")  
            assertResult(NumV(3))(result)  
        }  
        unitTest("Add store chaining works 3") {  
            val result \= interp("let x \= 1 in {(x \= 2\) \+ (x \= 3); x}")  
            assertResult(NumV(3))(result)  
        }

        unitTest("Sub store chaining works") {  
            val result \= interp("let x \= 1 in (x \= 2\) \- x")  
            assertResult(NumV(0))(result)  
        }  
        unitTest("Sub store chaining works 2") {  
            val result \= interp("let x \= 1 in {1 \- (x \= 3); x}")  
            assertResult(NumV(3))(result)  
        }  
        unitTest("Sub store chaining works 3") {  
            val result \= interp("let x \= 1 in {(x \= 2\) \- (x \= 3); x}")  
            assertResult(NumV(3))(result)  
        }

        unitTest("Mul store chaining works") {  
            val result \= interp("let x \= 1 in (x \= 2\) \* x")  
            assertResult(NumV(4))(result)  
        }  
        unitTest("Mul store chaining works 2") {  
            val result \= interp("let x \= 1 in {1 \* (x \= 3); x}")  
            assertResult(NumV(3))(result)  
        }  
        unitTest("Mul store chaining works 3") {  
            val result \= interp("let x \= 1 in {(x \= 2\) \* (x \= 3); x}")  
            assertResult(NumV(3))(result)  
        }

        unitTest("EqNum store chaining works") {  
            val result \= interp("let x \= 1 in (x \= 2\) \== x")  
            assertResult(BoolV(true))(result)  
        }  
        unitTest("EqNum store chaining works 2") {  
            val result \= interp("let x \= 1 in {1 \== (x \= 3); x}")  
            assertResult(NumV(3))(result)  
        }  
        unitTest("EqNum store chaining works 3") {  
            val result \= interp("let x \= 1 in {(x \= 2\) \== (x \= 3); x}")  
            assertResult(NumV(3))(result)  
        }

        unitTest("LtNum store chaining works") {  
            val result \= interp("let x \= 1 in (x \= 2\) \< x")  
            assertResult(BoolV(false))(result)  
        }  
        unitTest("LtNum store chaining works 2") {  
            val result \= interp("let x \= 1 in {1 \< (x \= 3); x}")  
            assertResult(NumV(3))(result)  
        }  
        unitTest("LtNum store chaining works 3") {  
            val result \= interp("let x \= 1 in {(x \= 2\) \< (x \= 3); x}")  
            assertResult(NumV(3))(result)  
        }

        unitTest("GtNum store chaining works") {  
            val result \= interp("let x \= 1 in (x \= 2\) \> x")  
            assertResult(BoolV(false))(result)  
        }  
        unitTest("GtNum store chaining works 2") {  
            val result \= interp("let x \= 1 in {1 \> (x \= 3); x}")  
            assertResult(NumV(3))(result)  
        }  
        unitTest("GtNum store chaining works 3") {  
            val result \= interp("let x \= 1 in {(x \= 2\) \> (x \= 3); x}")  
            assertResult(NumV(3))(result)  
        }

        unitTest("Not store chaining works") {  
            val result \= interp("let x \= false in {\!(x \= true); x}")  
            assertResult(BoolV(true))(result)  
        }

        unitTest("And store chaining works") {  
            val result \= interp("let x \= false in (x \= true) && x")  
            assertResult(BoolV(true))(result)  
        }  
        unitTest("And store chaining works 2") {  
            val result \= interp("let x \= false in {true && (x \= true); x}")  
            assertResult(BoolV(true))(result)  
        }  
        unitTest("And store chaining works 3") {  
            val result \= interp("let x \= false in {(x \= true) && (x \= false); x}")  
            assertResult(BoolV(false))(result)  
        }

        unitTest("Or store chaining works") {  
            val result \= interp("let x \= true in (x \= false) || x")  
            assertResult(BoolV(false))(result)  
        }  
        unitTest("Or store chaining works 2") {  
            val result \= interp("let x \= false in {false || (x \= true); x}")  
            assertResult(BoolV(true))(result)  
        }  
        unitTest("Or store chaining works 3") {  
            val result \= interp("let x \= true in {(x \= false) || (x \= true); x}")  
            assertResult(BoolV(true))(result)  
        }

        unitTest("If store chaining works") {  
            val result \= interp("let x \= 1 in if {x \= 2; true} then x else x \= 3")  
            assertResult(NumV(2))(result)  
        }  
        unitTest("If store chaining works 2") {  
            val result \= interp("let x \= 1 in if {x \= 2; false} then x else x")  
            assertResult(NumV(2))(result)  
        }  
        unitTest("If store chaining works 3") {  
            val result \= interp("let x \= 1 in {if {x \= 2; false} then x \= 4 else x \= 3; x}")  
            assertResult(NumV(3))(result)  
        }  
        unitTest("If store chaining works 4") {  
            val result \= interp("let x \= 1 in {if {x \= 2; true} then x \= 4 else x \= 3; x}")  
            assertResult(NumV(4))(result)  
        }

        unitTest("Cond store chaining works") {  
            val result \= interp("let x \= 1 in cond {x \= 2; true} \-\> x | false \-\> x \= 3")  
            assertResult(NumV(2))(result)  
        }  
        unitTest("Cond store chaining works 2") {  
            val result \= interp("let x \= 1 in cond {x \= 2; false} \-\> x | true \-\> x")  
            assertResult(NumV(2))(result)  
        }  
        unitTest("Cond store chaining works 3") {  
            val result \= interp("let x \= 1 in {cond {x \= 2; false} \-\> x \= 4 | true \-\> x \= 3; x}")  
            assertResult(NumV(3))(result)  
        }  
        unitTest("Cond store chaining works 4") {  
            val result \= interp("let x \= 1 in {cond {x \= 2; true} \-\> x \= 4 | false \-\> x \= 3; x}")  
            assertResult(NumV(4))(result)  
        }  
        unitTest("Cond store chaining works 5") {  
            val result \= interp("let x \= 1 in {cond {x \= 2; false} \-\> x \= 4 | false \-\> x \= 3 | x \== 2 \-\> x \= 5; x}")  
            assertResult(NumV(5))(result)  
        }

        unitTest("CondE store chaining works") {  
            val result \= interp("let x \= 1 in cond {x \= 2; true} \-\> x | else \-\> x \= 3")  
            assertResult(NumV(2))(result)  
        }  
        unitTest("CondE store chaining works 2") {  
            val result \= interp("let x \= 1 in cond {x \= 2; false} \-\> x | else \-\> x")  
            assertResult(NumV(2))(result)  
        }  
        unitTest("CondE store chaining works 3") {  
            val result \= interp("let x \= 1 in {cond {x \= 2; false} \-\> x \= 4 | else \-\> x \= 3; x}")  
            assertResult(NumV(3))(result)  
        }  
        unitTest("CondE store chaining works 4") {  
            val result \= interp("let x \= 1 in {cond {x \= 2; true} \-\> x \= 4 | else \-\> x \= 3; x}")  
            assertResult(NumV(4))(result)  
        }  
        unitTest("CondE store chaining works 5") {  
            val result \= interp("let x \= 1 in {cond {x \= 2; false} \-\> x \= 4 | false \-\> x \= 3 | else \-\> if x \== 2 then x \= 5 else 3; x}")  
            assertResult(NumV(5))(result)  
        }      

        unitTest("Cons store chaining works") {  
            val result \= interp("let x \= 1 in {x \= 2; 1} :: x")  
            assertResult(ConsV(NumV(1), NumV((2))))(result)  
        }  
        unitTest("Cons store chaining works 2") {  
            val result \= interp("let x \= 1 in {(x \= 2\) :: 3; x}")  
            assertResult(NumV(2))(result)  
        }  
        unitTest("Cons store chaining works 3") {  
            val result \= interp("let x \= 1 in {(x \= 2\) :: (x \= 3); x}")  
            assertResult(NumV(3))(result)  
        }

        unitTest("List store chaining works") {  
            val result \= interp("let x \= 1 in \[{x \= 2; 1}, x\]")  
            assertResult(ConsV(NumV(1), ConsV(NumV((2)), NilV())))(result)  
        }  
        unitTest("List store chaining works 2") {  
            val result \= interp("let x \= 1 in {\[(x \= 2), 3\]; x}")  
            assertResult(NumV(2))(result)  
        }  
        unitTest("List store chaining works 3") {  
            val result \= interp("let x \= 1 in {\[(x \= 2), (x \= 3)\]; x}")  
            assertResult(NumV(3))(result)  
        }

        unitTest("Head store chaining works") {  
            val result \= interp("let x \= 1 in {head {x \= 2; \[1\]}; x}")  
            assertResult(NumV(2))(result)  
        }

        unitTest("Tail store chaining works") {  
            val result \= interp("let x \= 1 in {tail {x \= 2; \[1\]}; x}")  
            assertResult(NumV(2))(result)  
        }

        unitTest("IsNil store chaining works") {  
            val result \= interp("let x \= 1 in {{x \= 2; \[1\]} is nil; x}")  
            assertResult(NumV(2))(result)  
        }

        unitTest("IsList store chaining works") {  
            val result \= interp("let x \= 1 in {{x \= 2; \[1\]} is list; x}")  
            assertResult(NumV(2))(result)  
        }

        unitTest("App store chaining works") {  
            val result \= interp("let x \= 0 in (lambda y \-\> x)(x \= 2)")  
            assertResult(NumV(2))(result)  
        }  
        unitTest("App store chaining works 2") {  
            val result \= interp("let x \= 0 in {(lambda y \-\> x \= 3)(x \= 2); x}")  
            assertResult(NumV(3))(result)  
        }  
        unitTest("App store chaining works 3") {  
            val result \= interp("let x \= 0 in ({x \= 1; lambda y \-\> x})(if x \== 1 then x \= 2 else 3)")  
            assertResult(NumV(2))(result)  
        }  
        unitTest("App store chaining works 4") {  
            val result \= interp("let x \= 0 in ({x \= 1; lambda y, z \-\> x})(if x \== 1 then x \= 2 else 3, if x \== 2 then x \= 4 else 5)")  
            assertResult(NumV(4))(result)  
        }  
        unitTest("App store chaining works 5") {  
            val result \= interp("let x \= 0 in {({x \= 1; lambda y, z \-\> x \= 6})(if x \== 1 then x \= 2 else 3, if x \== 2 then x \= 4 else 5); x}")  
            assertResult(NumV(6))(result)  
        }

        unitTest("Let store chaining works") {  
            val result \= interp("let x \= 0 in let y \= {x \= 1; 2} in x")  
            assertResult(NumV(1))(result)  
        }  
        unitTest("Let store chaining works 2") {  
            val result \= interp("let x \= 0 in {let y \= 1 in x \= 2; x}")  
            assertResult(NumV(2))(result)  
        }  
        unitTest("Let store chaining works 3") {  
            val result \= interp("let x \= 0 in {let y \= (x \= 1\) in x \= 2; x}")  
            assertResult(NumV(2))(result)  
        }

        unitTest("LetRec store chaining works") {  
            val result \= interp("let x \= 0 in let rec y \= {x \= 1; 2} in x")  
            assertResult(NumV(1))(result)  
        }  
        unitTest("LetRec store chaining works 2") {  
            val result \= interp("let x \= 0 in {let rec y \= 1 in x \= 2; x}")  
            assertResult(NumV(2))(result)  
        }  
        unitTest("LetRec store chaining works 3") {  
            val result \= interp("let x \= 0 in {let rec y \= (x \= 1\) in x \= 2; x}")  
            assertResult(NumV(2))(result)  
        }

        unitTest("Box store chaining works") {  
            val result \= interp("let x \= 1 in {box {x \= 2; \[1\]}; x}")  
            assertResult(NumV(2))(result)  
        }

        unitTest("Unbox store chaining works") {  
            val result \= interp("let x \= 1 in {unbox (box {x \= 2; \[1\]}); x}")  
            assertResult(NumV(2))(result)  
        }

        unitTest("SetBox store chaining works") {  
            val result \= interp("let x \= box 0, y \= 0 in {set box {y \= 2; x} to y; y}")  
            assertResult(NumV(2))(result)  
        }  
        unitTest("SetBox store chaining works 2") {  
            val result \= interp("let x \= box 0, y \= 0 in {set box {y \= 2; x} to y; unbox x}")  
            assertResult(NumV(2))(result)  
        }  
        unitTest("SetBox store chaining works 3") {  
            val result \= interp("let x \= box 0, y \= 0 in {set box {y \= 2; x} to y \= 3; y}")  
            assertResult(NumV(3))(result)  
        }  
    }

    suite("Compound") {

        unitTest("complex") {  
            val result \= interp(  
                """  
                  | (1 \+ 2\) \*  
                  | (3 \+ 5\) \-  
                  | 1  
                  |""".stripMargin)  
            assertResult(NumV(3\*8\-1))(result)  
        }

        unitTest("static scope") {  
            val result \= interp(  
                """  
                  | let x \= 1 in (lambda x \-\>  
                  |   x)(2)  
                  |""".stripMargin  
            )  
            assertResult(NumV(2))(result)  
        }

        unitTest("let has no access to current binds") {  
            val result \= interp(  
                """  
                  let x \= 3 in let x \= 1, y \= x in x \+ y  
                  """.stripMargin  
            )  
            assertResult(NumV(4))(result)  
        }

        unitTest("no args lambda") {  
            val result \= interp(  
                """  
                  let x \= lambda \-\> 1 in x()  
                  """.stripMargin  
            )  
            assertResult(NumV(1))(result)  
        }

        unitTest("store chaining with app") {  
            val result \= interp(  
                """  
                  let x \= 0 in  
                    let s \= {x \= 1;lambda z \-\> lambda y \-\> x}  
                    in s(if x \== 1 then x \= 2 else x \= 5)(if x \== 2 then x \= 3 else 4\)  
                  """.stripMargin  
            )  
            assertResult(NumV(3))(result)  
        }

        unitTest("no args lambda desugared") {  
            val result \= interp(  
                """  
                  (lambda x \-\> x())(lambda \-\> 1\)  
                  """.stripMargin  
            )  
            assertResult(NumV(1))(result)  
        }  
         
        unitTest("let stack") {  
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
        unitTest("merge recursive") {  
            val result \= interp(  
                """  
                let Z \= lambda f \-\>  
                    (  
                        (lambda y \-\> y(y))  
                        (lambda z \-\>  
                            f(lambda x, y \-\> z(z)(x, y))  
                        )  
                    )  
                in  
                    let merge \= Z(lambda g \-\> lambda x, y \-\>  
                            if (x is nil) then y else  
                            (  
                                if (y is nil) then x else  
                                (  
                                    if(head x \< head y) then  
                                        (head x) :: g((tail x), y)  
                                    else  
                                        (head y) :: g(x, (tail y))  
                                )  
                            ))  
                    in  
                        merge(\[1, 2, 5\], \[3, 4\])  
                  """.stripMargin  
            )  
            assertResult(ConsV(NumV(1),ConsV(NumV(2),ConsV(NumV(3),ConsV(NumV(4),ConsV(NumV(5),NilV()))))))(result)  
        }

        unitTest("mergesort recursive") {  
            val result \= interp(  
                """  
let Z2 \= lambda f \-\>  
    (  
        (lambda y \-\> y(y))  
        (lambda z \-\>  
            f(lambda x, y \-\> z(z)(x, y))  
        )  
    ),  
    Z1 \= lambda f \-\>  
    (  
        (lambda y \-\> y(y))  
        (lambda z \-\>  
            f(lambda x \-\> z(z)(x))  
        )  
    )  
in  
    let merge \= Z2(lambda g \-\> lambda x, y \-\>  
            if (x is nil) then y else  
            (  
                if (y is nil) then x else  
                (  
                    if(head x \< head y) then  
                        (head x) :: g((tail x), y)  
                    else  
                        (head y) :: g(x, (tail y))  
                )  
            )),  
        len \= Z1(lambda g \-\> lambda x \-\>  
            if(x is nil) then 0 else  
                (1 \+ g(tail x))  
            ),  
        divBy2 \= Z1(lambda g \-\> lambda x \-\> lambda target \-\>  
                if(x \* 2 \== target) then  
                    x  
                else if (x \* 2 \> target) then  
                    x \- 1  
                else  
                    g(x \+ 1)(target)  
            )(0),  
        digFirst \= Z2(lambda g \-\> lambda x, n \-\>  
                if(n \== 1\) then (\[head x\]) else (head x) :: g(tail x, n \- 1\)  
            ),  
        digLast \= Z2(lambda g \-\> lambda x, n \-\>  
                if(n \== 1\) then (tail x) else g(tail x, n \- 1\)  
            )  
    in  
        let mergeSort \= Z1(lambda g \-\> lambda x \-\>  
                let length \= len(x) in  
                if (length \< 2\) then x else  
                let halfLen \= divBy2(length) in  
                let left \= digFirst(x, halfLen), right \= digLast(x, halfLen) in  
                let newLeft \= g(left), newRight \= g(right) in  
                merge(newLeft, newRight)  
            )  
        in  
            mergeSort(\[83, 12, 47, 95, 6, 71, 29, 54, 18, 90, 3, 64, 41, 77, 22,  
            58, 99, 14, 36, 85, 1, 73, 49, 27, 60, 9, 92, 33, 68, 20, 56, 11, 80,  
            44, 97, 5, 39, 74, 24, 63, 88, 16, 52, 30, 70, 8, 94, 45, 2, 79, 34,  
            61, 21, 87, 13, 50, 98, 25, 66, 4, 82, 37, 59, 19, 75, 28, 91, 46, 10,  
            72, 35, 84, 7, 62, 26, 93, 40, 57, 15, 76, 31, 89, 23, 65, 17, 100, 43,  
            69, 32, 81, 48, 96, 38, 53, 78, 55, 42, 67, 86, 51\])  
                  """.stripMargin)  
             
            assertResult(ConsV(NumV(1),ConsV(NumV(2),ConsV(NumV(3),ConsV(NumV(4),ConsV(NumV(5),ConsV(NumV(6),ConsV(NumV(7),ConsV(NumV(8),ConsV(NumV(9),ConsV(NumV(10),ConsV(NumV(11),ConsV(NumV(12),ConsV(NumV(13),ConsV(NumV(14),ConsV(NumV(15),ConsV(NumV(16),ConsV(NumV(17),ConsV(NumV(18),ConsV(NumV(19),ConsV(NumV(20),ConsV(NumV(21),ConsV(NumV(22),ConsV(NumV(23),ConsV(NumV(24),ConsV(NumV(25),ConsV(NumV(26),ConsV(NumV(27),ConsV(NumV(28),ConsV(NumV(29),ConsV(NumV(30),ConsV(NumV(31),ConsV(NumV(32),ConsV(NumV(33),ConsV(NumV(34),ConsV(NumV(35),ConsV(NumV(36),ConsV(NumV(37),ConsV(NumV(38),ConsV(NumV(39),ConsV(NumV(40),ConsV(NumV(41),ConsV(NumV(42),ConsV(NumV(43),ConsV(NumV(44),ConsV(NumV(45),ConsV(NumV(46),ConsV(NumV(47),ConsV(NumV(48),ConsV(NumV(49),ConsV(NumV(50),ConsV(NumV(51),ConsV(NumV(52),ConsV(NumV(53),ConsV(NumV(54),ConsV(NumV(55),ConsV(NumV(56),ConsV(NumV(57),ConsV(NumV(58),ConsV(NumV(59),ConsV(NumV(60),ConsV(NumV(61),ConsV(NumV(62),ConsV(NumV(63),ConsV(NumV(64),ConsV(NumV(65),ConsV(NumV(66),ConsV(NumV(67),ConsV(NumV(68),ConsV(NumV(69),ConsV(NumV(70),ConsV(NumV(71),ConsV(NumV(72),ConsV(NumV(73),ConsV(NumV(74),ConsV(NumV(75),ConsV(NumV(76),ConsV(NumV(77),ConsV(NumV(78),ConsV(NumV(79),ConsV(NumV(80),ConsV(NumV(81),ConsV(NumV(82),ConsV(NumV(83),ConsV(NumV(84),ConsV(NumV(85),ConsV(NumV(86),ConsV(NumV(87),ConsV(NumV(88),ConsV(NumV(89),ConsV(NumV(90),ConsV(NumV(91),ConsV(NumV(92),ConsV(NumV(93),ConsV(NumV(94),ConsV(NumV(95),ConsV(NumV(96),ConsV(NumV(97),ConsV(NumV(98),ConsV(NumV(99),ConsV(NumV(100),NilV())))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))(result)  
        }

        unitTest("sum recursive") {  
            val result \= interp(  
                """  
    let sum \=  
        lambda g \-\> lambda x \-\>  
            if (x is nil) then 0 else  
            (head x) \+ g(g)(tail x)  
    in  
    sum(sum)(\[1, 2, 5\])  
                  """.stripMargin  
            )  
            assertResult(NumV(8))(result)  
        }

        unitTest("recursive data") {  
            val result \= interp(  
                """  
    let b \= box 0 in  
        {set box b to b; unbox (unbox (unbox (unbox b)))}  
         
                  """.stripMargin  
            )  
            assert(result.isInstanceOf\[BoxV\])  
        }  
    }

    def desugar(expr: String): ExprC \= {  
        val parsed \= CPLParser.parseUntyped(expr)  
        Desugar.desugar(parsed)  
    }

    def interp(expr: String): Value \= {  
        val desugared \= desugar(expr)  
        Interpret.interp(desugared, Nil, Nil).\_1  
    }

}  
