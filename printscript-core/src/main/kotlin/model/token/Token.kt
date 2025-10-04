package model.token

import model.span.Span
import model.trivia.Trivia

data class Token(
    val type: TokenType,
    val lexeme: String,
    val span: Span,
    val leading: Collection<Trivia> = emptyList(),
    val trailing: Collection<Trivia> = emptyList(),
)
