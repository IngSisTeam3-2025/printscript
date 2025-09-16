package token

import location.SourceLocation

data class Token(
    val type: TokenType,
    val value: String,
    val prefixTrivia: String = "",
    val suffixTrivia: String = "",
    val start: SourceLocation,
    val end: SourceLocation,
)
