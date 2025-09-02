package lexer
import source.SourcePosition
import token.Token

sealed class LexResult {
    data object EOF : LexResult()
    data class Success(val token: Token) : LexResult()
    data class IOError(val message: String) : LexResult()
    data class Error(val message: String, val position: SourcePosition) : LexResult()
}
