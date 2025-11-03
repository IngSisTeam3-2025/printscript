package formatter.internal.visitor

import formatter.internal.manipulator.TriviaManipulator
import formatter.internal.model.value.DocValue
import formatter.internal.transformer.NodeTransformer
import formatter.internal.type.toDoc
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

internal class IfBraceSameLineVisitor(
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

        return VisitResult(Outcome.Ok(DocValue(transformed.toDoc())), context)
    }

    private fun processChild(node: Node): Node {
        return when (node) {
            is Node.Leaf -> {
                if (node.type == LeftBraceNode) {
                    val withoutNewlines = TriviaManipulator.removeLeading(node, NewlineTrivia)
                    val withoutSpaces = TriviaManipulator.removeLeading(
                        withoutNewlines,
                        SpaceTrivia,
                    )

                    val space = listOf(Trivia(SpaceTrivia, " ", node.span))
                    TriviaManipulator.addLeading(withoutSpaces, space)
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
