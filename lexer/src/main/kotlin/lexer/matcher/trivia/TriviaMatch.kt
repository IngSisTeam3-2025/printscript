package lexer.matcher.trivia

import trivia.TriviaType

interface TriviaMatch {
    val kind: TriviaType
    val text: String
    val length: Int
}
