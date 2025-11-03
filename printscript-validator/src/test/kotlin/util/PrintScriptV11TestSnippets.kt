package util

import model.node.AssignNode
import model.node.AssignStatementNode
import model.node.BinaryOperationExpressionNode
import model.node.BlockNode
import model.node.BooleanLiteralNode
import model.node.BooleanTypeNode
import model.node.ColonNode
import model.node.ConstDeclarationStatementNode
import model.node.ConstNode
import model.node.ElseBlockNode
import model.node.ElseNode
import model.node.IdentifierNode
import model.node.IfNode
import model.node.IfStatementNode
import model.node.LeftBraceNode
import model.node.LeftParenthesisNode
import model.node.LetDeclarationStatementNode
import model.node.LetNode
import model.node.MultiplyNode
import model.node.Node
import model.node.NumberLiteralNode
import model.node.NumberTypeNode
import model.node.ParenthesizedExpressionNode
import model.node.PlusNode
import model.node.PrintlnKeywordNode
import model.node.PrintlnStatementNode
import model.node.ReadEnvExpressionNode
import model.node.ReadEnvKeywordNode
import model.node.ReadInputExpressionNode
import model.node.ReadInputKeywordNode
import model.node.RightBraceNode
import model.node.RightParenthesisNode
import model.node.SemicolonNode
import model.node.StringLiteralNode
import model.node.StringTypeNode
import model.node.UnaryOperationNode
import model.span.Position
import model.span.Span
import model.value.BooleanValue
import model.value.IntegerValue
import model.value.StringValue

object PrintScriptV11TestSnippets {

