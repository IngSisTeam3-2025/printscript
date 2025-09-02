package token

import source.SourcePosition

data class Token(
    val type: TokenType,
    val lexeme: String,
    val position: SourcePosition,
)
