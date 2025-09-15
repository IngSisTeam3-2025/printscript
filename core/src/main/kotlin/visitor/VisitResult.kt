package visitor

import ast.AstNode
import location.SourceLocation

sealed class VisitResult {
    data class Success(val value: Any?) : VisitResult()
    data class Error(val message: String, val start: SourceLocation, val end: SourceLocation) : VisitResult()
    data class NotHandled(val node: AstNode) : VisitResult()
}
