package token

import span.Position
import span.Span
import trivia.Trivia

data class Token(
    val type: TokenType,
    val lexeme: String,
    val span: Span,
    val leadingTrivia: List<Trivia> = emptyList(),
    val trailingTrivia: List<Trivia> = emptyList(),
) {
    companion object {
        val EOF = Token(TokenType.EOF, "", Span(Position(-1, 0, 0), (Position(-1, 0, 0))))
    }
}
