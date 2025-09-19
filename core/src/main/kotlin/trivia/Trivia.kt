package trivia

import span.Span

data class Trivia(val type: TriviaType, val text: String, val span: Span)
