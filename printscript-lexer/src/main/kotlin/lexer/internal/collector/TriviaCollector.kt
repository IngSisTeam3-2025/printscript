package lexer.internal.collector

import lexer.internal.buffer.LexemeBuffer
import lexer.internal.model.terminal.TriviaTerminal
import lexer.internal.spanner.Spanner
import model.trivia.Trivia
import type.option.Option

internal class TriviaCollector(private val terminals: Collection<TriviaTerminal>) {

    private data class TriviaMatch(val lexeme: String, val terminal: TriviaTerminal)

    fun collect(buffer: LexemeBuffer, spanner: Spanner): Collection<Trivia> {
        val trivia = mutableListOf<Trivia>()

        while (buffer.hasNext()) {
            val best = findLongestMatch(buffer)

            when (best) {
                is Option.Some -> {
                    val (text, terminal) = best.value
                    val span = spanner.span(text)
                    buffer.consume(text.length)
                    trivia.add(Trivia(terminal.type, text, span))
                }
                is Option.None -> break
            }
        }

        return trivia
    }

    private fun findLongestMatch(buffer: LexemeBuffer): Option<TriviaMatch> {
        var best: Option<TriviaMatch> = Option.None
        var bestLength = 0
        var peekSize = 64

        while (buffer.hasNext()) {
            val slice = buffer.peek(peekSize)

            val match = findBestTerminal(slice)
            if (match is Option.Some) {
                val matchLength = match.value.lexeme.length
                if (matchLength > bestLength) {
                    best = match
                    bestLength = matchLength
                    peekSize *= 2
                } else {
                    break
                }
            } else {
                break
            }
        }

        return best
    }

    private fun findBestTerminal(slice: String): Option<TriviaMatch> {
        for (terminal in terminals) {
            val match = terminal.pattern.matchAt(slice, 0)
            if (match != null && match.range.first == 0) {
                return Option.Some(TriviaMatch(match.value, terminal))
            }
        }
        return Option.None
    }
}
