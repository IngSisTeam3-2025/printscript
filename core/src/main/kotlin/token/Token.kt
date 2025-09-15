package token

import location.SourceLocation

data class Token(
    val type: TokenType,
    val lexeme: String,
    val start: SourceLocation,
    val end: SourceLocation
)
