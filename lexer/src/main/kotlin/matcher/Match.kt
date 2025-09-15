package lexer.matcher

import token.TokenType

data class Match(
    var length: Int = 0,
    var type: TokenType = TokenType("", ignore = true),
    var complete: Boolean = false
) {
    fun isValid(): Boolean = length > 0 && complete
}

