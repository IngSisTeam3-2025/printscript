package formatter.internal.transformer

import formatter.internal.model.value.DocValue
import model.node.Node
import model.visitor.context.ContextVisitorTable
import model.visitor.context.VisitResult
import model.visitor.context.VisitorContext
import type.outcome.Outcome

internal object NodeTransformer {

    fun applyDispatchResult(child: Node, dispatchResult: VisitResult): Node {
        val outcome = dispatchResult.outcome

        return when (outcome) {
            is Outcome.Ok -> {
                val value = outcome.value
                if (value is DocValue) {
                    val doc = value.value
                    when (child) {
                        is Node.Leaf -> child.copy(leading = doc.leading, trailing = doc.trailing)
                        is Node.Composite -> child
                    }
                } else {
                    child
                }
            }
            else -> child
        }
    }

    fun transformRecursive(
        node: Node,
        table: ContextVisitorTable,
        context: VisitorContext,
    ): Node {
        return when (node) {
            is Node.Leaf -> node
            is Node.Composite -> {
                val transformedChildren = node.children.map { child ->
                    val dispatched = applyDispatchResult(child, table.dispatch(child, context))
                    transformRecursive(dispatched, table, context)
                }
                node.copy(children = transformedChildren)
            }
        }
    }
}
