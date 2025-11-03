package util

import model.node.AssignNode
import model.node.AssignStatementNode
import model.node.BinaryOperationExpressionNode
import model.node.ColonNode
import model.node.DivideNode
import model.node.IdentifierNode
import model.node.LeftParenthesisNode
import model.node.LetDeclarationStatementNode
import model.node.LetNode
import model.node.MinusNode
import model.node.MultiplyNode
import model.node.Node
import model.node.NumberLiteralNode
import model.node.NumberTypeNode
import model.node.ParenthesizedExpressionNode
import model.node.PlusNode
import model.node.PrintlnKeywordNode
import model.node.PrintlnStatementNode
import model.node.RightParenthesisNode
import model.node.SemicolonNode
import model.node.StringLiteralNode
import model.node.StringTypeNode
import model.node.UnaryOperationNode
import model.span.Position
import model.span.Span
import model.value.FloatValue
import model.value.IntegerValue
import model.value.StringValue

internal object PrintScriptV10TestSnippets {

    init {

        TestSnippetRegistry.register(
            "basic-assignment",
            listOf(
                Node.Composite(
                    type = LetDeclarationStatementNode,
                    span = Span(Position(1, 1, 0), Position(1, 15, 14)),
                    children = listOf(
                        Node.Leaf(LetNode, StringValue("let"), Span(Position(1, 1, 0), Position(1, 4, 3))),
                        Node.Leaf(IdentifierNode, StringValue("x"), Span(Position(1, 5, 4), Position(1, 6, 5))),
                        Node.Leaf(ColonNode, StringValue(":"), Span(Position(1, 6, 5), Position(1, 7, 6))),
                        Node.Leaf(NumberTypeNode, StringValue("number"), Span(Position(1, 8, 7), Position(1, 14, 13))),
                        Node.Leaf(AssignNode, StringValue("="), Span(Position(1, 15, 14), Position(1, 16, 15))),
                        Node.Leaf(NumberLiteralNode, IntegerValue(5), Span(Position(1, 17, 16), Position(1, 18, 17))),
                        Node.Leaf(SemicolonNode, StringValue(";"), Span(Position(1, 18, 17), Position(1, 19, 18))),
                    ),
                ),
                Node.Composite(
                    type = AssignStatementNode,
                    span = Span(Position(2, 1, 19), Position(2, 8, 26)),
                    children = listOf(
                        Node.Leaf(IdentifierNode, StringValue("x"), Span(Position(2, 1, 19), Position(2, 2, 20))),
                        Node.Leaf(AssignNode, StringValue("="), Span(Position(2, 3, 21), Position(2, 4, 22))),
                        Node.Leaf(NumberLiteralNode, IntegerValue(10), Span(Position(2, 5, 23), Position(2, 7, 25))),
                        Node.Leaf(SemicolonNode, StringValue(";"), Span(Position(2, 7, 25), Position(2, 8, 26))),
                    ),
                ),
                Node.Composite(
                    type = AssignStatementNode,
                    span = Span(Position(3, 1, 27), Position(3, 11, 37)),
                    children = listOf(
                        Node.Leaf(IdentifierNode, StringValue("x"), Span(Position(3, 1, 27), Position(3, 2, 28))),
                        Node.Leaf(AssignNode, StringValue("="), Span(Position(3, 3, 29), Position(3, 4, 30))),
                        Node.Composite(
                            type = BinaryOperationExpressionNode,
                            span = Span(Position(3, 5, 31), Position(3, 11, 37)),
                            children = listOf(
                                Node.Leaf(IdentifierNode, StringValue("x"), Span(Position(3, 5, 31), Position(3, 6, 32))),
                                Node.Leaf(PlusNode, StringValue("+"), Span(Position(3, 7, 33), Position(3, 8, 34))),
                                Node.Leaf(NumberLiteralNode, IntegerValue(5), Span(Position(3, 9, 35), Position(3, 11, 37))),
                            ),
                        ),
                        Node.Leaf(SemicolonNode, StringValue(";"), Span(Position(3, 11, 37), Position(3, 12, 38))),
                    ),
                ),
                Node.Composite(
                    type = PrintlnStatementNode,
                    span = Span(Position(4, 1, 39), Position(4, 9, 47)),
                    children = listOf(
                        Node.Leaf(PrintlnKeywordNode, StringValue("println"), Span(Position(4, 1, 39), Position(4, 8, 46))),
                        Node.Leaf(LeftParenthesisNode, StringValue("("), Span(Position(4, 8, 46), Position(4, 9, 47))),
                        Node.Leaf(IdentifierNode, StringValue("x"), Span(Position(4, 9, 47), Position(4, 10, 48))),
                        Node.Leaf(RightParenthesisNode, StringValue(")"), Span(Position(4, 10, 48), Position(4, 11, 49))),
                        Node.Leaf(SemicolonNode, StringValue(";"), Span(Position(4, 11, 49), Position(4, 12, 50))),
                    ),
                ),
            ),
        )

        TestSnippetRegistry.register(
            "basic-binary-operation",
            listOf(
                Node.Composite(
                    type = LetDeclarationStatementNode,
                    span = Span(Position(1, 1, 0), Position(1, 27, 26)),
                    children = listOf(
                        Node.Leaf(LetNode, StringValue("let"), Span(Position(1, 1, 0), Position(1, 4, 3))),
                        Node.Leaf(IdentifierNode, StringValue("x"), Span(Position(1, 5, 4), Position(1, 6, 5))),
                        Node.Leaf(ColonNode, StringValue(":"), Span(Position(1, 6, 5), Position(1, 7, 6))),
                        Node.Leaf(NumberTypeNode, StringValue("number"), Span(Position(1, 8, 7), Position(1, 14, 13))),
                        Node.Leaf(AssignNode, StringValue("="), Span(Position(1, 15, 14), Position(1, 16, 15))),
                        Node.Composite(
                            type = BinaryOperationExpressionNode,
                            span = Span(Position(1, 16, 15), Position(1, 25, 24)),
                            children = listOf(
                                Node.Composite(
                                    type = ParenthesizedExpressionNode,
                                    span = Span(Position(1, 16, 15), Position(1, 23, 22)),
                                    children = listOf(
                                        Node.Leaf(LeftParenthesisNode, StringValue("("), Span(Position(1, 16, 15), Position(1, 17, 16))),
                                        Node.Composite(
                                            type = BinaryOperationExpressionNode,
                                            span = Span(Position(1, 17, 16), Position(1, 22, 21)),
                                            children = listOf(
                                                Node.Leaf(NumberLiteralNode, IntegerValue(5), Span(Position(1, 17, 16), Position(1, 18, 17))),
                                                Node.Leaf(MultiplyNode, StringValue("*"), Span(Position(1, 18, 17), Position(1, 19, 18))),
                                                Node.Leaf(NumberLiteralNode, FloatValue(3.2f), Span(Position(1, 19, 18), Position(1, 22, 21))),
                                            ),
                                        ),
                                        Node.Leaf(RightParenthesisNode, StringValue(")"), Span(Position(1, 22, 21), Position(1, 23, 22))),
                                    ),
                                ),
                                Node.Leaf(MinusNode, StringValue("-"), Span(Position(1, 23, 22), Position(1, 24, 23))),
                                Node.Leaf(NumberLiteralNode, IntegerValue(30), Span(Position(1, 24, 23), Position(1, 25, 24))),
                            ),
                        ),
                        Node.Leaf(SemicolonNode, StringValue(";"), Span(Position(1, 25, 24), Position(1, 27, 26))),
                    ),
                ),
                Node.Composite(
                    type = PrintlnStatementNode,
                    span = Span(Position(2, 1, 27), Position(2, 12, 38)),
                    children = listOf(
                        Node.Leaf(PrintlnKeywordNode, StringValue("println"), Span(Position(2, 1, 27), Position(2, 8, 34))),
                        Node.Leaf(LeftParenthesisNode, StringValue("("), Span(Position(2, 8, 34), Position(2, 9, 35))),
                        Node.Leaf(IdentifierNode, StringValue("x"), Span(Position(2, 9, 35), Position(2, 10, 36))),
                        Node.Leaf(RightParenthesisNode, StringValue(")"), Span(Position(2, 10, 36), Position(2, 11, 37))),
                        Node.Leaf(SemicolonNode, StringValue(";"), Span(Position(2, 11, 37), Position(2, 12, 38))),
                    ),
                ),
            ),
        )

        TestSnippetRegistry.register(
            "basic-unary-operation",
            listOf(
                Node.Composite(
                    type = LetDeclarationStatementNode,
                    span = Span(Position(1, 1, 0), Position(1, 16, 15)),
                    children = listOf(
                        Node.Leaf(LetNode, StringValue("let"), Span(Position(1, 1, 0), Position(1, 4, 3))),
                        Node.Leaf(IdentifierNode, StringValue("x"), Span(Position(1, 5, 4), Position(1, 6, 5))),
                        Node.Leaf(ColonNode, StringValue(":"), Span(Position(1, 6, 5), Position(1, 7, 6))),
                        Node.Leaf(NumberTypeNode, StringValue("number"), Span(Position(1, 8, 7), Position(1, 14, 13))),
                        Node.Leaf(AssignNode, StringValue("="), Span(Position(1, 15, 14), Position(1, 16, 15))),
                        Node.Leaf(NumberLiteralNode, FloatValue(8.67f), Span(Position(1, 16, 15), Position(1, 20, 19))),
                        Node.Leaf(SemicolonNode, StringValue(";"), Span(Position(1, 20, 19), Position(1, 21, 20))),
                    ),
                ),
                Node.Composite(
                    type = PrintlnStatementNode,
                    span = Span(Position(2, 1, 21), Position(2, 9, 29)),
                    children = listOf(
                        Node.Leaf(PrintlnKeywordNode, StringValue("println"), Span(Position(2, 1, 21), Position(2, 8, 28))),
                        Node.Leaf(LeftParenthesisNode, StringValue("("), Span(Position(2, 8, 28), Position(2, 9, 29))),
                        Node.Composite(
                            type = UnaryOperationNode,
                            span = Span(Position(2, 9, 29), Position(2, 11, 31)),
                            children = listOf(
                                Node.Leaf(MinusNode, StringValue("-"), Span(Position(2, 9, 29), Position(2, 10, 30))),
                                Node.Leaf(IdentifierNode, StringValue("x"), Span(Position(2, 10, 30), Position(2, 11, 31))),
                            ),
                        ),
                        Node.Leaf(RightParenthesisNode, StringValue(")"), Span(Position(2, 11, 31), Position(2, 12, 32))),
                        Node.Leaf(SemicolonNode, StringValue(";"), Span(Position(2, 12, 32), Position(2, 13, 33))),
                    ),
                ),
                Node.Composite(
                    type = PrintlnStatementNode,
                    span = Span(Position(3, 1, 33), Position(3, 9, 41)),
                    children = listOf(
                        Node.Leaf(PrintlnKeywordNode, StringValue("println"), Span(Position(3, 1, 33), Position(3, 8, 40))),
                        Node.Leaf(LeftParenthesisNode, StringValue("("), Span(Position(3, 8, 40), Position(3, 9, 41))),
                        Node.Composite(
                            type = UnaryOperationNode,
                            span = Span(Position(3, 9, 41), Position(3, 11, 43)),
                            children = listOf(
                                Node.Leaf(PlusNode, StringValue("+"), Span(Position(3, 9, 41), Position(3, 10, 42))),
                                Node.Leaf(IdentifierNode, StringValue("x"), Span(Position(3, 10, 42), Position(3, 11, 43))),
                            ),
                        ),
                        Node.Leaf(RightParenthesisNode, StringValue(")"), Span(Position(3, 11, 43), Position(3, 12, 44))),
                        Node.Leaf(SemicolonNode, StringValue(";"), Span(Position(3, 12, 44), Position(3, 13, 45))),
                    ),
                ),
            ),
        )

        TestSnippetRegistry.register(
            "empty-println",
            listOf(
                Node.Composite(
                    type = PrintlnStatementNode,
                    span = Span(Position(1, 1, 0), Position(1, 10, 9)),
                    children = listOf(
                        Node.Leaf(PrintlnKeywordNode, StringValue("println"), Span(Position(1, 1, 0), Position(1, 8, 7))),
                        Node.Leaf(LeftParenthesisNode, StringValue("("), Span(Position(1, 8, 7), Position(1, 9, 8))),
                        Node.Leaf(RightParenthesisNode, StringValue(")"), Span(Position(1, 9, 8), Position(1, 10, 9))),
                        Node.Leaf(SemicolonNode, StringValue(";"), Span(Position(1, 10, 9), Position(1, 11, 10))),
                    ),
                ),
            ),
        )

        TestSnippetRegistry.register(
            "invalid-binary-operation",
            listOf(
                Node.Composite(
                    type = LetDeclarationStatementNode,
                    span = Span(Position(1, 1, 0), Position(1, 30, 29)),
                    children = listOf(
                        Node.Leaf(LetNode, StringValue("let"), Span(Position(1, 1, 0), Position(1, 4, 3))),
                        Node.Leaf(IdentifierNode, StringValue("res"), Span(Position(1, 5, 4), Position(1, 8, 7))),
                        Node.Leaf(ColonNode, StringValue(":"), Span(Position(1, 8, 7), Position(1, 9, 8))),
                        Node.Leaf(StringTypeNode, StringValue("string"), Span(Position(1, 10, 9), Position(1, 16, 15))),
                        Node.Leaf(AssignNode, StringValue("="), Span(Position(1, 17, 16), Position(1, 18, 17))),
                        Node.Composite(
                            type = BinaryOperationExpressionNode,
                            span = Span(Position(1, 19, 18), Position(1, 29, 28)),
                            children = listOf(
                                Node.Leaf(StringLiteralNode, StringValue("Hello"), Span(Position(1, 19, 18), Position(1, 26, 25))),
                                Node.Leaf(DivideNode, StringValue("/"), Span(Position(1, 27, 26), Position(1, 28, 27))),
                                Node.Leaf(StringLiteralNode, StringValue("World"), Span(Position(1, 29, 28), Position(1, 34, 33))),
                            ),
                        ),
                        Node.Leaf(SemicolonNode, StringValue(";"), Span(Position(1, 34, 33), Position(1, 35, 34))),
                    ),
                ),
            ),
        )

        TestSnippetRegistry.register(
            "invalid-unary-operation",
            listOf(
                Node.Composite(
                    type = PrintlnStatementNode,
                    span = Span(Position(1, 1, 0), Position(1, 16, 15)),
                    children = listOf(
                        Node.Leaf(PrintlnKeywordNode, StringValue("println"), Span(Position(1, 1, 0), Position(1, 8, 7))),
                        Node.Leaf(LeftParenthesisNode, StringValue("("), Span(Position(1, 8, 7), Position(1, 9, 8))),
                        Node.Composite(
                            type = UnaryOperationNode,
                            span = Span(Position(1, 9, 8), Position(1, 15, 14)),
                            children = listOf(
                                Node.Leaf(MinusNode, StringValue("-"), Span(Position(1, 9, 8), Position(1, 10, 9))),
                                Node.Leaf(StringLiteralNode, StringValue("Hello"), Span(Position(1, 10, 9), Position(1, 15, 14))),
                            ),
                        ),
                        Node.Leaf(RightParenthesisNode, StringValue(")"), Span(Position(1, 15, 14), Position(1, 16, 15))),
                        Node.Leaf(SemicolonNode, StringValue(";"), Span(Position(1, 16, 15), Position(1, 17, 16))),
                    ),
                ),
            ),
        )

        TestSnippetRegistry.register(
            "let-invalid-assignment",
            listOf(
                Node.Composite(
                    type = LetDeclarationStatementNode,
                    span = Span(Position(1, 1, 0), Position(1, 25, 24)),
                    children = listOf(
                        Node.Leaf(LetNode, StringValue("let"), Span(Position(1, 1, 0), Position(1, 4, 3))),
                        Node.Leaf(IdentifierNode, StringValue("x"), Span(Position(1, 5, 4), Position(1, 6, 5))),
                        Node.Leaf(ColonNode, StringValue(":"), Span(Position(1, 6, 5), Position(1, 7, 6))),
                        Node.Leaf(NumberTypeNode, StringValue("number"), Span(Position(1, 8, 7), Position(1, 14, 13))),
                        Node.Leaf(AssignNode, StringValue("="), Span(Position(1, 15, 14), Position(1, 16, 15))),
                        Node.Leaf(StringLiteralNode, StringValue("Hello, World!"), Span(Position(1, 17, 16), Position(1, 25, 24))),
                        Node.Leaf(SemicolonNode, StringValue(";"), Span(Position(1, 25, 24), Position(1, 26, 25))),
                    ),
                ),
            ),
        )

        TestSnippetRegistry.register(
            "none-value",
            listOf(
                Node.Composite(
                    type = LetDeclarationStatementNode,
                    span = Span(Position(1, 1, 0), Position(1, 14, 13)),
                    children = listOf(
                        Node.Leaf(LetNode, StringValue("let"), Span(Position(1, 1, 0), Position(1, 4, 3))),
                        Node.Leaf(IdentifierNode, StringValue("x"), Span(Position(1, 5, 4), Position(1, 6, 5))),
                        Node.Leaf(ColonNode, StringValue(":"), Span(Position(1, 6, 5), Position(1, 7, 6))),
                        Node.Leaf(NumberTypeNode, StringValue("number"), Span(Position(1, 8, 7), Position(1, 14, 13))),
                        Node.Leaf(SemicolonNode, StringValue(";"), Span(Position(1, 14, 13), Position(1, 15, 14))),
                    ),
                ),
                Node.Composite(
                    type = PrintlnStatementNode,
                    span = Span(Position(2, 1, 15), Position(2, 9, 23)),
                    children = listOf(
                        Node.Leaf(PrintlnKeywordNode, StringValue("println"), Span(Position(2, 1, 15), Position(2, 8, 22))),
                        Node.Leaf(LeftParenthesisNode, StringValue("("), Span(Position(2, 8, 22), Position(2, 9, 23))),
                        Node.Leaf(IdentifierNode, StringValue("x"), Span(Position(2, 9, 23), Position(2, 10, 24))),
                        Node.Leaf(RightParenthesisNode, StringValue(")"), Span(Position(2, 10, 24), Position(2, 11, 25))),
                        Node.Leaf(SemicolonNode, StringValue(";"), Span(Position(2, 11, 25), Position(2, 12, 26))),
                    ),
                ),
            ),
        )

        TestSnippetRegistry.register(
            "println-statement",
            listOf(
                Node.Composite(
                    type = PrintlnStatementNode,
                    span = Span(Position(1, 1, 0), Position(1, 21, 20)),
                    children = listOf(
                        Node.Leaf(PrintlnKeywordNode, StringValue("println"), Span(Position(1, 1, 0), Position(1, 8, 7))),
                        Node.Leaf(LeftParenthesisNode, StringValue("("), Span(Position(1, 8, 7), Position(1, 9, 8))),
                        Node.Leaf(StringLiteralNode, StringValue("Hello, World!"), Span(Position(1, 9, 8), Position(1, 22, 21))),
                        Node.Leaf(RightParenthesisNode, StringValue(")"), Span(Position(1, 22, 21), Position(1, 23, 22))),
                        Node.Leaf(SemicolonNode, StringValue(";"), Span(Position(1, 23, 22), Position(1, 24, 23))),
                    ),
                ),
            ),
        )

        TestSnippetRegistry.register(
            "string-coercion",
            listOf(
                Node.Composite(
                    type = LetDeclarationStatementNode,
                    span = Span(Position(1, 1, 0), Position(1, 20, 19)),
                    children = listOf(
                        Node.Leaf(LetNode, StringValue("let"), Span(Position(1, 1, 0), Position(1, 4, 3))),
                        Node.Leaf(IdentifierNode, StringValue("x"), Span(Position(1, 5, 4), Position(1, 6, 5))),
                        Node.Leaf(ColonNode, StringValue(":"), Span(Position(1, 6, 5), Position(1, 7, 6))),
                        Node.Leaf(StringTypeNode, StringValue("string"), Span(Position(1, 8, 7), Position(1, 14, 13))),
                        Node.Leaf(AssignNode, StringValue("="), Span(Position(1, 15, 14), Position(1, 16, 15))),
                        Node.Composite(
                            type = BinaryOperationExpressionNode,
                            span = Span(Position(1, 17, 16), Position(1, 20, 19)),
                            children = listOf(
                                Node.Leaf(StringLiteralNode, StringValue("Number: "), Span(Position(1, 17, 16), Position(1, 27, 26))),
                                Node.Leaf(PlusNode, StringValue("+"), Span(Position(1, 28, 27), Position(1, 29, 28))),
                                Node.Leaf(NumberLiteralNode, IntegerValue(5), Span(Position(1, 30, 29), Position(1, 31, 30))),
                            ),
                        ),
                        Node.Leaf(SemicolonNode, StringValue(";"), Span(Position(1, 31, 30), Position(1, 32, 31))),
                    ),
                ),
                Node.Composite(
                    type = PrintlnStatementNode,
                    span = Span(Position(2, 1, 32), Position(2, 8, 39)),
                    children = listOf(
                        Node.Leaf(PrintlnKeywordNode, StringValue("println"), Span(Position(2, 1, 32), Position(2, 8, 39))),
                        Node.Leaf(LeftParenthesisNode, StringValue("("), Span(Position(2, 8, 39), Position(2, 9, 40))),
                        Node.Leaf(IdentifierNode, StringValue("x"), Span(Position(2, 9, 40), Position(2, 10, 41))),
                        Node.Leaf(RightParenthesisNode, StringValue(")"), Span(Position(2, 10, 41), Position(2, 11, 42))),
                        Node.Leaf(SemicolonNode, StringValue(";"), Span(Position(2, 11, 42), Position(2, 12, 43))),
                    ),
                ),
            ),
        )

        TestSnippetRegistry.register(
            "undefined-identifier",
            listOf(
                Node.Composite(
                    type = PrintlnStatementNode,
                    span = Span(Position(1, 1, 0), Position(1, 10, 9)),
                    children = listOf(
                        Node.Leaf(PrintlnKeywordNode, StringValue("println"), Span(Position(1, 1, 0), Position(1, 8, 7))),
                        Node.Leaf(LeftParenthesisNode, StringValue("("), Span(Position(1, 8, 7), Position(1, 9, 8))),
                        Node.Leaf(IdentifierNode, StringValue("x"), Span(Position(1, 9, 8), Position(1, 10, 9))),
                        Node.Leaf(RightParenthesisNode, StringValue(")"), Span(Position(1, 10, 9), Position(1, 11, 10))),
                        Node.Leaf(SemicolonNode, StringValue(";"), Span(Position(1, 11, 10), Position(1, 12, 11))),
                    ),
                ),
            ),
        )
    }
}
