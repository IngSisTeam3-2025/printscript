package internal.table

import internal.model.terminal.TokenTerminal
import internal.model.terminal.TriviaTerminal

internal interface TerminalTable {
    val tokenTerminals: Collection<TokenTerminal>
    val triviaTerminals: Collection<TriviaTerminal>
}
