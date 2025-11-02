package formatter.internal.visitor

import formatter.internal.model.value.DocValue
import formatter.internal.type.getChildAt
import formatter.internal.type.setLeadingNewlines
import formatter.internal.type.setTrailingNewlines
import formatter.internal.type.toDoc
import formatter.internal.type.updateChildAt
import model.diagnostic.Diagnostic
import model.node.Node
import model.node.PrintlnStatementNode
import model.value.NoneValue
import model.value.Value
import model.visitor.Visitor
import model.visitor.VisitorTable
import type.outcome.Outcome

internal class LineBreaksAfterPrintlnVisitor(
    private val lineBreaks: Int,
) : Visitor {

    override fun visit(
        node: Node.Leaf,
        table: VisitorTable,
    ): Outcome<Value, Diagnostic> = Outcome.Ok(NoneValue)

    override fun visit(
        node: Node.Composite,
        table: VisitorTable,
    ): Outcome<Value, Diagnostic> {
        if (lineBreaks < 0 || node.type != PrintlnStatementNode) {
            return Outcome.Ok(DocValue(node.toDoc()))
        }

        val lastIndex = node.children.size - 1
        val lastLeaf = node.getChildAt(lastIndex)
        if (lastLeaf !is Node.Leaf) return Outcome.Ok(DocValue(node.toDoc()))

        val updatedLeaf = lastLeaf.setLeadingNewlines(lastLeaf.span, 0)
            .setTrailingNewlines(lastLeaf.span, lineBreaks)

        val updatedNode = node.updateChildAt(lastIndex) { updatedLeaf }
        return Outcome.Ok(DocValue(updatedNode.toDoc()))
    }
}
