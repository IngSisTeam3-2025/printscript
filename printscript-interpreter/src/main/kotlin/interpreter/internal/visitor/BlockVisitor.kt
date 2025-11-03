package interpreter.internal.visitor

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
        if (node.type != BlockNode) return VisitResult(Outcome.Ok(NoneValue), context)

        var currentContext = context

        for (statement in node.children) {
            if (statement.type == LeftBraceNode || statement.type == RightBraceNode) {
                continue
            }

            val result = table.dispatch(statement, currentContext)
            if (result.outcome is Outcome.Error) {
                return result
            }

            currentContext = result.context
        }
        return VisitResult(Outcome.Ok(NoneValue), currentContext)
    }
}
