import symbol.Symbol

sealed class MatchResult {
    data class Full(val symbol: Symbol, val lexeme: String) : MatchResult()
    data class Partial(val symbol: Symbol, val lexeme: String) : MatchResult()
    data object Skip : MatchResult()
    data object None : MatchResult()
}
