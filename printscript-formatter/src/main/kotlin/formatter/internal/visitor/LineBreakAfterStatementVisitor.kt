package formatter.internal.visitor

import formatter.internal.model.value.DocValue
import formatter.internal.type.addTrailingNewline
import formatter.internal.type.findChildOfType
import formatter.internal.type.getChildAt
import formatter.internal.type.hasNewline
import formatter.internal.type.toDoc
import formatter.internal.type.updateChildAt
import model.diagnostic.Diagnostic
import model.node.Node
import model.node.SemicolonNode
import model.value.NoneValue
import model.value.Value
import model.visitor.Visitor
import model.visitor.VisitorTable
import type.outcome.Outcome

internal class LineBreakAfterStatementVisitor(
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

        val semicolonIndex = node.findChildOfType(SemicolonNode)
        if (semicolonIndex == -1) return Outcome.Ok(NoneValue)

        val semicolonNode = when (val child = node.getChildAt(semicolonIndex)) {
            is Node.Leaf -> child
            is Node.Composite -> return Outcome.Ok(NoneValue)
        }

        if (semicolonNode.trailing.hasNewline()) {
            return Outcome.Ok(NoneValue)
        }

        val updatedSemicolon = semicolonNode.addTrailingNewline(semicolonNode.span)
        val updatedNode = node.updateChildAt(semicolonIndex) { updatedSemicolon }

        return Outcome.Ok(DocValue(updatedNode.toDoc()))
    }
}
