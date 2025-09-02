import symbol.Symbol

class SymbolMatcher(private val alphabet: List<Symbol>) {

    fun match(lexeme: String): MatchResult {
        var bestFullMatch: Symbol? = null
        var bestPartialMatch: Symbol? = null

        for (symbol in alphabet) {
            val matcher = symbol.pattern.matcher(lexeme)

            if (isFullMatch(matcher)) {
                if (symbol.ignore) {
                    return MatchResult.Skip
                }
                if (bestFullMatch == null || symbol.priority > bestFullMatch.priority) {
                    bestFullMatch = symbol
                }
            } else if (isPrefixMatch(matcher)) {
                if (bestPartialMatch == null || symbol.priority > bestPartialMatch.priority) {
                    bestPartialMatch = symbol
                }
            }
        }

        return buildMatchResult(bestFullMatch, bestPartialMatch, lexeme)
    }

    private fun isFullMatch(matcher: java.util.regex.Matcher): Boolean {
        return matcher.matches()
    }

    private fun isPrefixMatch(matcher: java.util.regex.Matcher): Boolean {
        return matcher.hitEnd() && !matcher.matches()
    }

    private fun buildMatchResult(
        fullMatch: Symbol?,
        partialMatch: Symbol?,
        lexeme: String,
    ): MatchResult {
        return when {
            fullMatch != null -> MatchResult.Full(fullMatch, lexeme)
            partialMatch != null -> MatchResult.Partial(partialMatch, lexeme)
            else -> MatchResult.None
        }
    }
}
