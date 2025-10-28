package validator.internal.visitor

import model.node.Node
import model.node.NumberLiteralNode
import model.value.NoneValue
import model.visitor.context.ContextVisitor
import model.visitor.context.ContextVisitorTable
import model.visitor.context.VisitResult
import model.visitor.context.VisitorContext
import type.outcome.Outcome

internal class NumberLiteralVisitor : ContextVisitor {
    override fun visit(
        node: Node.Leaf,
        table: ContextVisitorTable,
        context: VisitorContext,
    ): VisitResult {
        if (node.type != NumberLiteralNode) return VisitResult(Outcome.Ok(NoneValue), context)

        val value = node.value
        return VisitResult(Outcome.Ok(value), context)
    }

    override fun visit(
        node: Node.Composite,
        table: ContextVisitorTable,
        context: VisitorContext,
    ): VisitResult = VisitResult(Outcome.Ok(NoneValue), context)
}
