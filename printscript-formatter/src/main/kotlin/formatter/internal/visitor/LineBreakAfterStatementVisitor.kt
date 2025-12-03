package formatter.internal.visitor

import formatter.internal.manipulator.TriviaManipulator
import formatter.internal.model.context.LineBreakState
import formatter.internal.model.value.NodeValue
import formatter.internal.transformer.NodeTransformer
import formatter.internal.traversal.NodeTraversal
import model.node.Node
import model.node.SemicolonNode
import model.trivia.NewlineTrivia
import model.trivia.Trivia
import model.value.NoneValue
import model.visitor.context.ContextVisitor
import model.visitor.context.ContextVisitorTable
import model.visitor.context.VisitResult
import model.visitor.context.VisitorContext
import type.option.Option
import type.outcome.Outcome

internal class LineBreakAfterStatementVisitor(
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
        if (!enforce) {
            val newContext = initializeStateIfNeeded(context)
            return VisitResult(Outcome.Ok(NoneValue), newContext)
        }

        val state = getOrInitializeState(context)

        val (nodeWithoutOriginalLeading, originalLeadingNewlines) =
            TriviaManipulator.extractLeading(node, NewlineTrivia)

        val nodeWithLeading = if (state.isFirstNode) {
            nodeWithoutOriginalLeading
        } else {
            val newlinesToAdd = if (originalLeadingNewlines.isEmpty()) {
                state.pendingNewlines
            } else {
                originalLeadingNewlines
            }
            TriviaManipulator.addLeading(nodeWithoutOriginalLeading, newlinesToAdd)
        }

        val transformed = NodeTransformer.transformRecursive(nodeWithLeading, table, context)

        val (nodeWithoutTrailing, extractedNewlines) =
            TriviaManipulator.extractTrailing(transformed, NewlineTrivia)

        val newlinesToTransfer = calculateNewlinesToTransfer(
            nodeWithoutTrailing,
            extractedNewlines,
        )

        val newState = LineBreakState(pendingNewlines = newlinesToTransfer, isFirstNode = false)
        val newContext = context.register(LineBreakState::class, newState)

        return VisitResult(Outcome.Ok(NodeValue(nodeWithoutTrailing)), newContext)
    }

    private fun initializeStateIfNeeded(context: VisitorContext): VisitorContext {
        return if (context.get(LineBreakState::class) is Option.None) {
            context.register(LineBreakState::class, LineBreakState())
        } else {
            context
        }
    }

    private fun getOrInitializeState(context: VisitorContext): LineBreakState {
        return when (val opt = context.get(LineBreakState::class)) {
            is Option.Some -> opt.value
            is Option.None -> LineBreakState()
        }
    }

    private fun calculateNewlinesToTransfer(
        node: Node,
        extractedNewlines: List<Trivia>,
    ): List<Trivia> {
        val endsWithSemicolon = NodeTraversal.endsWith(node, SemicolonNode)

        return if (endsWithSemicolon) {
            if (extractedNewlines.isEmpty()) {
                listOf(Trivia(NewlineTrivia, "\n", node.span))
            } else {
                extractedNewlines
            }
        } else {
            extractedNewlines
        }
    }
}
