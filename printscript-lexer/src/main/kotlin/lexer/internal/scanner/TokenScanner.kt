package lexer.internal.scanner

import lexer.internal.buffer.LexemeBuffer
import lexer.internal.model.scan.TokenScan
import lexer.internal.model.terminal.TokenTerminal
import lexer.internal.spanner.Spanner
import model.token.TokenType
import type.option.Option

internal class TokenScanner(private val terminals: Collection<TokenTerminal>) {

    private data class TokenMatch(
        val lexeme: String,
        val type: TokenType,
    )

    fun scan(
        buffer: LexemeBuffer,
        spanner: Spanner,
    ): TokenScan {
        if (buffer.isEmpty()) return TokenScan.Empty

        val best = findLongestMatch(buffer)
        return if (best is Option.Some) {
            val (lexeme, type) = best.value
            val span = spanner.span(lexeme)

            TokenScan.Ok(type, lexeme, span)
        } else {
            TokenScan.Error
        }
    }

    private fun findLongestMatch(buffer: LexemeBuffer): Option<TokenMatch> {
        var best: Option<TokenMatch> = Option.None
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

    private fun findBestTerminal(slice: String): Option<TokenMatch> {
        var bestMatch: Option<TokenMatch> = Option.None
        var bestPriority = 0
        var bestLength = 0

        for (terminal in terminals) {
            val match = terminal.pattern.matchAt(slice, 0)
            if (match != null && match.range.first == 0) {
                val matchLength = match.value.length

                val isBetterMatch = matchLength > bestLength ||
                    (matchLength == bestLength && terminal.priority > bestPriority)

                if (isBetterMatch) {
                    bestMatch = Option.Some(TokenMatch(match.value, terminal.type))
                    bestPriority = terminal.priority
                    bestLength = matchLength
                }
            }
        }
        return bestMatch
    }
}
