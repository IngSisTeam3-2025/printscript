package lexer.internal.model.terminal

import model.token.TokenType

internal interface TokenTerminal {
    val type: TokenType
    val pattern: Regex
    val priority: Int
}
