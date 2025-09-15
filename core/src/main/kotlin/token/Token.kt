package token

data class Token(
    val type: TokenType,
    val value: String,
    val prefixTrivia: String = "",
    val suffixTrivia: String = "",
    val start: Int = 0,
    val end: Int = 0,
)
