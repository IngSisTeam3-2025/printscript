package lexer.processor

import lexer.matcher.Matcher
import lexer.matcher.token.TokenMatch

class TokenProcessor(
    private val source: SourceProcessor,
    private val matchers: List<Matcher<TokenMatch>>,
    private val maxLookahead: Int = 64,
) {
    fun matchToken(): TokenMatch? {
        val slice = source.peekSlice(maxLookahead)

        val matches = matchers.mapNotNull { it.match(slice) }
        if (matches.isEmpty()) return null
        val maxLen = matches.maxOf { it.value.length }

        val longestMatches = matches.filter { it.value.length == maxLen }
        val best = longestMatches.maxBy { it.type.priority }
        source.advance(best.length)
        return best
    }
}
