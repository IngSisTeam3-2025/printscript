import ast.Program
import source.SourcePosition

sealed class ParseResult {
    data class Success(val program: Program) : ParseResult()
    data class Error(val message: String, val position: SourcePosition) : ParseResult()
}
