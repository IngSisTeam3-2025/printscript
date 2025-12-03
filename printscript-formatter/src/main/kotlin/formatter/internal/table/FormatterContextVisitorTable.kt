package formatter.internal.table

import formatter.internal.model.value.DocValue
import formatter.internal.model.value.NodeValue
import formatter.internal.type.toDoc
import model.node.Node
import model.visitor.context.ContextVisitor
import model.visitor.context.ContextVisitorTable
import model.visitor.context.VisitResult
import model.visitor.context.VisitorContext
import type.outcome.Outcome

internal class FormatterContextVisitorTable(
    override val visitors: Collection<ContextVisitor>,
) : ContextVisitorTable {

    override fun dispatch(node: Node, context: VisitorContext): VisitResult {
        var currentContext = context
        var currentNode = node

        for (visitor in visitors) {
            val visit = currentNode.accept(visitor, this, currentContext)
            val outcome = visit.outcome
            if (outcome is Outcome.Error) return visit

            currentContext = visit.context

            if (outcome is Outcome.Ok) {
                val value = outcome.value
                if (value is NodeValue) {
                    currentNode = value.value
                }
            }
        }

        val result = VisitResult(Outcome.Ok(DocValue(currentNode.toDoc())), currentContext)
        return result
    }
}
