package lexer.internal.spanner

import model.span.Position
import model.span.Span

internal class Spanner {
    private var line: Int = 1
    private var column: Int = 1
    private var index: Int = 0

    fun span(text: String): Span {
        val start = mark()
        advance(text)
        val end = mark()
        return Span(start, end)
    }

    private fun mark(): Position = Position(line, column, index)

    private fun advance(text: String) {
        text.forEach { c ->
            when (c) {
                '\n' -> {
                    line++
                    column = 1
                }
                else -> column++
            }
            index++
        }
    }
}
