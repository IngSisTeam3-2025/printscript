package lexer.internal.table

import lexer.internal.model.terminal.TokenTerminal
import lexer.internal.model.terminal.TriviaTerminal

internal interface TerminalTable {
    val tokenTerminals: Collection<TokenTerminal>
    val triviaTerminals: Collection<TriviaTerminal>
}
