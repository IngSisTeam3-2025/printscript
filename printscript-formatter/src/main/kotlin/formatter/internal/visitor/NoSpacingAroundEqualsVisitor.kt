package formatter.internal.visitor

import formatter.internal.model.value.DocValue
import formatter.internal.type.findChildOfType
import formatter.internal.type.getChildAt
import formatter.internal.type.hasSpace
import formatter.internal.type.hasSpaceAfter
import formatter.internal.type.hasSpaceBefore
import formatter.internal.type.removeLeadingSpaces
import formatter.internal.type.removeTrailingSpaces
import formatter.internal.type.toDoc
import formatter.internal.type.updateChildAt
import model.diagnostic.Diagnostic
import model.node.AssignNode
import model.node.AssignStatementNode
import model.node.ConstDeclarationStatementNode
import model.node.LetDeclarationStatementNode
import model.node.Node
import model.value.NoneValue
import model.value.Value
import model.visitor.Visitor
import model.visitor.VisitorTable
import type.outcome.Outcome

internal class NoSpacingAroundEqualsVisitor(
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
        if (!isRelevantNode(node)) return Outcome.Ok(NoneValue)

        val assignIndex = node.findChildOfType(AssignNode)
        if (assignIndex == -1) return Outcome.Ok(NoneValue)

        val assignNode = node.getChildAt(assignIndex) as? Node.Leaf
            ?: return Outcome.Ok(NoneValue)

        if (!hasSpacesAround(node, assignIndex)) {
            return Outcome.Ok(NoneValue)
        }

        val updated = removeSpacesAroundEquals(node, assignIndex, assignNode)
        return Outcome.Ok(DocValue(updated.toDoc()))
    }

    private fun isRelevantNode(node: Node.Composite): Boolean {
        return node.type == LetDeclarationStatementNode ||
            node.type == ConstDeclarationStatementNode ||
            node.type == AssignStatementNode
    }

    private fun hasSpacesAround(node: Node.Composite, assignIndex: Int): Boolean {
        val hasSpaceBefore = node.hasSpaceBefore(assignIndex)
        val hasSpaceAfter = node.hasSpaceAfter(assignIndex)
        return hasSpaceBefore || hasSpaceAfter
    }

    private fun removeSpacesAroundEquals(
        node: Node.Composite,
        assignIndex: Int,
        assignNode: Node.Leaf,
    ): Node.Composite {
        var updated = node

        val cleaned = assignNode
            .removeLeadingSpaces()
            .removeTrailingSpaces()
        updated = updated.updateChildAt(assignIndex) { cleaned }

        updated = cleanPreviousNode(updated, assignIndex)
        updated = cleanNextNode(updated, assignIndex)

        return updated
    }

    private fun cleanPreviousNode(node: Node.Composite, assignIndex: Int): Node.Composite {
        val prevIndex = assignIndex - 1
        if (prevIndex < 0) return node

        val prevNode = node.getChildAt(prevIndex)
        if (prevNode is Node.Leaf && prevNode.trailing.hasSpace()) {
            val cleanedPrev = prevNode.removeTrailingSpaces()
            return node.updateChildAt(prevIndex) { cleanedPrev }
        }

        return node
    }

    private fun cleanNextNode(node: Node.Composite, assignIndex: Int): Node.Composite {
        val nextIndex = assignIndex + 1
        if (nextIndex >= node.children.size) return node

        val nextNode = node.getChildAt(nextIndex)
        if (nextNode is Node.Leaf && nextNode.leading.hasSpace()) {
            val cleanedNext = nextNode.removeLeadingSpaces()
            return node.updateChildAt(nextIndex) { cleanedNext }
        }

        return node
    }
}
