package formatter.internal.visitor

import formatter.internal.model.value.DocValue
import formatter.internal.type.ensureLeadingSpace
import formatter.internal.type.ensureTrailingSpace
import formatter.internal.type.toDoc
import formatter.internal.type.updateChildAt
import model.diagnostic.Diagnostic
import model.node.Node
import model.node.NodeType
import model.value.NoneValue
import model.value.Value
import model.visitor.Visitor
import model.visitor.VisitorTable
import type.outcome.Outcome

internal class SpacingAroundOperatorVisitor(
    private val enforce: Boolean,
    private val operators: Collection<NodeType>,
) : Visitor {

    override fun visit(
        node: Node.Leaf,
        table: VisitorTable,
    ): Outcome<Value, Diagnostic> {
        return Outcome.Ok(NoneValue)
    }

    override fun visit(
        node: Node.Composite,
        table: VisitorTable,
    ): Outcome<Value, Diagnostic> {
        if (!enforce) return Outcome.Ok(DocValue(node.toDoc()))

        var updatedNode = node

        node.children.forEachIndexed { index, child ->
            if (child is Node.Leaf && operators.contains(child.type)) {
                val withLeading = child.ensureLeadingSpace(child.span)
                val withBoth = withLeading.ensureTrailingSpace(child.span)

                updatedNode = updatedNode.updateChildAt(index) { withBoth }
            }
        }
        return Outcome.Ok(DocValue(updatedNode.toDoc()))
    }
}
