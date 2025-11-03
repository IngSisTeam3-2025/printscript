package model.trivia

import model.span.Span

data class Trivia(
    val type: TriviaType,
    val lexeme: String,
    val span: Span,
)
