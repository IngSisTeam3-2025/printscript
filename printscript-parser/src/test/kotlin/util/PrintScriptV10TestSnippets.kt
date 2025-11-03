package util

import model.span.Position
import model.span.Span
import model.token.AssignToken
import model.token.ColonToken
import model.token.IdentifierToken
import model.token.LeftParenthesisToken
import model.token.LetToken
import model.token.MinusToken
import model.token.MultiplyToken
import model.token.NumberLiteralToken
import model.token.NumberTypeToken
import model.token.PlusToken
import model.token.PrintlnToken
import model.token.ReadInputToken
import model.token.RightParenthesisToken
import model.token.SemicolonToken
import model.token.StringLiteralToken
import model.token.StringTypeToken
import model.token.Token

internal object PrintScriptV10TestSnippets {

    init {
        TestSnippetRegistry.register(
            "empty-println-statement",
            listOf(
                Token(PrintlnToken, "println", Span(Position(1, 1, 0), Position(1, 8, 7))),
                Token(LeftParenthesisToken, "(", Span(Position(1, 8, 7), Position(1, 9, 8))),
                Token(RightParenthesisToken, ")", Span(Position(1, 9, 8), Position(1, 10, 9))),
                Token(SemicolonToken, ";", Span(Position(1, 10, 9), Position(1, 11, 10))),
            ),
        )

        TestSnippetRegistry.register(
            "expression-as-statement",
            listOf(
                Token(ReadInputToken, "readInput", Span(Position(1, 1, 0), Position(1, 9, 8))),
                Token(LeftParenthesisToken, "(", Span(Position(1, 9, 8), Position(1, 10, 9))),
                Token(StringLiteralToken, "\"Enter a number: \"", Span(Position(1, 10, 9), Position(1, 29, 28))),
                Token(RightParenthesisToken, ")", Span(Position(1, 29, 28), Position(1, 30, 29))),
                Token(SemicolonToken, ";", Span(Position(1, 30, 29), Position(1, 31, 30))),
            ),
        )

        TestSnippetRegistry.register(
            "identifier-and-number-binary-expression",
            listOf(
                Token(LetToken, "let", Span(Position(1, 1, 0), Position(1, 4, 3))),
                Token(IdentifierToken, "x", Span(Position(1, 5, 4), Position(1, 6, 5))),
                Token(ColonToken, ":", Span(Position(1, 6, 5), Position(1, 7, 6))),
                Token(NumberTypeToken, "number", Span(Position(1, 8, 7), Position(1, 14, 13))),
                Token(AssignToken, "=", Span(Position(1, 15, 14), Position(1, 16, 15))),
                Token(LeftParenthesisToken, "(", Span(Position(1, 17, 16), Position(1, 18, 17))),
                Token(IdentifierToken, "x", Span(Position(1, 18, 17), Position(1, 19, 18))),
                Token(MultiplyToken, "*", Span(Position(1, 19, 18), Position(1, 20, 19))),
                Token(NumberLiteralToken, "5", Span(Position(1, 21, 20), Position(1, 22, 21))),
                Token(RightParenthesisToken, ")", Span(Position(1, 22, 21), Position(1, 23, 22))),
                Token(MinusToken, "-", Span(Position(1, 24, 23), Position(1, 25, 24))),
                Token(IdentifierToken, "y", Span(Position(1, 26, 25), Position(1, 27, 26))),
                Token(SemicolonToken, ";", Span(Position(1, 28, 27), Position(1, 29, 28))),
            ),
        )

        TestSnippetRegistry.register(
            "identifier-and-string-binary-expression",
            listOf(
                Token(LetToken, "let", Span(Position(1, 1, 0), Position(1, 4, 3))),
                Token(IdentifierToken, "messageEnd", Span(Position(1, 5, 4), Position(1, 15, 14))),
                Token(ColonToken, ":", Span(Position(1, 15, 14), Position(1, 16, 15))),
                Token(StringTypeToken, "string", Span(Position(1, 17, 16), Position(1, 23, 22))),
                Token(AssignToken, "=", Span(Position(1, 24, 23), Position(1, 25, 24))),
                Token(StringLiteralToken, "\" World!\"", Span(Position(1, 26, 25), Position(1, 35, 34))),
                Token(SemicolonToken, ";", Span(Position(1, 35, 34), Position(1, 36, 35))),

                Token(LetToken, "let", Span(Position(2, 1, 36), Position(2, 4, 39))),
                Token(IdentifierToken, "message", Span(Position(2, 5, 40), Position(2, 12, 47))),
                Token(ColonToken, ":", Span(Position(2, 12, 47), Position(2, 13, 48))),
                Token(StringTypeToken, "string", Span(Position(2, 14, 49), Position(2, 20, 55))),
                Token(AssignToken, "=", Span(Position(2, 21, 56), Position(2, 22, 57))),
                Token(StringLiteralToken, "\"Hello,\"", Span(Position(2, 23, 58), Position(2, 31, 66))),
                Token(PlusToken, "+", Span(Position(2, 32, 67), Position(2, 33, 68))),
                Token(IdentifierToken, "messageEnd", Span(Position(2, 34, 69), Position(2, 45, 80))),
                Token(SemicolonToken, ";", Span(Position(2, 46, 81), Position(2, 47, 82))),
            ),
        )

        TestSnippetRegistry.register(
            "let-assignation-statement",
            listOf(
                Token(LetToken, "let", Span(Position(1, 1, 0), Position(1, 4, 3))),
                Token(IdentifierToken, "x", Span(Position(1, 5, 4), Position(1, 6, 5))),
                Token(ColonToken, ":", Span(Position(1, 6, 5), Position(1, 7, 6))),
                Token(NumberTypeToken, "number", Span(Position(1, 8, 7), Position(1, 14, 13))),
                Token(SemicolonToken, ";", Span(Position(1, 14, 13), Position(1, 15, 14))),

                Token(IdentifierToken, "x", Span(Position(2, 1, 15), Position(2, 2, 16))),
                Token(AssignToken, "=", Span(Position(2, 3, 17), Position(2, 4, 18))),
                Token(NumberLiteralToken, "5", Span(Position(2, 5, 19), Position(2, 6, 20))),
                Token(SemicolonToken, ";", Span(Position(2, 6, 20), Position(2, 7, 21))),

                Token(LetToken, "let", Span(Position(3, 1, 22), Position(3, 4, 25))),
                Token(IdentifierToken, "y", Span(Position(3, 5, 26), Position(3, 6, 27))),
                Token(ColonToken, ":", Span(Position(3, 6, 27), Position(3, 7, 28))),
                Token(StringTypeToken, "string", Span(Position(3, 8, 29), Position(3, 14, 35))),
                Token(AssignToken, "=", Span(Position(3, 15, 36), Position(3, 16, 37))),
                Token(StringLiteralToken, "\"Hello\"", Span(Position(3, 17, 38), Position(3, 24, 45))),
                Token(SemicolonToken, ";", Span(Position(3, 24, 45), Position(3, 25, 46))),

                Token(IdentifierToken, "y", Span(Position(4, 1, 47), Position(4, 2, 48))),
                Token(AssignToken, "=", Span(Position(4, 3, 49), Position(4, 4, 50))),
                Token(StringLiteralToken, "\"Goodbye\"", Span(Position(4, 5, 51), Position(4, 14, 60))),
                Token(SemicolonToken, ";", Span(Position(4, 14, 60), Position(4, 15, 61))),
            ),
        )

        TestSnippetRegistry.register(
            "let-declaration-statement",
            listOf(
                Token(LetToken, "let", Span(Position(1, 1, 0), Position(1, 4, 3))),
                Token(IdentifierToken, "x", Span(Position(1, 5, 4), Position(1, 6, 5))),
                Token(ColonToken, ":", Span(Position(1, 6, 5), Position(1, 7, 6))),
                Token(NumberTypeToken, "number", Span(Position(1, 8, 7), Position(1, 14, 13))),
                Token(SemicolonToken, ";", Span(Position(1, 14, 13), Position(1, 15, 14))),

                Token(LetToken, "let", Span(Position(2, 1, 15), Position(2, 4, 18))),
                Token(IdentifierToken, "y", Span(Position(2, 5, 19), Position(2, 6, 20))),
                Token(ColonToken, ":", Span(Position(2, 6, 20), Position(2, 7, 21))),
            ),
        )

        TestSnippetRegistry.register(
            "missing-end-of-statement",
            listOf(
                Token(PrintlnToken, "println", Span(Position(1, 1, 0), Position(1, 7, 6))),
                Token(LeftParenthesisToken, "(", Span(Position(1, 7, 6), Position(1, 8, 7))),
                Token(StringLiteralToken, "\"Hello, World!\"", Span(Position(1, 8, 7), Position(1, 26, 25))),
                Token(RightParenthesisToken, ")", Span(Position(1, 26, 25), Position(1, 27, 26))),
            ),
        )

        TestSnippetRegistry.register(
            "println-statement",
            listOf(
                Token(PrintlnToken, "println", Span(Position(1, 1, 0), Position(1, 7, 6))),
                Token(LeftParenthesisToken, "(", Span(Position(1, 7, 6), Position(1, 8, 7))),
                Token(StringLiteralToken, "\"Hello, World!\"", Span(Position(1, 8, 7), Position(1, 26, 25))),
                Token(RightParenthesisToken, ")", Span(Position(1, 26, 25), Position(1, 27, 26))),
                Token(SemicolonToken, ";", Span(Position(1, 27, 26), Position(1, 28, 27))),
            ),
        )

        TestSnippetRegistry.register(
            "println-with-binary-operation",
            listOf(
                Token(PrintlnToken, "println", Span(Position(1, 1, 0), Position(1, 7, 6))),
                Token(LeftParenthesisToken, "(", Span(Position(1, 7, 6), Position(1, 8, 7))),
                Token(NumberLiteralToken, "5", Span(Position(1, 8, 7), Position(1, 9, 8))),
                Token(MultiplyToken, "*", Span(Position(1, 9, 8), Position(1, 10, 9))),
                Token(LeftParenthesisToken, "(", Span(Position(1, 10, 9), Position(1, 11, 10))),
                Token(NumberLiteralToken, "8", Span(Position(1, 11, 10), Position(1, 12, 11))),
                Token(MinusToken, "-", Span(Position(1, 12, 11), Position(1, 13, 12))),
                Token(NumberLiteralToken, "3", Span(Position(1, 13, 12), Position(1, 14, 13))),
                Token(RightParenthesisToken, ")", Span(Position(1, 14, 13), Position(1, 15, 14))),
                Token(PlusToken, "+", Span(Position(1, 15, 14), Position(1, 16, 15))),
                Token(NumberLiteralToken, "4", Span(Position(1, 16, 15), Position(1, 17, 16))),
                Token(RightParenthesisToken, ")", Span(Position(1, 17, 16), Position(1, 18, 17))),
                Token(SemicolonToken, ";", Span(Position(1, 18, 17), Position(1, 19, 18))),
            ),
        )

        TestSnippetRegistry.register(
            "string-and-number-binary-expression",
            listOf(
                Token(LetToken, "let", Span(Position(1, 1, 0), Position(1, 4, 3))),
                Token(IdentifierToken, "x", Span(Position(1, 5, 4), Position(1, 6, 5))),
                Token(ColonToken, ":", Span(Position(1, 6, 5), Position(1, 7, 6))),
                Token(StringTypeToken, "string", Span(Position(1, 8, 7), Position(1, 14, 13))),
                Token(AssignToken, "=", Span(Position(1, 15, 14), Position(1, 16, 15))),
                Token(StringLiteralToken, "\"Hello \"", Span(Position(1, 17, 16), Position(1, 25, 24))),
                Token(PlusToken, "+", Span(Position(1, 26, 25), Position(1, 27, 26))),
                Token(NumberLiteralToken, "123", Span(Position(1, 28, 27), Position(1, 31, 30))),
                Token(SemicolonToken, ";", Span(Position(1, 31, 30), Position(1, 32, 31))),
            ),
        )
    }
}
