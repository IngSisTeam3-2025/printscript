package formatter.internal.visitor

import formatter.internal.model.value.DocValue
import formatter.internal.type.addTrailingSpace
import formatter.internal.type.findChildOfType
import formatter.internal.type.getChildAt
import formatter.internal.type.hasSpaceAfter
import formatter.internal.type.toDoc
import formatter.internal.type.updateChildAt
import model.diagnostic.Diagnostic
import model.node.ColonNode
import model.node.ConstDeclarationStatementNode
import model.node.LetDeclarationStatementNode
import model.node.Node
import model.value.NoneValue
import model.value.Value
import model.visitor.Visitor
import model.visitor.VisitorTable
import type.outcome.Outcome

internal class SpacingAfterColonVisitor(
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

        val isDeclaration = node.type == LetDeclarationStatementNode ||
            node.type == ConstDeclarationStatementNode

        if (!isDeclaration) return Outcome.Ok(NoneValue)

        val colonIndex = node.findChildOfType(ColonNode)
        if (colonIndex == -1) return Outcome.Ok(NoneValue)

        val colonNode = when (val child = node.getChildAt(colonIndex)) {
            is Node.Leaf -> child
            is Node.Composite -> return Outcome.Ok(NoneValue)
        }

        val hasSpaceAfter = node.hasSpaceAfter(colonIndex)

        if (hasSpaceAfter) {
            return Outcome.Ok(NoneValue)
        }

        val updated = colonNode.addTrailingSpace(colonNode.span)
        val result = node.updateChildAt(colonIndex) { updated }

        return Outcome.Ok(DocValue(result.toDoc()))
    }
}
