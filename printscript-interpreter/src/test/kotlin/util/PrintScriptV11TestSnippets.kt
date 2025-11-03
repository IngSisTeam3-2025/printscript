package util

import model.node.AssignNode
import model.node.BinaryOperationExpressionNode
import model.node.BlockNode
import model.node.BooleanLiteralNode
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
import model.node.Node
import model.node.NumberLiteralNode
import model.node.NumberTypeNode
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
import model.span.Position
import model.span.Span
import model.value.BooleanValue
import model.value.FloatValue
import model.value.IntegerValue
import model.value.StringValue

object PrintScriptV11TestSnippets {

    init {
        TestSnippetRegistry.register(
            "basic-const-assignment",
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
                        Node.Leaf(NumberLiteralNode, FloatValue(5.87f), Span(Position(1, 18, 17), Position(1, 22, 21))),
                        Node.Leaf(SemicolonNode, StringValue(";"), Span(Position(1, 22, 21), Position(1, 23, 22))),
                    ),
                ),
                Node.Composite(
                    type = PrintlnStatementNode,
                    span = Span(Position(2, 1, 23), Position(2, 9, 31)),
                    children = listOf(
                        Node.Leaf(PrintlnKeywordNode, StringValue("println"), Span(Position(2, 1, 23), Position(2, 8, 30))),
                        Node.Leaf(LeftParenthesisNode, StringValue("("), Span(Position(2, 8, 30), Position(2, 9, 31))),
                        Node.Leaf(IdentifierNode, StringValue("x"), Span(Position(2, 9, 31), Position(2, 10, 32))),
                        Node.Leaf(RightParenthesisNode, StringValue(")"), Span(Position(2, 10, 32), Position(2, 11, 33))),
                        Node.Leaf(SemicolonNode, StringValue(";"), Span(Position(2, 11, 33), Position(2, 12, 34))),
                    ),
                ),
            ),
        )

        TestSnippetRegistry.register(
            "if-else-with-println-statement",
            listOf(
                Node.Composite(
                    type = IfStatementNode,
                    span = Span(Position(1, 1, 0), Position(9, 2, 120)),
                    children = listOf(
                        Node.Leaf(IfNode, StringValue("if"), Span(Position(1, 1, 0), Position(1, 3, 2))),
                        Node.Leaf(LeftParenthesisNode, StringValue("("), Span(Position(1, 4, 3), Position(1, 5, 4))),
                        Node.Leaf(BooleanLiteralNode, BooleanValue(true), Span(Position(1, 5, 4), Position(1, 9, 8))),
                        Node.Leaf(RightParenthesisNode, StringValue(")"), Span(Position(1, 9, 8), Position(1, 10, 9))),

                        Node.Composite(
                            type = BlockNode,
                            span = Span(Position(1, 11, 10), Position(5, 2, 60)),
                            children = listOf(
                                Node.Leaf(LeftBraceNode, StringValue("{"), Span(Position(1, 11, 10), Position(1, 12, 11))),
                                Node.Composite(
                                    type = IfStatementNode,
                                    span = Span(Position(2, 5, 15), Position(4, 2, 50)),
                                    children = listOf(
                                        Node.Leaf(IfNode, StringValue("if"), Span(Position(2, 5, 15), Position(2, 7, 17))),
                                        Node.Leaf(LeftParenthesisNode, StringValue("("), Span(Position(2, 8, 18), Position(2, 9, 19))),
                                        Node.Leaf(BooleanLiteralNode, BooleanValue(false), Span(Position(2, 9, 19), Position(2, 14, 24))),
                                        Node.Leaf(RightParenthesisNode, StringValue(")"), Span(Position(2, 14, 24), Position(2, 15, 25))),
                                        Node.Composite(
                                            type = BlockNode,
                                            span = Span(Position(2, 16, 26), Position(3, 2, 40)),
                                            children = listOf(
                                                Node.Leaf(LeftBraceNode, StringValue("{"), Span(Position(2, 16, 26), Position(2, 17, 27))),
                                                Node.Composite(
                                                    type = PrintlnStatementNode,
                                                    span = Span(Position(3, 5, 32), Position(3, 9, 36)),
                                                    children = listOf(
                                                        Node.Leaf(PrintlnKeywordNode, StringValue("println"), Span(Position(3, 5, 32), Position(3, 12, 39))),
                                                        Node.Leaf(LeftParenthesisNode, StringValue("("), Span(Position(3, 12, 39), Position(3, 13, 40))),
                                                        Node.Leaf(NumberLiteralNode, IntegerValue(1), Span(Position(3, 13, 40), Position(3, 14, 41))),
                                                        Node.Leaf(RightParenthesisNode, StringValue(")"), Span(Position(3, 14, 41), Position(3, 15, 42))),
                                                        Node.Leaf(SemicolonNode, StringValue(";"), Span(Position(3, 15, 42), Position(3, 16, 43))),
                                                    ),
                                                ),
                                                Node.Leaf(RightBraceNode, StringValue("}"), Span(Position(3, 1, 41), Position(3, 2, 42))),
                                            ),
                                        ),
                                        Node.Composite(
                                            type = ElseBlockNode,
                                            span = Span(Position(3, 3, 44), Position(4, 2, 50)),
                                            children = listOf(
                                                Node.Leaf(ElseNode, StringValue("else"), Span(Position(3, 3, 44), Position(3, 7, 48))),
                                                Node.Composite(
                                                    type = BlockNode,
                                                    span = Span(Position(3, 8, 49), Position(4, 2, 50)),
                                                    children = listOf(
                                                        Node.Leaf(LeftBraceNode, StringValue("{"), Span(Position(3, 8, 49), Position(3, 9, 50))),
                                                        Node.Composite(
                                                            type = PrintlnStatementNode,
                                                            span = Span(Position(4, 5, 52), Position(4, 9, 56)),
                                                            children = listOf(
                                                                Node.Leaf(PrintlnKeywordNode, StringValue("println"), Span(Position(4, 5, 52), Position(4, 12, 59))),
                                                                Node.Leaf(LeftParenthesisNode, StringValue("("), Span(Position(4, 12, 59), Position(4, 13, 60))),
                                                                Node.Leaf(NumberLiteralNode, IntegerValue(2), Span(Position(4, 13, 60), Position(4, 14, 61))),
                                                                Node.Leaf(RightParenthesisNode, StringValue(")"), Span(Position(4, 14, 61), Position(4, 15, 62))),
                                                                Node.Leaf(SemicolonNode, StringValue(";"), Span(Position(4, 15, 62), Position(4, 16, 63))),
                                                            ),
                                                        ),
                                                        Node.Leaf(RightBraceNode, StringValue("}"), Span(Position(4, 1, 61), Position(4, 2, 62))),
                                                    ),
                                                ),
                                            ),
                                        ),
                                    ),
                                ),
                                Node.Leaf(RightBraceNode, StringValue("}"), Span(Position(5, 1, 59), Position(5, 2, 60))),
                            ),
                        ),

                        Node.Composite(
                            type = ElseBlockNode,
                            span = Span(Position(5, 3, 61), Position(9, 2, 120)),
                            children = listOf(
                                Node.Leaf(ElseNode, StringValue("else"), Span(Position(5, 3, 61), Position(5, 7, 65))),
                                Node.Composite(
                                    type = BlockNode,
                                    span = Span(Position(5, 8, 66), Position(9, 2, 120)),
                                    children = listOf(
                                        Node.Leaf(LeftBraceNode, StringValue("{"), Span(Position(5, 8, 66), Position(5, 9, 67))),
                                        Node.Composite(
                                            type = PrintlnStatementNode,
                                            span = Span(Position(6, 5, 72), Position(6, 9, 76)),
                                            children = listOf(
                                                Node.Leaf(PrintlnKeywordNode, StringValue("println"), Span(Position(6, 5, 72), Position(6, 12, 79))),
                                                Node.Leaf(LeftParenthesisNode, StringValue("("), Span(Position(6, 12, 79), Position(6, 13, 80))),
                                                Node.Leaf(NumberLiteralNode, IntegerValue(3), Span(Position(6, 13, 80), Position(6, 14, 81))),
                                                Node.Leaf(RightParenthesisNode, StringValue(")"), Span(Position(6, 14, 81), Position(6, 15, 82))),
                                                Node.Leaf(SemicolonNode, StringValue(";"), Span(Position(6, 15, 82), Position(6, 16, 83))),
                                            ),
                                        ),
                                        Node.Leaf(RightBraceNode, StringValue("}"), Span(Position(9, 1, 119), Position(9, 2, 120))),
                                    ),
                                ),
                            ),
                        ),
                    ),
                ),
            ),
        )

        TestSnippetRegistry.register(
            "invalid-const-assignment",
            listOf(
                Node.Composite(
                    type = ConstDeclarationStatementNode,
                    span = Span(Position(1, 1, 0), Position(1, 28, 27)),
                    children = listOf(
                        Node.Leaf(ConstNode, StringValue("const"), Span(Position(1, 1, 0), Position(1, 6, 5))),
                        Node.Leaf(IdentifierNode, StringValue("x"), Span(Position(1, 7, 6), Position(1, 8, 7))),
                        Node.Leaf(ColonNode, StringValue(":"), Span(Position(1, 8, 7), Position(1, 9, 8))),
                        Node.Leaf(NumberTypeNode, StringValue("number"), Span(Position(1, 10, 9), Position(1, 16, 15))),
                        Node.Leaf(AssignNode, StringValue("="), Span(Position(1, 17, 16), Position(1, 18, 17))),
                        Node.Leaf(StringLiteralNode, StringValue("Hello, World!"), Span(Position(1, 19, 18), Position(1, 33, 32))),
                        Node.Leaf(SemicolonNode, StringValue(";"), Span(Position(1, 33, 32), Position(1, 34, 33))),
                    ),
                ),
            ),
        )

        TestSnippetRegistry.register(
            "invalid-readEnv-statement",
            listOf(
                Node.Composite(
                    type = ConstDeclarationStatementNode,
                    span = Span(Position(1, 1, 0), Position(1, 21, 20)),
                    children = listOf(
                        Node.Leaf(ConstNode, StringValue("const"), Span(Position(1, 1, 0), Position(1, 6, 5))),
                        Node.Leaf(IdentifierNode, StringValue("x"), Span(Position(1, 7, 6), Position(1, 8, 7))),
                        Node.Leaf(ColonNode, StringValue(":"), Span(Position(1, 8, 7), Position(1, 9, 8))),
                        Node.Leaf(NumberTypeNode, StringValue("number"), Span(Position(1, 10, 9), Position(1, 16, 15))),
                        Node.Leaf(AssignNode, StringValue("="), Span(Position(1, 17, 16), Position(1, 18, 17))),
                        Node.Composite(
                            type = ReadEnvExpressionNode,
                            span = Span(Position(1, 18, 17), Position(1, 21, 20)),
                            children = listOf(
                                Node.Leaf(ReadEnvKeywordNode, StringValue("readEnv"), Span(Position(1, 18, 17), Position(1, 24, 23))),
                                Node.Leaf(LeftParenthesisNode, StringValue("("), Span(Position(1, 24, 23), Position(1, 25, 24))),
                                Node.Leaf(NumberLiteralNode, IntegerValue(5), Span(Position(1, 25, 24), Position(1, 26, 25))),
                                Node.Leaf(RightParenthesisNode, StringValue(")"), Span(Position(1, 26, 25), Position(1, 27, 26))),
                            ),
                        ),
                        Node.Leaf(SemicolonNode, StringValue(";"), Span(Position(1, 27, 26), Position(1, 28, 27))),
                    ),
                ),
                Node.Composite(
                    type = PrintlnStatementNode,
                    span = Span(Position(2, 1, 28), Position(2, 10, 37)),
                    children = listOf(
                        Node.Leaf(PrintlnKeywordNode, StringValue("println"), Span(Position(2, 1, 28), Position(2, 8, 35))),
                        Node.Leaf(LeftParenthesisNode, StringValue("("), Span(Position(2, 8, 35), Position(2, 9, 36))),
                        Node.Leaf(IdentifierNode, StringValue("x"), Span(Position(2, 9, 36), Position(2, 10, 37))),
                        Node.Leaf(RightParenthesisNode, StringValue(")"), Span(Position(2, 10, 37), Position(2, 11, 38))),
                        Node.Leaf(SemicolonNode, StringValue(";"), Span(Position(2, 11, 38), Position(2, 12, 39))),
                    ),
                ),
            ),
        )

        TestSnippetRegistry.register(
            "missing-readEnv-key",
            listOf(
                Node.Composite(
                    type = ConstDeclarationStatementNode,
                    span = Span(Position(1, 1, 0), Position(1, 27, 26)),
                    children = listOf(
                        Node.Leaf(ConstNode, StringValue("const"), Span(Position(1, 1, 0), Position(1, 6, 5))),
                        Node.Leaf(IdentifierNode, StringValue("x"), Span(Position(1, 7, 6), Position(1, 8, 7))),
                        Node.Leaf(ColonNode, StringValue(":"), Span(Position(1, 8, 7), Position(1, 9, 8))),
                        Node.Leaf(StringTypeNode, StringValue("string"), Span(Position(1, 10, 9), Position(1, 16, 15))),
                        Node.Leaf(AssignNode, StringValue("="), Span(Position(1, 17, 16), Position(1, 18, 17))),
                        Node.Composite(
                            type = ReadEnvExpressionNode,
                            span = Span(Position(1, 18, 17), Position(1, 27, 26)),
                            children = listOf(
                                Node.Leaf(ReadEnvKeywordNode, StringValue("readEnv"), Span(Position(1, 18, 17), Position(1, 24, 23))),
                                Node.Leaf(LeftParenthesisNode, StringValue("("), Span(Position(1, 24, 23), Position(1, 25, 24))),
                                Node.Leaf(StringLiteralNode, StringValue("INVALID"), Span(Position(1, 25, 24), Position(1, 27, 26))),
                                Node.Leaf(RightParenthesisNode, StringValue(")"), Span(Position(1, 27, 26), Position(1, 28, 27))),
                            ),
                        ),
                        Node.Leaf(SemicolonNode, StringValue(";"), Span(Position(1, 28, 27), Position(1, 29, 28))),
                    ),
                ),
                Node.Composite(
                    type = PrintlnStatementNode,
                    span = Span(Position(2, 1, 29), Position(2, 10, 38)),
                    children = listOf(
                        Node.Leaf(PrintlnKeywordNode, StringValue("println"), Span(Position(2, 1, 29), Position(2, 8, 36))),
                        Node.Leaf(LeftParenthesisNode, StringValue("("), Span(Position(2, 8, 36), Position(2, 9, 37))),
                        Node.Leaf(IdentifierNode, StringValue("x"), Span(Position(2, 9, 37), Position(2, 10, 38))),
                        Node.Leaf(RightParenthesisNode, StringValue(")"), Span(Position(2, 10, 38), Position(2, 11, 39))),
                        Node.Leaf(SemicolonNode, StringValue(";"), Span(Position(2, 11, 39), Position(2, 12, 40))),
                    ),
                ),
            ),
        )

        TestSnippetRegistry.register(
            "readEnv-inside-expression",
            listOf(
                Node.Composite(
                    type = LetDeclarationStatementNode,
                    span = Span(Position(1, 1, 0), Position(2, 10, 30)),
                    children = listOf(
                        Node.Leaf(LetNode, StringValue("let"), Span(Position(1, 1, 0), Position(1, 4, 3))),
                        Node.Leaf(IdentifierNode, StringValue("x"), Span(Position(1, 5, 4), Position(1, 6, 5))),
                        Node.Leaf(ColonNode, StringValue(":"), Span(Position(1, 6, 5), Position(1, 7, 6))),
                        Node.Leaf(NumberTypeNode, StringValue("number"), Span(Position(1, 8, 7), Position(1, 14, 13))),
                        Node.Leaf(AssignNode, StringValue("="), Span(Position(1, 15, 14), Position(1, 16, 15))),

                        Node.Composite(
                            type = BinaryOperationExpressionNode,
                            span = Span(Position(1, 17, 16), Position(1, 32, 31)),
                            children = listOf(
                                Node.Leaf(NumberLiteralNode, IntegerValue(3), Span(Position(1, 17, 16), Position(1, 18, 17))),
                                Node.Leaf(PlusNode, StringValue("+"), Span(Position(1, 19, 18), Position(1, 20, 19))),
                                Node.Composite(
                                    type = ReadEnvExpressionNode,
                                    span = Span(Position(1, 21, 20), Position(1, 32, 31)),
                                    children = listOf(
                                        Node.Leaf(ReadEnvKeywordNode, StringValue("readEnv"), Span(Position(1, 21, 20), Position(1, 28, 27))),
                                        Node.Leaf(LeftParenthesisNode, StringValue("("), Span(Position(1, 28, 27), Position(1, 29, 28))),
                                        Node.Leaf(StringLiteralNode, StringValue("NUMBER"), Span(Position(1, 29, 28), Position(1, 31, 30))),
                                        Node.Leaf(RightParenthesisNode, StringValue(")"), Span(Position(1, 31, 30), Position(1, 32, 31))),
                                    ),
                                ),
                            ),
                        ),
                        Node.Leaf(SemicolonNode, StringValue(";"), Span(Position(1, 32, 31), Position(1, 33, 32))),
                    ),
                ),

                Node.Composite(
                    type = PrintlnStatementNode,
                    span = Span(Position(2, 1, 33), Position(2, 10, 42)),
                    children = listOf(
                        Node.Leaf(PrintlnKeywordNode, StringValue("println"), Span(Position(2, 1, 33), Position(2, 8, 40))),
                        Node.Leaf(LeftParenthesisNode, StringValue("("), Span(Position(2, 8, 40), Position(2, 9, 41))),
                        Node.Leaf(IdentifierNode, StringValue("x"), Span(Position(2, 9, 41), Position(2, 10, 42))),
                        Node.Leaf(RightParenthesisNode, StringValue(")"), Span(Position(2, 10, 42), Position(2, 11, 43))),
                        Node.Leaf(SemicolonNode, StringValue(";"), Span(Position(2, 11, 43), Position(2, 12, 44))),
                    ),
                ),
            ),
        )

        TestSnippetRegistry.register(
            "readEnv-statement",
            listOf(
                Node.Composite(
                    type = LetDeclarationStatementNode,
                    span = Span(Position(1, 1, 0), Position(1, 27, 26)),
                    children = listOf(
                        Node.Leaf(LetNode, StringValue("let"), Span(Position(1, 1, 0), Position(1, 4, 3))),
                        Node.Leaf(IdentifierNode, StringValue("str"), Span(Position(1, 5, 4), Position(1, 8, 7))),
                        Node.Leaf(ColonNode, StringValue(":"), Span(Position(1, 8, 7), Position(1, 9, 8))),
                        Node.Leaf(StringTypeNode, StringValue("string"), Span(Position(1, 10, 9), Position(1, 16, 15))),
                        Node.Leaf(AssignNode, StringValue("="), Span(Position(1, 17, 16), Position(1, 18, 17))),

                        Node.Composite(
                            type = ReadEnvExpressionNode,
                            span = Span(Position(1, 19, 18), Position(1, 27, 26)),
                            children = listOf(
                                Node.Leaf(ReadEnvKeywordNode, StringValue("readEnv"), Span(Position(1, 19, 18), Position(1, 26, 25))),
                                Node.Leaf(LeftParenthesisNode, StringValue("("), Span(Position(1, 26, 25), Position(1, 27, 26))),
                                Node.Leaf(StringLiteralNode, StringValue("STRING"), Span(Position(1, 27, 26), Position(1, 33, 32))),
                                Node.Leaf(RightParenthesisNode, StringValue(")"), Span(Position(1, 33, 32), Position(1, 34, 33))),
                            ),
                        ),
                        Node.Leaf(SemicolonNode, StringValue(";"), Span(Position(1, 34, 33), Position(1, 35, 34))),
                    ),
                ),

                Node.Composite(
                    type = PrintlnStatementNode,
                    span = Span(Position(2, 1, 35), Position(2, 11, 45)),
                    children = listOf(
                        Node.Leaf(PrintlnKeywordNode, StringValue("println"), Span(Position(2, 1, 35), Position(2, 8, 42))),
                        Node.Leaf(LeftParenthesisNode, StringValue("("), Span(Position(2, 8, 42), Position(2, 9, 43))),
                        Node.Leaf(IdentifierNode, StringValue("str"), Span(Position(2, 9, 43), Position(2, 12, 46))),
                        Node.Leaf(RightParenthesisNode, StringValue(")"), Span(Position(2, 12, 46), Position(2, 13, 47))),
                        Node.Leaf(SemicolonNode, StringValue(";"), Span(Position(2, 13, 47), Position(2, 14, 48))),
                    ),
                ),
            ),
        )

        TestSnippetRegistry.register(
            "readInput-inside-expression",
            listOf(
                Node.Composite(
                    type = PrintlnStatementNode,
                    span = Span(Position(1, 1, 0), Position(1, 55, 54)),
                    children = listOf(
                        Node.Leaf(PrintlnKeywordNode, StringValue("println"), Span(Position(1, 1, 0), Position(1, 8, 7))),
                        Node.Leaf(LeftParenthesisNode, StringValue("("), Span(Position(1, 8, 7), Position(1, 9, 8))),

                        Node.Composite(
                            type = BinaryOperationExpressionNode,
                            span = Span(Position(1, 9, 8), Position(1, 54, 53)),
                            children = listOf(
                                Node.Leaf(StringLiteralNode, StringValue("Hello, "), Span(Position(1, 9, 8), Position(1, 18, 17))),
                                Node.Leaf(PlusNode, StringValue("+"), Span(Position(1, 19, 18), Position(1, 20, 19))),

                                Node.Composite(
                                    type = ReadInputExpressionNode,
                                    span = Span(Position(1, 21, 20), Position(1, 54, 53)),
                                    children = listOf(
                                        Node.Leaf(ReadInputKeywordNode, StringValue("readInput"), Span(Position(1, 21, 20), Position(1, 30, 29))),
                                        Node.Leaf(LeftParenthesisNode, StringValue("("), Span(Position(1, 30, 29), Position(1, 31, 30))),
                                        Node.Leaf(StringLiteralNode, StringValue("Enter your name:"), Span(Position(1, 31, 30), Position(1, 52, 51))),
                                        Node.Leaf(RightParenthesisNode, StringValue(")"), Span(Position(1, 52, 51), Position(1, 53, 52))),
                                    ),
                                ),
                            ),
                        ),

                        Node.Leaf(RightParenthesisNode, StringValue(")"), Span(Position(1, 54, 53), Position(1, 55, 54))),
                        Node.Leaf(SemicolonNode, StringValue(";"), Span(Position(1, 55, 54), Position(1, 56, 55))),
                    ),
                ),
            ),
        )

        TestSnippetRegistry.register(
            "readInput-statement",
            listOf(
                Node.Composite(
                    type = LetDeclarationStatementNode,
                    span = Span(Position(1, 1, 0), Position(1, 26, 25)),
                    children = listOf(
                        Node.Leaf(LetNode, StringValue("let"), Span(Position(1, 1, 0), Position(1, 4, 3))),
                        Node.Leaf(IdentifierNode, StringValue("city"), Span(Position(1, 5, 4), Position(1, 9, 8))),
                        Node.Leaf(ColonNode, StringValue(":"), Span(Position(1, 9, 8), Position(1, 10, 9))),
                        Node.Leaf(StringTypeNode, StringValue("string"), Span(Position(1, 11, 10), Position(1, 17, 16))),
                        Node.Leaf(AssignNode, StringValue("="), Span(Position(1, 18, 17), Position(1, 19, 18))),
                        Node.Composite(
                            type = ReadInputExpressionNode,
                            span = Span(Position(1, 20, 19), Position(1, 26, 25)),
                            children = listOf(
                                Node.Leaf(ReadInputKeywordNode, StringValue("readInput"), Span(Position(1, 20, 19), Position(1, 29, 28))),
                                Node.Leaf(LeftParenthesisNode, StringValue("("), Span(Position(1, 29, 28), Position(1, 30, 29))),
                                Node.Leaf(StringLiteralNode, StringValue("City:"), Span(Position(1, 30, 29), Position(1, 37, 36))),
                                Node.Leaf(RightParenthesisNode, StringValue(")"), Span(Position(1, 37, 36), Position(1, 38, 37))),
                            ),
                        ),
                        Node.Leaf(SemicolonNode, StringValue(";"), Span(Position(1, 38, 37), Position(1, 39, 38))),
                    ),
                ),
                Node.Composite(
                    type = PrintlnStatementNode,
                    span = Span(Position(2, 1, 40), Position(2, 14, 53)),
                    children = listOf(
                        Node.Leaf(PrintlnKeywordNode, StringValue("println"), Span(Position(2, 1, 40), Position(2, 8, 47))),
                        Node.Leaf(LeftParenthesisNode, StringValue("("), Span(Position(2, 8, 47), Position(2, 9, 48))),
                        Node.Leaf(IdentifierNode, StringValue("city"), Span(Position(2, 9, 48), Position(2, 13, 52))),
                        Node.Leaf(RightParenthesisNode, StringValue(")"), Span(Position(2, 13, 52), Position(2, 14, 53))),
                        Node.Leaf(SemicolonNode, StringValue(";"), Span(Position(2, 14, 53), Position(2, 15, 54))),
                    ),
                ),
            ),
        )
    }
}
