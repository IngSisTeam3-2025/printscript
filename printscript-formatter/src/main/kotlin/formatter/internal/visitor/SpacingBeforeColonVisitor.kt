package formatter.internal.visitor

import formatter.internal.manipulator.TriviaManipulator
import formatter.internal.model.value.DocValue
import formatter.internal.type.toDoc
import model.node.ColonNode
import model.node.ConstDeclarationStatementNode
import model.node.LetDeclarationStatementNode
import model.node.Node
import model.trivia.SpaceTrivia
import model.trivia.Trivia
import model.value.NoneValue
import model.visitor.context.ContextVisitor
import model.visitor.context.ContextVisitorTable
import model.visitor.context.VisitResult
import model.visitor.context.VisitorContext
import type.outcome.Outcome

internal class SpacingBeforeColonVisitor(
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
        if (!enforce || !isDeclarationStatement(node)) {
            return VisitResult(Outcome.Ok(NoneValue), context)
        }

        val children = node.children.toList()
        val colonIndex = children.indexOfFirst {
            it is Node.Leaf && it.type == ColonNode
        }

        if (colonIndex == -1) {
            return VisitResult(Outcome.Ok(NoneValue), context)
        }

        val colonNode = children[colonIndex]
        if (colonNode !is Node.Leaf) {
            return VisitResult(Outcome.Ok(NoneValue), context)
        }

        val prevNode = children.getOrNull(colonIndex - 1)
            ?: return VisitResult(Outcome.Ok(NoneValue), context)

        val colonWithoutSpaces = TriviaManipulator.removeLeading(colonNode, SpaceTrivia)
            as Node.Leaf
        val prevWithoutSpaces = TriviaManipulator.removeTrailing(prevNode, SpaceTrivia)

        val space = listOf(Trivia(SpaceTrivia, " ", colonNode.span))
        val updatedPrev = TriviaManipulator.addTrailing(prevWithoutSpaces, space)

        val updatedChildren = children.toMutableList().apply {
            set(colonIndex - 1, updatedPrev)
            set(colonIndex, colonWithoutSpaces)
        }

        val updatedNode = node.copy(children = updatedChildren)
        return VisitResult(Outcome.Ok(DocValue(updatedNode.toDoc())), context)
    }

    private fun isDeclarationStatement(node: Node.Composite): Boolean {
        return node.type == LetDeclarationStatementNode ||
            node.type == ConstDeclarationStatementNode
    }
}
