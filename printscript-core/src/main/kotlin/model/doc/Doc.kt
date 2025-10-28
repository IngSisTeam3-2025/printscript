package model.doc

import model.span.Span
import model.trivia.Trivia

data class Doc(
    val text: String,
    val span: Span,
    val leading: Collection<Trivia>,
    val trailing: Collection<Trivia>,
) {
    fun format(): String {
        val leadingText = leading.joinToString("") { it.lexeme }
        val trailingText = trailing.joinToString("") { it.lexeme }
        return leadingText + text + trailingText
    }
}
