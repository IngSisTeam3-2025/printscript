package lexer

import token.TokenType

data class TokenMatch(
    val type: TokenType,
    val value: String,
    val length: Int,
)

interface TokenRecognizer {
    fun match(input: String): TokenMatch?
}

class NumberRecognizer : TokenRecognizer {
    private val regex = Regex("^\\d+")
    override fun match(input: String): TokenMatch? =
        regex.find(input)?.let { TokenMatch(TokenType.NUMBER, it.value, it.value.length) }
}

class IdentifierRecognizer : TokenRecognizer {
    private val regex = Regex("^[a-zA-Z][a-zA-Z0-9]*")
    override fun match(input: String): TokenMatch? =
        regex.find(input)?.let { TokenMatch(TokenType.IDENTIFIER, it.value, it.value.length) }
}

class PlusRecognizer : TokenRecognizer {
    override fun match(input: String): TokenMatch? =
        if (input.startsWith("+")) TokenMatch(TokenType.PLUS, "+", 1) else null
}

class MinusRecognizer : TokenRecognizer {
    override fun match(input: String): TokenMatch? =
        if (input.startsWith("-")) TokenMatch(TokenType.MINUS, "-", 1) else null
}