    init {
        TestSnippetRegistry.register(
            "const-invalid-assignment",
            listOf(
                Node.Composite(
                    type = ConstDeclarationStatementNode,
                    span = Span(Position(1,1,0), Position(1,39,38)),
                    children = listOf(
                        Node.Leaf(ConstNode, StringValue("const"), Span(Position(1,1,0), Position(1,6,5))),
                        Node.Leaf(IdentifierNode, StringValue("x"), Span(Position(1,7,6), Position(1,8,7))),
                        Node.Leaf(ColonNode, StringValue(":"), Span(Position(1,8,7), Position(1,9,8))),
                        Node.Leaf(NumberTypeNode, StringValue("number"), Span(Position(1,10,9), Position(1,16,15))),
                        Node.Leaf(AssignNode, StringValue("="), Span(Position(1,17,16), Position(1,18,17))),
                        Node.Composite(
                            type = ReadEnvExpressionNode,
                            span = Span(Position(1,19,18), Position(1,38,37)),
                            children = listOf(
                                Node.Leaf(ReadEnvKeywordNode, StringValue("readEnv"), Span(Position(1,19,18), Position(1,26,25))),
                                Node.Leaf(LeftParenthesisNode, StringValue("("), Span(Position(1,26,25), Position(1,27,26))),
                                Node.Leaf(StringLiteralNode, StringValue("NUMBER"), Span(Position(1,27,26), Position(1,35,34))),
                                Node.Leaf(RightParenthesisNode, StringValue(")"), Span(Position(1,35,34), Position(1,36,35)))
                            )
                        ),
                        Node.Leaf(SemicolonNode, StringValue(";"), Span(Position(1,38,37), Position(1,39,38)))
                    )
                ),
                Node.Composite(
                    type = ConstDeclarationStatementNode,
                    span = Span(Position(2,1,39), Position(2,24,62)),
                    children = listOf(
                        Node.Leaf(ConstNode, StringValue("const"), Span(Position(2,1,39), Position(2,6,44))),
                        Node.Leaf(IdentifierNode, StringValue("y"), Span(Position(2,7,45), Position(2,8,46))),
                        Node.Leaf(ColonNode, StringValue(":"), Span(Position(2,8,46), Position(2,9,47))),
                        Node.Leaf(StringTypeNode, StringValue("string"), Span(Position(2,10,48), Position(2,16,54))),
                        Node.Leaf(AssignNode, StringValue("="), Span(Position(2,17,55), Position(2,18,56))),
                        Node.Leaf(IdentifierNode, StringValue("x"), Span(Position(2,19,57), Position(2,20,58))),
                        Node.Leaf(SemicolonNode, StringValue(";"), Span(Position(2,23,61), Position(2,24,62)))
                    )
                )
            )
        )

        TestSnippetRegistry.register(
            "const-missing-assignment",
            listOf(
                Node.Composite(
                    type = ConstDeclarationStatementNode,
                    span = Span(Position(1, 1, 0), Position(1, 14, 13)),
                    children = listOf(
                        Node.Leaf(ConstNode, StringValue("const"), Span(Position(1, 1, 0), Position(1, 6, 5))),
                        Node.Leaf(IdentifierNode, StringValue("x"), Span(Position(1, 7, 6), Position(1, 8, 7))),
                        Node.Leaf(ColonNode, StringValue(":"), Span(Position(1, 8, 7), Position(1, 9, 8))),
                        Node.Leaf(NumberTypeNode, StringValue("number"), Span(Position(1, 10, 9), Position(1, 14, 13))),
                    ),
                ),
            ),
        )

        TestSnippetRegistry.register(
            "const-reassignment",
            listOf(
                Node.Composite(
                    type = ConstDeclarationStatementNode,
                    span = Span(Position(1, 1, 0), Position(1, 18, 17)),
                    children = listOf(
                        Node.Leaf(ConstNode, StringValue("const"), Span(Position(1, 1, 0), Position(1, 6, 5))),
                        Node.Leaf(IdentifierNode, StringValue("x"), Span(Position(1, 7, 6), Position(1, 8, 7))),
                        Node.Leaf(ColonNode, StringValue(":"), Span(Position(1, 8, 7), Position(1, 9, 8))),
                        Node.Leaf(NumberTypeNode, StringValue("number"), Span(Position(1, 10, 9), Position(1, 16, 15))),
                        Node.Leaf(AssignNode, StringValue("="), Span(Position(1, 17, 16), Position(1, 18, 17))),
                        Node.Leaf(NumberLiteralNode, IntegerValue(5), Span(Position(1, 18, 17), Position(1, 19, 18))),
                    ),
                ),

                Node.Composite(
                    type = AssignStatementNode,
                    span = Span(Position(2, 1, 19), Position(2, 7, 25)),
                    children = listOf(
                        Node.Leaf(IdentifierNode, StringValue("x"), Span(Position(2, 1, 19), Position(2, 2, 20))),
                        Node.Leaf(AssignNode, StringValue("="), Span(Position(2, 3, 21), Position(2, 4, 22))),
                        Node.Leaf(NumberLiteralNode, IntegerValue(10), Span(Position(2, 5, 23), Position(2, 7, 25))),
                    ),
                ),
            ),
        )

        TestSnippetRegistry.register(
            "const-with-runtime-values",
            listOf(
                Node.Composite(
                    type = ConstDeclarationStatementNode,
                    span = Span(Position(1,1,0), Position(1,39,38)),
                    children = listOf(
                        Node.Leaf(ConstNode, StringValue("const"), Span(Position(1,1,0), Position(1,6,5))),
                        Node.Leaf(IdentifierNode, StringValue("x"), Span(Position(1,7,6), Position(1,8,7))),
                        Node.Leaf(ColonNode, StringValue(":"), Span(Position(1,8,7), Position(1,9,8))),
                        Node.Leaf(NumberTypeNode, StringValue("number"), Span(Position(1,10,9), Position(1,16,15))),
                        Node.Leaf(AssignNode, StringValue("="), Span(Position(1,17,16), Position(1,18,17))),
                        Node.Composite(
                            type = ReadInputExpressionNode,
                            span = Span(Position(1,19,18), Position(1,39,38)),
                            children = listOf(
                                Node.Leaf(ReadInputKeywordNode, StringValue("readInput"), Span(Position(1,19,18), Position(1,28,27))),
                                Node.Leaf(LeftParenthesisNode, StringValue("("), Span(Position(1,28,27), Position(1,29,28))),
                                Node.Leaf(StringLiteralNode, StringValue("Enter a number: "), Span(Position(1,29,28), Position(1,39,38))),
                                Node.Leaf(RightParenthesisNode, StringValue(")"), Span(Position(1,39,38), Position(1,40,39)))
                            )
                        ),
                        Node.Leaf(SemicolonNode, StringValue(";"), Span(Position(1,40,39), Position(1,41,40)))
                    )
                ),

                Node.Composite(
                    type = ConstDeclarationStatementNode,
                    span = Span(Position(2,1,41), Position(2,26,66)),
                    children = listOf(
                        Node.Leaf(ConstNode, StringValue("const"), Span(Position(2,1,41), Position(2,6,46))),
                        Node.Leaf(IdentifierNode, StringValue("y"), Span(Position(2,7,47), Position(2,8,48))),
                        Node.Leaf(ColonNode, StringValue(":"), Span(Position(2,8,48), Position(2,9,49))),
                        Node.Leaf(StringTypeNode, StringValue("string"), Span(Position(2,10,50), Position(2,16,56))),
                        Node.Leaf(AssignNode, StringValue("="), Span(Position(2,17,57), Position(2,18,58))),
                        Node.Composite(
                            type = ReadEnvExpressionNode,
                            span = Span(Position(2,19,59), Position(2,26,66)),
                            children = listOf(
                                Node.Leaf(ReadEnvKeywordNode, StringValue("readEnv"), Span(Position(2,19,59), Position(2,25,65))),
                                Node.Leaf(LeftParenthesisNode, StringValue("("), Span(Position(2,25,65), Position(2,26,66))),
                                Node.Leaf(StringLiteralNode, StringValue("KEY"), Span(Position(2,26,66), Position(2,29,69))),
                                Node.Leaf(RightParenthesisNode, StringValue(")"), Span(Position(2,29,69), Position(2,30,70)))
                            )
                        ),
                        Node.Leaf(SemicolonNode, StringValue(";"), Span(Position(2,30,70), Position(2,31,71)))
                    )
                )
            )
        )

        TestSnippetRegistry.register(
            "invalid-boolean-binary-operation",
            listOf(
                Node.Composite(
                    type = ConstDeclarationStatementNode,
                    span = Span(Position(1,1,0), Position(1,38,37)),
                    children = listOf(
                        Node.Leaf(ConstNode, StringValue("const"), Span(Position(1,1,0), Position(1,6,5))),
                        Node.Leaf(IdentifierNode, StringValue("x"), Span(Position(1,7,6), Position(1,8,7))),
                        Node.Leaf(ColonNode, StringValue(":"), Span(Position(1,8,7), Position(1,9,8))),
                        Node.Leaf(BooleanTypeNode, StringValue("boolean"), Span(Position(1,10,9), Position(1,17,16))),
                        Node.Leaf(AssignNode, StringValue("="), Span(Position(1,18,17), Position(1,19,18))),
                        Node.Composite(
                            type = ReadEnvExpressionNode,
                            span = Span(Position(1,20,19), Position(1,37,36)),
                            children = listOf(
                                Node.Leaf(ReadEnvKeywordNode, StringValue("reaEnv"), Span(Position(1,20,19), Position(1,26,25))),
                                Node.Leaf(LeftParenthesisNode, StringValue("("), Span(Position(1,26,25), Position(1,27,26))),
                                Node.Leaf(StringLiteralNode, StringValue("BOOLEAN"), Span(Position(1,27,26), Position(1,36,35))),
                                Node.Leaf(RightParenthesisNode, StringValue(")"), Span(Position(1,36,35), Position(1,37,36)))
                            )
                        ),
                        Node.Leaf(SemicolonNode, StringValue(";"), Span(Position(1,37,36), Position(1,38,37)))
                    )
                ),
                Node.Composite(
                    type = PrintlnStatementNode,
                    span = Span(Position(2,1,38), Position(2,18,55)),
                    children = listOf(
                        Node.Leaf(PrintlnKeywordNode, StringValue("println"), Span(Position(2,1,38), Position(2,8,45))),
                        Node.Leaf(LeftParenthesisNode, StringValue("("), Span(Position(2,8,45), Position(2,9,46))),
                        Node.Composite(
                            type = BinaryOperationExpressionNode,
                            span = Span(Position(2,9,46), Position(2,15,52)),
                            children = listOf(
                                Node.Leaf(IdentifierNode, StringValue("x"), Span(Position(2,9,46), Position(2,10,47))),
                                Node.Leaf(MultiplyNode, StringValue("*"), Span(Position(2,11,48), Position(2,12,49))),
                                Node.Leaf(NumberLiteralNode, IntegerValue(5), Span(Position(2,13,50), Position(2,14,51)))
                            )
                        ),
                        Node.Leaf(RightParenthesisNode, StringValue(")"), Span(Position(2,15,52), Position(2,16,53))),
                        Node.Leaf(SemicolonNode, StringValue(";"), Span(Position(2,17,54), Position(2,18,55)))
                    )
                )
            )
        )

        TestSnippetRegistry.register(
            "invalid-type-readEnv-argument",
            listOf(
                Node.Composite(
                    type = LetDeclarationStatementNode,
                    span = Span(Position(1, 1, 0), Position(1, 28, 27)),
                    children = listOf(
                        Node.Leaf(LetNode, StringValue("let"), Span(Position(1, 1, 0), Position(1, 4, 3))),
                        Node.Leaf(IdentifierNode, StringValue("x"), Span(Position(1, 5, 4), Position(1, 6, 5))),
                        Node.Leaf(ColonNode, StringValue(":"), Span(Position(1, 6, 5), Position(1, 7, 6))),
                        Node.Leaf(StringTypeNode, StringValue("string"), Span(Position(1, 8, 7), Position(1, 14, 13))),
                        Node.Leaf(AssignNode, StringValue("="), Span(Position(1, 15, 14), Position(1, 16, 15))),

                        Node.Composite(
                            type = ReadEnvExpressionNode,
                            span = Span(Position(1, 17, 16), Position(1, 27, 26)),
                            children = listOf(
                                Node.Leaf(ReadEnvKeywordNode, StringValue("readEnv"), Span(Position(1, 17, 16), Position(1, 24, 23))),
                                Node.Leaf(LeftParenthesisNode, StringValue("("), Span(Position(1, 24, 23), Position(1, 25, 24))),
                                Node.Leaf(NumberLiteralNode, IntegerValue(5), Span(Position(1, 25, 24), Position(1, 26, 25))),
                                Node.Leaf(RightParenthesisNode, StringValue(")"), Span(Position(1, 26, 25), Position(1, 27, 26))),
                            ),
                        ),
                        Node.Leaf(SemicolonNode, StringValue(";"), Span(Position(1, 27, 26), Position(1, 28, 27))),
                    ),
                ),
            ),
        )

        TestSnippetRegistry.register(
            "invalid-type-readInput-argument",
            listOf(
                Node.Composite(
                    type = LetDeclarationStatementNode,
                    span = Span(Position(1, 1, 0), Position(1, 29, 28)),
                    children = listOf(
                        Node.Leaf(LetNode, StringValue("let"), Span(Position(1, 1, 0), Position(1, 4, 3))),
                        Node.Leaf(IdentifierNode, StringValue("x"), Span(Position(1, 5, 4), Position(1, 6, 5))),
                        Node.Leaf(ColonNode, StringValue(":"), Span(Position(1, 6, 5), Position(1, 7, 6))),
                        Node.Leaf(StringTypeNode, StringValue("string"), Span(Position(1, 8, 7), Position(1, 14, 13))),
                        Node.Leaf(AssignNode, StringValue("="), Span(Position(1, 15, 14), Position(1, 16, 15))),

                        Node.Composite(
                            type = ReadInputExpressionNode,
                            span = Span(Position(1, 17, 16), Position(1, 28, 27)),
                            children = listOf(
                                Node.Leaf(ReadInputKeywordNode, StringValue("readInput"), Span(Position(1, 17, 16), Position(1, 26, 25))),
                                Node.Leaf(LeftParenthesisNode, StringValue("("), Span(Position(1, 26, 25), Position(1, 27, 26))),
                                Node.Leaf(BooleanLiteralNode, BooleanValue(true), Span(Position(1, 27, 26), Position(1, 31, 30))),
                                Node.Leaf(RightParenthesisNode, StringValue(")"), Span(Position(1, 31, 30), Position(1, 32, 31))),
                            ),
                        ),
                        Node.Leaf(SemicolonNode, StringValue(";"), Span(Position(1, 32, 31), Position(1, 33, 32))),
                    ),
                ),
            ),
        )

        TestSnippetRegistry.register(
            "invalid-type-unary-operation",
            listOf(
                Node.Composite(
                    type = ConstDeclarationStatementNode,
                    span = Span(Position(1,1,0), Position(1,27,26)),
                    children = listOf(
                        Node.Leaf(ConstNode, StringValue("const"), Span(Position(1,1,0), Position(1,6,5))),
                        Node.Leaf(IdentifierNode, StringValue("x"), Span(Position(1,7,6), Position(1,8,7))),
                        Node.Leaf(ColonNode, StringValue(":"), Span(Position(1,8,7), Position(1,9,8))),
                        Node.Leaf(BooleanTypeNode, StringValue("boolean"), Span(Position(1,10,9), Position(1,17,16))),
                        Node.Leaf(AssignNode, StringValue("="), Span(Position(1,18,17), Position(1,19,18))),
                        Node.Composite(
                            type = UnaryOperationNode,
                            span = Span(Position(1,20,19), Position(1,26,25)),
                            children = listOf(
                                Node.Leaf(PlusNode, StringValue("+"), Span(Position(1,20,19), Position(1,21,20))),
                                Node.Leaf(BooleanLiteralNode, BooleanValue(true), Span(Position(1,21,20), Position(1,25,24)))
                            )
                        ),
                        Node.Leaf(SemicolonNode, StringValue(";"), Span(Position(1,26,25), Position(1,27,26)))
                    )
                )
            )
        )

        TestSnippetRegistry.register(
            "missing-readEnv-argument",
            listOf(
                Node.Composite(
                    type = LetDeclarationStatementNode,
                    span = Span(Position(1, 1, 0), Position(1, 22, 21)),
                    children = listOf(
                        Node.Leaf(LetNode, StringValue("let"), Span(Position(1, 1, 0), Position(1, 4, 3))),
                        Node.Leaf(IdentifierNode, StringValue("x"), Span(Position(1, 5, 4), Position(1, 6, 5))),
                        Node.Leaf(ColonNode, StringValue(":"), Span(Position(1, 6, 5), Position(1, 7, 6))),
                        Node.Leaf(BooleanTypeNode, StringValue("boolean"), Span(Position(1, 8, 7), Position(1, 15, 14))),
                        Node.Leaf(AssignNode, StringValue("="), Span(Position(1, 16, 15), Position(1, 17, 16))),

                        Node.Composite(
                            type = ReadEnvExpressionNode,
                            span = Span(Position(1, 18, 17), Position(1, 22, 21)),
                            children = listOf(
                                Node.Leaf(ReadEnvKeywordNode, StringValue("readEnv"), Span(Position(1, 18, 17), Position(1, 25, 24))),
                                Node.Leaf(LeftParenthesisNode, StringValue("("), Span(Position(1, 25, 24), Position(1, 26, 25))),
                                Node.Leaf(RightParenthesisNode, StringValue(")"), Span(Position(1, 26, 25), Position(1, 27, 26))),
                            ),
                        ),
                        Node.Leaf(SemicolonNode, StringValue(";"), Span(Position(1, 27, 26), Position(1, 28, 27))),
                    ),
                ),
            ),
        )

        TestSnippetRegistry.register(
            "missing-readInput-argument",
            listOf(
                Node.Composite(
                    type = LetDeclarationStatementNode,
                    span = Span(Position(1, 1, 0), Position(1, 20, 19)),
                    children = listOf(
                        Node.Leaf(LetNode, StringValue("let"), Span(Position(1, 1, 0), Position(1, 4, 3))),
                        Node.Leaf(IdentifierNode, StringValue("x"), Span(Position(1, 5, 4), Position(1, 6, 5))),
                        Node.Leaf(ColonNode, StringValue(":"), Span(Position(1, 6, 5), Position(1, 7, 6))),
                        Node.Leaf(NumberTypeNode, StringValue("number"), Span(Position(1, 8, 7), Position(1, 14, 13))),
                        Node.Leaf(AssignNode, StringValue("="), Span(Position(1, 15, 14), Position(1, 16, 15))),

                        Node.Composite(
                            type = ReadInputExpressionNode,
                            span = Span(Position(1, 17, 16), Position(1, 20, 19)),
                            children = listOf(
                                Node.Leaf(ReadInputKeywordNode, StringValue("readInput"), Span(Position(1, 17, 16), Position(1, 26, 25))),
                                Node.Leaf(LeftParenthesisNode, StringValue("("), Span(Position(1, 26, 25), Position(1, 27, 26))),
                                Node.Leaf(RightParenthesisNode, StringValue(")"), Span(Position(1, 27, 26), Position(1, 28, 27))),
                            ),
                        ),
                        Node.Leaf(SemicolonNode, StringValue(";"), Span(Position(1, 28, 27), Position(1, 29, 28))),
                    ),
                ),
            ),
        )

        TestSnippetRegistry.register(
            "valid-statements",
            listOf(
                Node.Composite(
                    type = ConstDeclarationStatementNode,
                    span = Span(Position(1, 1, 0), Position(1, 22, 21)),
                    children = listOf(
                        Node.Leaf(ConstNode, StringValue("const"), Span(Position(1, 1, 0), Position(1, 6, 5))),
                        Node.Leaf(IdentifierNode, StringValue("x"), Span(Position(1, 7, 6), Position(1, 8, 7))),
                        Node.Leaf(ColonNode, StringValue(":"), Span(Position(1, 8, 7), Position(1, 9, 8))),
                        Node.Leaf(BooleanTypeNode, StringValue("boolean"), Span(Position(1, 10, 9), Position(1, 17, 16))),
                        Node.Leaf(AssignNode, StringValue("="), Span(Position(1, 18, 17), Position(1, 19, 18))),
                        Node.Leaf(BooleanLiteralNode, BooleanValue(true), Span(Position(1, 20, 19), Position(1, 22, 21))),
                        Node.Leaf(SemicolonNode, StringValue(";"), Span(Position(1, 22, 21), Position(1, 23, 22)))
                    )
                ),

                Node.Composite(
                    type = IfStatementNode,
                    span = Span(Position(3, 1, 24), Position(9, 2, 120)),
                    children = listOf(
                        Node.Leaf(IfNode, StringValue("if"), Span(Position(3, 1, 24), Position(3, 3, 26))),
                        Node.Leaf(LeftParenthesisNode, StringValue("("), Span(Position(3, 4, 27), Position(3, 5, 28))),
                        Node.Leaf(IdentifierNode, StringValue("x"), Span(Position(3, 5, 28), Position(3, 6, 29))),
                        Node.Leaf(RightParenthesisNode, StringValue(")"), Span(Position(3, 6, 29), Position(3, 7, 30))),

                        Node.Composite(
                            type = BlockNode,
                            span = Span(Position(3, 8, 31), Position(5, 2, 60)),
                            children = listOf(
                                Node.Leaf(LeftBraceNode, StringValue("{"), Span(Position(3, 8, 31), Position(3, 9, 32))),
                                Node.Composite(
                                    type = PrintlnStatementNode,
                                    span = Span(Position(4, 5, 37), Position(4, 28, 60)),
                                    children = listOf(
                                        Node.Leaf(PrintlnKeywordNode, StringValue("println"), Span(Position(4, 5, 37), Position(4, 12, 44))),
                                        Node.Leaf(LeftParenthesisNode, StringValue("("), Span(Position(4, 12, 44), Position(4, 13, 45))),
                                        Node.Leaf(StringLiteralNode, StringValue("x is true"), Span(Position(4, 13, 45), Position(4, 25, 57))),
                                        Node.Leaf(RightParenthesisNode, StringValue(")"), Span(Position(4, 25, 57), Position(4, 26, 58))),
                                        Node.Leaf(SemicolonNode, StringValue(";"), Span(Position(4, 26, 58), Position(4, 27, 59)))
                                    )
                                ),
                                Node.Leaf(RightBraceNode, StringValue("}"), Span(Position(5, 1, 59), Position(5, 2, 60)))
                            )
                        ),

                        Node.Composite(
                            type = ElseBlockNode,
                            span = Span(Position(6, 1, 61), Position(9, 2, 120)),
                            children = listOf(
                                Node.Leaf(ElseNode, StringValue("else"), Span(Position(6, 1, 61), Position(6, 5, 65))),
                                Node.Leaf(LeftBraceNode, StringValue("{"), Span(Position(6, 6, 66), Position(6, 7, 67))),

                                Node.Composite(
                                    type = PrintlnStatementNode,
                                    span = Span(Position(7, 5, 72), Position(7, 36, 103)),
                                    children = listOf(
                                        Node.Leaf(PrintlnKeywordNode, StringValue("println"), Span(Position(7, 5, 72), Position(7, 12, 79))),
                                        Node.Leaf(LeftParenthesisNode, StringValue("("), Span(Position(7, 12, 79), Position(7, 13, 80))),

                                        Node.Composite(
                                            type = BinaryOperationExpressionNode,
                                            span = Span(Position(7, 13, 80), Position(7, 35, 102)),
                                            children = listOf(
                                                Node.Composite(
                                                    type = ParenthesizedExpressionNode,
                                                    span = Span(Position(7, 13, 80), Position(7, 19, 86)),
                                                    children = listOf(
                                                        Node.Leaf(LeftParenthesisNode, StringValue("("), Span(Position(7, 13, 80), Position(7, 14, 81))),
                                                        Node.Leaf(NumberLiteralNode, IntegerValue(5), Span(Position(7, 14, 81), Position(7, 15, 82))),
                                                        Node.Leaf(MultiplyNode, StringValue("*"), Span(Position(7, 15, 82), Position(7, 16, 83))),
                                                        Node.Leaf(NumberLiteralNode, IntegerValue(2), Span(Position(7, 16, 83), Position(7, 17, 84))),
                                                        Node.Leaf(RightParenthesisNode, StringValue(")"), Span(Position(7, 18, 85), Position(7, 19, 86)))
                                                    )
                                                ),
                                                Node.Leaf(PlusNode, StringValue("+"), Span(Position(7, 20, 87), Position(7, 21, 88))),
                                                Node.Leaf(StringLiteralNode, StringValue("Number"), Span(Position(7, 22, 89), Position(7, 35, 102)))
                                            )
                                        ),

                                        Node.Leaf(RightParenthesisNode, StringValue(")"), Span(Position(7, 35, 102), Position(7, 36, 103))),
                                        Node.Leaf(SemicolonNode, StringValue(";"), Span(Position(7, 36, 103), Position(7, 37, 104)))
                                    )
                                ),

                                Node.Leaf(RightBraceNode, StringValue("}"), Span(Position(9, 1, 119), Position(9, 2, 120)))
                            )
                        )
                    )
                )
            )
        )
    }
}
