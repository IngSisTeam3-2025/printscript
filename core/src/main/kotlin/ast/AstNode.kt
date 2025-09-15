package ast

import location.SourceLocation
import provider.DependencyProvider
import visitor.VisitResult
import visitor.VisitorDispatcher

abstract class AstNode(
    val leadingTrivia: String = "",
    val trailingTrivia: String = "",
) {
    abstract val children: List<AstNode>
    abstract fun start(): SourceLocation
    abstract fun end(): SourceLocation
    abstract fun accept(dispatcher: VisitorDispatcher, provider: DependencyProvider): VisitResult
}
