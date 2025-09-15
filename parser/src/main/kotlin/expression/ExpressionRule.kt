package expression

import token.Token

interface ExpressionRule {
    val name: String
    fun match(tokens: List<Token>): ExpressionMatchResult
}

/*


https://chatgpt.com/share/68c04966-a4d0-8002-9f75-ed96e51d0f04


 */