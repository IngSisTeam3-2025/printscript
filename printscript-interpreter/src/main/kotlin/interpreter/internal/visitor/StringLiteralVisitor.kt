package interpreter.internal.visitor

import model.node.Node
import model.node.StringLiteralNode
import model.value.NoneValue
import model.value.StringValue
import model.visitor.context.ContextVisitor
import model.visitor.context.ContextVisitorTable
import model.visitor.context.VisitResult
import model.visitor.context.VisitorContext
import type.outcome.Outcome

internal class StringLiteralVisitor : ContextVisitor {

    override fun visit(
        node: Node.Leaf,
        table: ContextVisitorTable,
        context: VisitorContext,
    ): VisitResult {
        if (node.type != StringLiteralNode) {
            return VisitResult(Outcome.Ok(NoneValue), context)
        }

        val value = node.value as StringValue
        val unquoted = value.value
            .removeSurrounding("\"")
            .removeSurrounding("'")

        return VisitResult(Outcome.Ok(StringValue(unquoted)), context)
    }

    override fun visit(
        node: Node.Composite,
        table: ContextVisitorTable,
        context: VisitorContext,
    ): VisitResult = VisitResult(Outcome.Ok(NoneValue), context)
}
