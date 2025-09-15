package lexer

import iterator.PeekableIterator
import lexer.lexer.lexers.*
import token.TokenType

fun main() {
    // Define some token types
    val intType = TokenType("NUMBER_INTEGER", priority = 5)
    val floatType = TokenType("NUMBER_FLOAT", priority = 10)
    val letKeywordType = TokenType("LET", priority = 20)
    val whitespaceType = TokenType("WHITESPACE", ignore = true)

    // Define lexers
    val digit = DigitLexer()
    val integerLexer = RepeatLexer(digit, intType)

    val dot = CharLexer('.', TokenType("DOT"))
    val fractional = SequenceLexer(dot, RepeatLexer(digit, TokenType("NUMBER_FRACTION")), TokenType("NUMBER_FRACTION"))
    val floatLexer = SequenceLexer(integerLexer, fractional, floatType)

    val letKeywordLexer = KeywordLexer("let", letKeywordType)
    val spaceLexer = RepeatLexer(CharLexer(' ', whitespaceType), whitespaceType)

    // Combined lexer list
    val lexers = listOf(floatLexer, integerLexer, letKeywordLexer, spaceLexer)

    // Input string
    val inputText = "let x = 42.5"

    // Wrap input in PeekableIterator
    val iterator = inputText.iterator()

    // Create lexical analyzer
    val analyzer = LexicalAnalyzer(iterator, lexers, TestReporter())

    // Iterate and print tokens
    while (analyzer.hasNext()) {
        val result = analyzer.next()
        result.onSuccess { token ->
            println("Token: ${token.type.name}, Lexeme: '${token.lexeme}', Pos: ${token.start}-${token.end}")
        }.onFailure { ex ->
            println("Lexical error: ${ex.message}")
        }
    }
}
