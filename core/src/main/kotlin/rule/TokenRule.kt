package rule

import token.TokenType
import java.util.regex.Pattern

data class TokenRule(
    val tokenType: TokenType,
    val pattern: Pattern,
    val priority: Int,
    val ignore: Boolean = false,
)
