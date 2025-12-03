package formatter.internal.visitor

import formatter.internal.manipulator.TriviaManipulator
import formatter.internal.model.value.NodeValue
import model.node.AssignNode
import model.node.AssignStatementNode
import model.node.ConstDeclarationStatementNode
import model.node.LetDeclarationStatementNode
import model.node.Node
import model.trivia.SpaceTrivia
import model.value.NoneValue
import model.visitor.context.ContextVisitor
import model.visitor.context.ContextVisitorTable
import model.visitor.context.VisitResult
import model.visitor.context.VisitorContext
import type.outcome.Outcome

internal class NoSpacingAroundEqualsVisitor(
    private val enforce: Boolean,
) : ContextVisitor {

    override fun visit(
        node: Node.Leaf,
        table: ContextVisitorTable,
        context: VisitorContext,
    ): VisitResult {
        return VisitResult(Outcome.Ok(NoneValue), context)
    }

    override fun visit(
        node: Node.Composite,
        table: ContextVisitorTable,
        context: VisitorContext,
    ): VisitResult {
        if (!enforce || !isRelevantNode(node)) {
            return VisitResult(Outcome.Ok(NoneValue), context)
        }

        val children = node.children.toList()
        val assignIndex = children.indexOfFirst {
            it is Node.Leaf && it.type == AssignNode
        }

        if (assignIndex == -1) {
            return VisitResult(Outcome.Ok(NoneValue), context)
        }

        val updatedChildren = children.mapIndexed { i, child ->
            when {
                i == assignIndex -> {
                    val withoutTrailing = TriviaManipulator.removeTrailing(child, SpaceTrivia)
                    TriviaManipulator.removeLeading(withoutTrailing, SpaceTrivia)
                }
                i == assignIndex - 1 -> {
                    TriviaManipulator.removeTrailing(child, SpaceTrivia)
                }
                i == assignIndex + 1 -> {
                    TriviaManipulator.removeLeading(child, SpaceTrivia)
                }
                else -> child
            }
        }

        val updatedNode = node.copy(children = updatedChildren)
        return VisitResult(Outcome.Ok(NodeValue(updatedNode)), context)
    }

    private fun isRelevantNode(node: Node.Composite): Boolean {
        return node.type == LetDeclarationStatementNode ||
            node.type == ConstDeclarationStatementNode ||
            node.type == AssignStatementNode
    }
}
