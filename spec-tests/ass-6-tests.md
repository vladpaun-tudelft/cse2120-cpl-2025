import nl.tudelft.cpl.language.Library.\*  
import nl.tudelft.cpl.test.\*

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
            val result \= interp("true && \[\]")  
            assertResult(NilV())(result)  
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
            val result \= interp("false || \[\]")  
            assertResult(NilV())(result)  
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
            assertResult(ConsV(ThunkV(Left((NumC(1), List()))), ThunkV(Left((NumC(2), List())))))(result)  
        }  
        unitTest("Cons works 2") {  
            val result \= interp("1 :: 2 :: nil")  
            assertResult(ConsV(ThunkV(Left((NumC(1), List()))), ThunkV(Left((ConsC(NumC(2), NilC()), List())))))(result)  
        }

        unitTest("List works 1") {  
            val result \= interp("\[1\]")  
            assertResult(ConsV(ThunkV(Left((NumC(1), List()))), ThunkV(Left((NilC(), List())))))(result)  
        }  
        unitTest("List works 2") {  
            val result \= interp("\[1, 2\]")  
            assertResult(ConsV(ThunkV(Left((NumC(1), List()))), ThunkV(Left((ConsC(NumC(2), NilC()), List())))))(result)  
        }  
        unitTest("List works 3") {  
            val result \= interp("\[\]")  
            assertResult(NilV())(result)  
        }

        unitTest("Head works 1") {  
            val result \= interp("head \[1\]")  
            assertResult(ThunkV(Left((NumC(1), List()))))(result)  
        }  
        unitTest("Head works 2") {  
            val result \= interp("head \[2, 1\]")  
            assertResult(ThunkV(Left((NumC(2), List()))))(result)  
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
            assertResult(ThunkV(Left((NilC(), List()))))(result)  
        }  
        unitTest("Tail works 2") {  
            val result \= interp("tail \[2, 1\]")  
            assertResult(ThunkV(Left((ConsC(NumC(1), NilC()), List()))))(result)  
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
            assertResult(ThunkV(Left((NumC(1), List()))))(result)  
        }  
        unitTest("Id works 2") {  
            val result \= interp("(lambda x \-\> x)(\[1, 2\])")  
            assertResult(ThunkV(Left((ConsC(NumC(1), ConsC(NumC(2), NilC())), List()))))(result)  
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
            assert(result.isInstanceOf\[MutClosureV\])  
        }  
        unitTest("Lambda returns closure 2") {  
            val result \= interp("lambda x, y \-\> 1")  
            assert(result.isInstanceOf\[MutClosureV\])  
        }

        unitTest("App works") {  
            val result \= interp("(lambda \-\> 2)()")  
            assertResult(NumV(2))(result)  
        }  
        unitTest("App works 2") {  
            val result \= interp("(lambda x \-\> x)(1)")  
            assertResult(ThunkV(Left((NumC(1), List()))))(result)  
        }  
        unitTest("App binds properly") {  
            val result \= interp("(lambda x, y \-\> x)(\[1, 2\], 3)")  
            assertResult(ThunkV(Left((ConsC(NumC(1), ConsC(NumC(2), NilC())), List()))))(result)  
        }  
        unitTest("App binds properly 2") {  
            val result \= interp("(lambda x, y \-\> y)(\[1, 2\], 3)")  
            assertResult(ThunkV(Left((NumC(3), List()))))(result)  
        }  
        unitTest("App nested binds properly") {  
            val result \= interp("(lambda x \-\> lambda y \-\> x)(\[1, 2\])(3)")  
            assertResult(ThunkV(Left((ConsC(NumC(1), ConsC(NumC(2), NilC())), List()))))(result)  
        }  
        unitTest("App nested binds properly 2") {  
            val result \= interp("(lambda x \-\> lambda y \-\> y)(\[1, 2\])(3)")  
            assertResult(ThunkV(Left((NumC(3), List()))))(result)  
        }  
        unitTest("App allows name shadowing") {  
            val result \= interp("(lambda x \-\> lambda x \-\> x)(2)(1)")  
            assertResult(ThunkV(Left((NumC(1), List()))))(result)  
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
            assertResult(ThunkV(Left((NumC(1), List()))))(result)  
        }  
        unitTest("Let binds properly") {  
            val result \= interp("let x \= 1, y \= 2 in x")  
            assertResult(ThunkV(Left((NumC(1), List()))))(result)  
        }  
        unitTest("Let binds properly 2") {  
            val result \= interp("let x \= 1, y \= 2 in y")  
            assertResult(ThunkV(Left((NumC(2), List()))))(result)  
        }  
        unitTest("Let nested binds properly") {  
            val result \= interp("let x \= 1 in let y \= 2 in x")  
            assertResult(ThunkV(Left((NumC(1), List()))))(result)  
        }  
        unitTest("Let nested binds properly 2") {  
            val result \= interp("let x \= 1 in let y \= 2 in y")  
            assertResult(ThunkV(Left((NumC(2), List(MutBind("x", ThunkV(Left((NumC(1), List())))))))))(result)  
        }  
        unitTest("Let allows name shadowing") {  
            val result \= interp("let x \= 2 in let x \= 1 in x")  
            assertResult(ThunkV(Left((NumC(1), List(MutBind("x", ThunkV(Left((NumC(2), List())))))))))(result)  
        }  
    }

    suite("Assignment 6"){  
         
        unitTest("Force works") {  
            val result \= interp("(1 :: 2)\!")  
            assertResult(ConsV(NumV(1), NumV(2)))(result)  
        }  
        unitTest("Force works 2") {  
            val result \= interp("(1 :: 2 :: 3 :: 4 :: 5 :: nil)\!")  
            assertResult(ConsV(NumV(1),ConsV(NumV(2),ConsV(NumV(3),ConsV(NumV(4),ConsV(NumV(5),NilV()))))))(result)  
        }  
        unitTest("Force works 3") {  
            val result \= interp("((1 :: 2\) :: nil)\!")  
            assertResult(ConsV(ConsV(NumV(1), NumV(2)), NilV()))(result)  
        }  
        unitTest("Force works 4") {  
            val result \= interp("let x \= 1 in x\!")  
            assertResult(NumV(1))(result)  
        }  
        unitTest("Force works 5") {  
            val result \= interp("let x \= ((1 :: 2\) :: nil) in x\!")  
            assertResult(ConsV(ConsV(NumV(1), NumV(2)), NilV()))(result)  
        }

        unitTest("LetRec works") {  
            val result \= interp("let rec x \= 4 in x\!")  
            assertResult(NumV(4))(result)  
        }  
        unitTest("LetRec works 2") {  
            val result \= interp("let rec x \= 1, y \= x in y\!")  
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
        unitTest("LetRec allows reverse variable passing") {  
            val result \= interp("let rec x \= y, y \= 1 in x\!")  
            assertResult(NumV(1))(result)  
        }

        unitTest("Print works") {  
            val (result, printed) \= interpPrint("print(10)".stripMargin)  
            assertResult(Some(NumV(10)))(result)  
            assertResult("10")(printed)  
        }  
        unitTest("Print works 2") {  
            val (result, printed) \= interpPrint("print(true)".stripMargin)  
            assertResult(Some(BoolV(true)))(result)  
            assertResult("true")(printed)  
        }  
        unitTest("Print works 3") {  
            val (result, printed) \= interpPrint("print(nil)".stripMargin)  
            assertResult(Some(NilV()))(result)  
            assertResult("\[\]")(printed)  
        }  
        unitTest("Print works 4") {  
            val (result, printed) \= interpPrint("print(1 :: 2)".stripMargin)  
            assertResult(Some(ConsV(ThunkV(Right(NumV(1))), ThunkV(Right(NumV(2))))))(result)  
            assertResult("\[1 2\]")(printed)  
        }  
        unitTest("Print works 5") {  
            val (result, printed) \= interpPrint("print(lambda x \-\> 1)".stripMargin)  
            assertResult(Some(MutClosureV(LambdaC(List("x"), NumC(1)), List())))(result)  
            assertResult("\<closure\>")(printed)  
        }  
        // unitTest("Print works 6") {  
        //     val (result, printed) \= interpPrint("let rec x \= x in print(x)".stripMargin)  
        //     assertResult(Some(UninitialisedV()))(result)  
        //     assertResult("\<uninitialized\>")(printed)  
        // }

        unitTest("Neg strict"){  
            val result \= interp("-(head (1 :: 2))")  
            assertResult(NumV(-1))(result)  
        }  
         
        unitTest("Add strict left"){  
            val result \= interp("(head (1 :: 2)) \+ 3")  
            assertResult(NumV(4))(result)  
        }  
        unitTest("Add strict right"){  
            val result \= interp("3 \+ (head (1 :: 2))")  
            assertResult(NumV(4))(result)  
        }  
        unitTest("Add strict both"){  
            val result \= interp("(head \[3\]) \+ (head (1 :: 2))")  
            assertResult(NumV(4))(result)  
        }

        unitTest("Sub strict left"){  
            val result \= interp("(head (1 :: 2)) \- 3")  
            assertResult(NumV(-2))(result)  
        }  
        unitTest("Sub strict right"){  
            val result \= interp("3 \- (head (1 :: 2))")  
            assertResult(NumV(2))(result)  
        }  
        unitTest("Sub strict both"){  
            val result \= interp("(head \[3\]) \- (head (1 :: 2))")  
            assertResult(NumV(2))(result)  
        }

        unitTest("Mul strict left"){  
            val result \= interp("(head (1 :: 2)) \* 3")  
            assertResult(NumV(3))(result)  
        }  
        unitTest("Mul strict right"){  
            val result \= interp("3 \* (head (1 :: 2))")  
            assertResult(NumV(3))(result)  
        }  
        unitTest("Mul strict both"){  
            val result \= interp("(head \[3\]) \* (head (1 :: 2))")  
            assertResult(NumV(3))(result)  
        }

        unitTest("Not strict"){  
            val result \= interp("\!(head (true :: false))")  
            assertResult(BoolV(false))(result)  
        }  
         
        unitTest("And strict left"){  
            val result \= interp("(head (true :: false)) && false")  
            assertResult(BoolV(false))(result)  
        }  
        unitTest("And not strict right"){  
            val result \= interp("true && (head (true :: false))")  
            assertResult(ThunkV(Left(TrueC(), List())))(result)  
        }

        unitTest("Or strict left"){  
            val result \= interp("(head (false :: false)) || true")  
            assertResult(BoolV(true))(result)  
        }  
        unitTest("Or not strict right"){  
            val result \= interp("false || (head (true :: false))")  
            assertResult(ThunkV(Left(TrueC(), List())))(result)  
        }

        unitTest("If strict"){  
            val result \= interp("if (head \[false\]) then 1 else 2")  
            assertResult(NumV(2))(result)  
        }  
        unitTest("If not strict"){  
            val result \= interp("if true then (head \[1\]) else 2")  
            assertResult(ThunkV(Left(NumC(1), List())))(result)  
        }  
        unitTest("If not strict 2"){  
            val result \= interp("if false then 1 else (head \[2\])")  
            assertResult(ThunkV(Left(NumC(2), List())))(result)  
        }

        unitTest("Cond strict"){  
            val result \= interp("cond (head \[false\]) \-\> 1 | (head \[true\]) \-\> 2")  
            assertResult(NumV(2))(result)  
        }  
        unitTest("Cond not strict"){  
            val result \= interp("cond true \-\> (head \[1\]) | true \-\> 2")  
            assertResult(ThunkV(Left(NumC(1), List())))(result)  
        }  
        unitTest("Cond not strict 2"){  
            val result \= interp("cond false \-\> 1 | true \-\> (head \[2\])")  
            assertResult(ThunkV(Left(NumC(2), List())))(result)  
        }

        unitTest("CondE strict"){  
            val result \= interp("cond (head \[false\]) \-\> 1 | else \-\> 2")  
            assertResult(NumV(2))(result)  
        }  
        unitTest("CondE not strict"){  
            val result \= interp("cond true \-\> (head \[1\]) | else \-\> 2")  
            assertResult(ThunkV(Left(NumC(1), List())))(result)  
        }  
        unitTest("CondE not strict 2"){  
            val result \= interp("cond false \-\> 1 | else \-\> (head \[2\])")  
            assertResult(ThunkV(Left(NumC(2), List())))(result)  
        }

        unitTest("Cons not strict"){  
            val result \= interp("1 :: 2")  
            assertResult(ConsV(ThunkV(Left(NumC(1), List())), ThunkV(Left(NumC(2), List()))))(result)  
        }

        unitTest("List not strict"){  
            val result \= interp("\[1\]")  
            assertResult(ConsV(ThunkV(Left(NumC(1), List())), ThunkV(Left(NilC(), List()))))(result)  
        }

        unitTest("Head not strict"){  
            val result \= interp("head (1 :: 2)")  
            assertResult(ThunkV(Left(NumC(1), List())))(result)  
        }

        unitTest("Tail not strict"){  
            val result \= interp("tail (1 :: 2)")  
            assertResult(ThunkV(Left(NumC(2), List())))(result)  
        }

        unitTest("IsNil strict and not strict"){  
            val (result, printed) \= interpPrint("(lambda x \-\> x is nil)(\[print(10)\])")  
            assertResult(Some(BoolV(false)))(result)  
            assertResult("")(printed)  
        }

        unitTest("IsList strict and not strict"){  
            val (result, printed) \= interpPrint("(lambda x \-\> x is list)(\[print(10)\])")  
            assertResult(Some(BoolV(true)))(result)  
            assertResult("")(printed)  
        }

        unitTest("Id not strict"){  
            val result \= interp("(lambda x \-\> x)(1)")  
            assertResult(ThunkV(Left(NumC(1), List())))(result)  
        }

        unitTest("App strict and not strict"){  
            val result \= interp("(lambda x \-\> x(2))(lambda t \-\> t)")  
            assertResult(ThunkV(Left(NumC(2), List(MutBind("x", ThunkV(Right(MutClosureV(LambdaC(List("t"), IdC("t")), List()))))))))(result)  
        }

        unitTest("Let not strict"){  
            val result \= interp("let x \= 1 in x")  
            assertResult(ThunkV(Left(NumC(1), List())))(result)  
        }

        unitTest("LetRec not strict"){  
            val (result, printed) \= interpPrint("let rec x \= print(10) in 1")  
            assertResult(Some(NumV(1)))(result)  
            assertResult("")(printed)  
        }

        unitTest("Print strict"){  
            val (result, printed) \= interpPrint("print(head \[1\])")  
            assertResult(Some(NumV(1)))(result)  
            assertResult("1")(printed)  
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
                  |   x)(2)\!  
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
                x\!  
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
                        merge(\[1, 2, 5\], \[3, 4\])\!  
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
            69, 32, 81, 48, 96, 38, 53, 78, 55, 42, 67, 86, 51\])\!  
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

        unitTest("Print skipped"){  
            val (result, printed) \= interpPrint("(lambda x \-\> if true then x else print(20))(print(10))".stripMargin)  
            assertResult(Some(ThunkV(Left(PrintC(NumC(10)), List()))))(result)  
            assertResult("")(printed)  
        }  
    }

    def desugar(expr: String): ExprC \= {  
        val parsed \= CPLParser.parseUntyped(expr)  
        Desugar.desugar(parsed)  
    }

    def interp(expr: String): Value \= {  
        val desugared \= desugar(expr)  
        Interpret.interp(desugared, Nil)  
    }

    def interpPrint(expr: ExprC): (Option\[Value\], String) \= {  
        capturePrintOutput {  
            Interpret.interp(expr, Nil)  
        }  
    }

    def interpPrint(expr: String): (Option\[Value\], String) \= {  
        interpPrint(desugar(expr))  
    }

}

