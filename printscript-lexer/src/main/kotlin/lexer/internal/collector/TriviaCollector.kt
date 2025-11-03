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
        var lookahead = 1
        var best: Option<TriviaMatch> = Option.None
        var lastLength = 0

        while (buffer.hasNext()) {
            val slice = buffer.peek(lookahead)
            if (slice.length <= lastLength) break
            lastLength = slice.length

            val match = findBestTerminal(slice)
            if (match is Option.Some) best = match

            lookahead++
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
