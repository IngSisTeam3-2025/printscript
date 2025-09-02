
import source.SourcePosition
import token.Token

sealed class LexerResult {
    data object EOF : LexerResult()
    data class Success(val token: Token) : LexerResult()
    data class IOError(val message: String) : LexerResult()
    data class Error(val message: String, val position: SourcePosition) : LexerResult()
}
