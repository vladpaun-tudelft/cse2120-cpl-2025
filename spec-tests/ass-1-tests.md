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

        unitTest("complex") {  
            val result \= interp(  
                """  
                  | (1 \+ 2\) \*  
                  | (3 \+ 5\) \-  
                  | 1  
                  |""".stripMargin)  
            assertResult(NumV(3\*8\-1))(result)  
        }  
    }

    def desugar(expr: String): ExprC \= {  
        val parsed \= CPLParser.parseUntyped(expr)  
        Desugar.desugar(parsed)  
    }

    def interp(expr: String): Value \= {  
        val desugared \= desugar(expr)  
        Interpret.interp(desugared)  
    }

}

