package internal.scanner

import internal.model.terminal.TokenTerminal
import internal.buffer.LexemeBuffer
import internal.model.scan.Scan
import internal.spanner.Spanner
import model.token.TokenType
import util.option.Option

internal class TokenScanner(private val terminals: Collection<TokenTerminal>) {

    fun scan(buffer: LexemeBuffer, spanner: Spanner): Scan {
        if (buffer.isEmpty()) return Scan.Empty

        val best = findBestMatch(buffer)
        return if (best is Option.Some) {
            val (lexeme, type) = best.value
            val span = spanner.span(lexeme)
            buffer.consume(lexeme.length)

            Scan.Ok(type, lexeme, span)
        } else {
            Scan.Error
        }
    }

    private fun findBestMatch(buffer: LexemeBuffer): Option<Pair<String, TokenType>> {
        var lookahead = 1
        var best: Option<Pair<String, TokenType>> = Option.None
        var lastLength = 0

        while (buffer.hasNext()) {
            val slice = buffer.peek(lookahead)
            if (slice.length == lastLength) break
            lastLength = slice.length

            val match = findBestTerminal(slice)
            if (match is Option.Some) best = match

            lookahead++
        }

        return best
    }

    private fun findBestTerminal(slice: String): Option<Pair<String, TokenType>> {
        var bestMatch: Option<Pair<String, TokenType>> = Option.None
        var bestPriority = 0
        var bestLength = 0

        for (terminal in terminals) {
            val match = terminal.pattern.matchAt(slice, 0)
            if (match != null && match.range.first == 0) {
                val matchLength = match.value.length

                if (matchLength > bestLength || (matchLength == bestLength && terminal.priority > bestPriority)) {
                    bestMatch = Option.Some(match.value to terminal.type)
                    bestPriority = terminal.priority
                    bestLength = matchLength
                }
            }
        }

        return bestMatch
    }
}

