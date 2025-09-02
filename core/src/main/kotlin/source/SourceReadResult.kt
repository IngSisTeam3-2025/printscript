package source

sealed class SourceReadResult {
    data class Success(val char: Char, val position: SourcePosition) : SourceReadResult()
    data class Failure(val message: String) : SourceReadResult()
    data object EOF : SourceReadResult()
}
