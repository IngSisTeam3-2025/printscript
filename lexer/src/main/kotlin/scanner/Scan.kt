package lexer.scanner

import token.TokenType

sealed class Scan {
    data class Success(
        val type: TokenType,
        val lexeme: String,
        val start: Int,
        val end: Int
    ) : Scan()

    data class Failure(
        val message: String,
        val start: Int,
        val end: Int
    ) : Scan()
}
