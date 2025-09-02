package symbol

import token.TokenType
import java.util.regex.Pattern

data class Symbol(
    val tokenType: TokenType,
    val pattern: Pattern,
    val priority: Int,
    val ignore: Boolean = false,
)
