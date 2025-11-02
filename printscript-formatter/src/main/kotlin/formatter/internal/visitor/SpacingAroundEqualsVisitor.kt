package formatter.internal.visitor

import formatter.internal.model.value.DocValue
import formatter.internal.type.addLeadingSpace
import formatter.internal.type.addTrailingSpace
import formatter.internal.type.findChildOfType
import formatter.internal.type.getChildAt
import formatter.internal.type.hasSpaceAfter
import formatter.internal.type.hasSpaceBefore
import formatter.internal.type.toDoc
import formatter.internal.type.updateChildAt
import model.diagnostic.Diagnostic
import model.node.AssignNode
import model.node.Node
import model.value.NoneValue
import model.value.Value
import model.visitor.Visitor
import model.visitor.VisitorTable
import type.outcome.Outcome

internal class SpacingAroundEqualsVisitor(
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

        val assignIndex = node.findChildOfType(AssignNode)
        if (assignIndex == -1) return Outcome.Ok(NoneValue)

        val assignNode = when (val child = node.getChildAt(assignIndex)) {
            is Node.Leaf -> child
            is Node.Composite -> return Outcome.Ok(NoneValue)
        }

        val hasSpaceBefore = node.hasSpaceBefore(assignIndex)
        val hasSpaceAfter = node.hasSpaceAfter(assignIndex)

        if (hasSpaceBefore && hasSpaceAfter) {
            return Outcome.Ok(NoneValue)
        }

        val updated = when {
            !hasSpaceBefore && !hasSpaceAfter ->
                assignNode
                    .addLeadingSpace(assignNode.span)
                    .addTrailingSpace(assignNode.span)
            !hasSpaceBefore ->
                assignNode
                    .addLeadingSpace(assignNode.span)
            !hasSpaceAfter ->
                assignNode
                    .addTrailingSpace(assignNode.span)
            else -> assignNode
        }

        val result = node.updateChildAt(assignIndex) { updated }
        return Outcome.Ok(DocValue(result.toDoc()))
    }
}
