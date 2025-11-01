package formatter.internal.visitor

import formatter.internal.model.value.DocValue
import formatter.internal.type.ensureLeadingSpace
import formatter.internal.type.ensureTrailingSpace
import formatter.internal.type.findChildOfType
import formatter.internal.type.getChildAt
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

internal class MandatorySingleSpaceVisitor(
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
        if (!enforce) return Outcome.Ok(DocValue(node.toDoc()))

        val assignIndex = node.findChildOfType(AssignNode)
        if (assignIndex == -1) return Outcome.Ok(DocValue(node.toDoc()))

        val before = node.getChildAt(assignIndex - 1)
        val assign = node.getChildAt(assignIndex)
        val after = node.getChildAt(assignIndex + 1)

        val updatedNode = node
            .updateChildAt(assignIndex - 1) { child ->
                if (child is Node.Leaf) child.ensureTrailingSpace(assign.span) else child
            }
            .updateChildAt(assignIndex) { child ->
                if (child is Node.Leaf) {
                    child.ensureLeadingSpace(before.span)
                        .ensureTrailingSpace(after.span)
                } else {
                    child
                }
            }
            .updateChildAt(assignIndex + 1) { child ->
                if (child is Node.Leaf) child.ensureLeadingSpace(assign.span) else child
            }

        return Outcome.Ok(DocValue(updatedNode.toDoc()))
    }
}
