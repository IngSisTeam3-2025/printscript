package matcher

import rule.TokenRule

sealed class MatchResult {
    data class Full(val tokenRule: TokenRule, val lexeme: String) : MatchResult()
    data class Partial(val symbol: TokenRule, val lexeme: String) : MatchResult()
    data object Skip : MatchResult()
    data object None : MatchResult()
}
