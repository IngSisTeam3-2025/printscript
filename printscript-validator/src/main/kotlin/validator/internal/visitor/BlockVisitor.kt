package validator.internal.visitor

import model.node.BlockNode
import model.node.LeftBraceNode
import model.node.Node
import model.node.RightBraceNode
import model.value.NoneValue
import model.visitor.context.ContextVisitor
import model.visitor.context.ContextVisitorTable
import model.visitor.context.VisitResult
import model.visitor.context.VisitorContext
import type.outcome.Outcome

internal class BlockVisitor : ContextVisitor {

    override fun visit(
        node: Node.Leaf,
        table: ContextVisitorTable,
        context: VisitorContext,
    ): VisitResult = VisitResult(Outcome.Ok(NoneValue), context)

    override fun visit(
        node: Node.Composite,
        table: ContextVisitorTable,
        context: VisitorContext,
    ): VisitResult {
        if (node.type != BlockNode) {
            return VisitResult(Outcome.Ok(NoneValue), context)
        }

        val children = node.children.toList()
        val statements = children
            .dropWhile { it.type != LeftBraceNode }
            .drop(1)
            .takeWhile { it.type != RightBraceNode }

        var currentContext = context

        for (statement in statements) {
            val visit = table.dispatch(statement, currentContext)
            if (visit.outcome is Outcome.Error) return visit

            currentContext = visit.context
        }

        return VisitResult(Outcome.Ok(NoneValue), currentContext)
    }
}
