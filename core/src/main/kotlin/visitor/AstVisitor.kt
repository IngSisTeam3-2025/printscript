package visitor

import ast.AstNode
import provider.DependencyProvider

interface AstVisitor {
    fun visit(node: AstNode, dispatcher: VisitorDispatcher, provider: DependencyProvider): VisitResult
}
