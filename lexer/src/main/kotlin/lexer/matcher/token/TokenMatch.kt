package lexer.matcher.token

import token.TokenType

interface TokenMatch {
    val type: TokenType
    val value: String
    val length: Int
}
