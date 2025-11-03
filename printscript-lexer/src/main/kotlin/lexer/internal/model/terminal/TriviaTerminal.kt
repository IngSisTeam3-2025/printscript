package lexer.internal.model.terminal

import model.trivia.TriviaType

interface TriviaTerminal {
    val type: TriviaType
    val pattern: Regex
    val priority: Int
}
