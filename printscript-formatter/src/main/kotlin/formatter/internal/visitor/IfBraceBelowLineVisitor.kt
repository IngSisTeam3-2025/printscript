package formatter.internal.visitor

import formatter.internal.manipulator.TriviaManipulator
import formatter.internal.model.value.NodeValue
import formatter.internal.transformer.NodeTransformer
import model.node.IfStatementNode
import model.node.LeftBraceNode
import model.node.Node
import model.trivia.NewlineTrivia
import model.trivia.SpaceTrivia
import model.trivia.Trivia
import model.value.NoneValue
import model.visitor.context.ContextVisitor
import model.visitor.context.ContextVisitorTable
import model.visitor.context.VisitResult
import model.visitor.context.VisitorContext
import type.outcome.Outcome

internal class IfBraceBelowLineVisitor(
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
        if (!enforce || node.type != IfStatementNode) {
            return VisitResult(Outcome.Ok(NoneValue), context)
        }

        val transformedChildren = node.children.map { child ->
            processChild(child)
        }

        val updatedNode = node.copy(children = transformedChildren)
        val transformed = NodeTransformer.transformRecursive(updatedNode, table, context)

        return VisitResult(Outcome.Ok(NodeValue(transformed)), context)
    }

    private fun processChild(node: Node): Node {
        return when (node) {
            is Node.Leaf -> {
                if (node.type == LeftBraceNode) {
                    val withoutSpaces = TriviaManipulator.removeLeading(node, SpaceTrivia)
                    val withoutNewlines = TriviaManipulator.removeLeading(
                        withoutSpaces,
                        NewlineTrivia,
                    )

                    val newline = listOf(Trivia(NewlineTrivia, "\n", node.span))
                    TriviaManipulator.addLeading(withoutNewlines, newline)
                } else {
                    node
                }
            }
            is Node.Composite -> {
                val updatedChildren = node.children.map { child -> processChild(child) }
                node.copy(children = updatedChildren)
            }
        }
    }
}
