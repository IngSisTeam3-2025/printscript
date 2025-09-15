package lexer.processor

import lexer.TokenMatch
import lexer.TokenRecognizer

class TokenProcessor(
    private val source: SourceProcessor,
    private val recognizers: List<TokenRecognizer>,
) {
    fun matchToken(): TokenMatch? {
        val input = source.remainingString(MAX_PEEK)
        val matches = recognizers.mapNotNull { it.match(input) }
        return matches.maxByOrNull { it.length }
    }

    companion object {
        const val MAX_PEEK = 64
    }
}
