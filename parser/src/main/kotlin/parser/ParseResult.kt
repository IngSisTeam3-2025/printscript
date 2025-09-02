package parser

import ast.AstNode
import source.SourcePosition

sealed interface ParseResult {
    data class Success(val node : AstNode) : ParseResult
    data class Error(val message : String, val position: SourcePosition) : ParseResult
    data object EOF : ParseResult
}
