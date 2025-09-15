package visitor

import ast.AstNode
import provider.DependencyProvider

class VisitorDispatcher(private val visitors: List<AstVisitor>) {
    fun dispatch(node: AstNode, provider: DependencyProvider): VisitResult {
        for (visitor in visitors) {
            val result = visitor.visit(node, this, provider)
            if (result !is VisitResult.NotHandled) return result
        }
        return VisitResult.NotHandled(node)
    }
}

