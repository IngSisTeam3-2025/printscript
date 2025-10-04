package internal.collector

import internal.buffer.LexemeBuffer
import internal.spanner.Spanner
import internal.model.terminal.TriviaTerminal
import model.trivia.Trivia
import util.option.Option

internal class TriviaCollector(private val terminals: Collection<TriviaTerminal>) {

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

    private fun findLongestMatch(buffer: LexemeBuffer): Option<Pair<String, TriviaTerminal>> {
        var lookahead = 1
        var best: Option<Pair<String, TriviaTerminal>> = Option.None
        var lastLength = 0

        while (buffer.hasNext()) {
            val slice = buffer.peek(lookahead)
            if (slice.length <= lastLength) break
            lastLength = slice.length

            val match = matchTerminals(slice)
            if (match is Option.Some) best = match

            lookahead++
        }

        return best
    }

    private fun matchTerminals(slice: String): Option<Pair<String, TriviaTerminal>> {
        for (terminal in terminals) {
            val match = terminal.pattern.matchAt(slice, 0)
            if (match != null && match.range.first == 0)
                return Option.Some(match.value to terminal)
        }
        return Option.None
    }
}
