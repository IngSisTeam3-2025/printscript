package lexer

import lexer.matcher.token.BooleanLiteralMatcher
import lexer.matcher.token.IdentifierMatcher
import lexer.matcher.token.NumberMatcher
import lexer.matcher.token.SymbolMatcher
import lexer.matcher.trivia.WhitespaceMatcher
import token.TokenType

fun main() {
    val input = "true3 true. true"
    val lexer = Lexer(
        input.iterator(),
        listOf(
            IdentifierMatcher(),
            NumberMatcher(),
            BooleanLiteralMatcher(),
            SymbolMatcher('+', TokenType.PLUS),
            SymbolMatcher('-', TokenType.MINUS),
        ),
        listOf(WhitespaceMatcher()),
    )

    while (lexer.hasNext()) {
        val result = lexer.next()
        result.fold(
            onSuccess = {
                println(
                    "Token: ${it.type}, value='${it.value}', prefix='${it.prefixTrivia}', " +
                        "suffix='${it.suffixTrivia}', start=${it.start}, end=${it.end}",
                )
            },
            onFailure = { println("Lexer error: ${it.message}") },
        )
    }
}
