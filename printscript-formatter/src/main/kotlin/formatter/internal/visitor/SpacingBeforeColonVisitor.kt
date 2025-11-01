package formatter.internal.visitor

import formatter.internal.model.value.DocValue
import formatter.internal.type.addLeadingSpace
import formatter.internal.type.findChildOfType
import formatter.internal.type.getChildAt
import formatter.internal.type.hasSpaceBefore
import formatter.internal.type.toDoc
import formatter.internal.type.updateChildAt
import model.diagnostic.Diagnostic
import model.node.ColonNode
import model.node.Node
import model.value.NoneValue
import model.value.Value
import model.visitor.Visitor
import model.visitor.VisitorTable
import type.outcome.Outcome

internal class SpacingBeforeColonVisitor(
    private val enforce: Boolean,
) : Visitor {

    override fun visit(
        node: Node.Leaf,
        table: VisitorTable,
    ): Outcome<Value, Diagnostic> = Outcome.Ok(NoneValue)

    override fun visit(
        node: Node.Composite,
        table: VisitorTable,
    ): Outcome<Value, Diagnostic> {
        if (!enforce) return Outcome.Ok(NoneValue)

        val colonIndex = node.findChildOfType(ColonNode)
        if (colonIndex == -1) return Outcome.Ok(NoneValue)

        val colonNode = when (val child = node.getChildAt(colonIndex)) {
            is Node.Leaf -> child
            is Node.Composite -> return Outcome.Ok(NoneValue)
        }

        val hasSpaceBefore = node.hasSpaceBefore(colonIndex)

        if (hasSpaceBefore) {
            return Outcome.Ok(NoneValue)
        }

        val updated = colonNode.addLeadingSpace(colonNode.span)
        val result = node.updateChildAt(colonIndex) { updated }

        return Outcome.Ok(DocValue(result.toDoc()))
    }
}
