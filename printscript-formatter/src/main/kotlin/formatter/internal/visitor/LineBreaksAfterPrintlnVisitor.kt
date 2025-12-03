package formatter.internal.visitor

import formatter.internal.manipulator.TriviaManipulator
import formatter.internal.model.context.PrintlnState
import formatter.internal.model.value.NodeValue
import formatter.internal.transformer.NodeTransformer
import model.node.Node
import model.node.PrintlnStatementNode
import model.trivia.NewlineTrivia
import model.trivia.Trivia
import model.value.NoneValue
import model.visitor.context.ContextVisitor
import model.visitor.context.ContextVisitorTable
import model.visitor.context.VisitResult
import model.visitor.context.VisitorContext
import type.option.Option
import type.outcome.Outcome

internal class LineBreaksAfterPrintlnVisitor(
    private val lineBreaks: Int,
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
        val state = getOrInitializeState(context)
        val isPrintln = node.type == PrintlnStatementNode

        val nodeWithoutLeadingNewlines = if (state.pendingNewlines.isNotEmpty()) {
            TriviaManipulator.removeLeading(node, NewlineTrivia)
        } else {
            node
        }
        val nodeWithLeading = TriviaManipulator.addLeading(
            nodeWithoutLeadingNewlines,
            state.pendingNewlines,
        )

        val transformed = NodeTransformer.transformRecursive(nodeWithLeading, table, context)

        val (nodeWithoutTrailing, extractedNewlines) =
            TriviaManipulator.extractTrailing(transformed, NewlineTrivia)

        val newlinesToTransfer = if (isPrintln) {
            calculateNewlinesAfterPrintln(nodeWithoutTrailing, extractedNewlines)
        } else {
            extractedNewlines
        }

        val newState = state.copy(
            pendingNewlines = newlinesToTransfer,
            hadPreviousPrintln = isPrintln,
        )
        val newContext = context.register(PrintlnState::class, newState)

        return VisitResult(Outcome.Ok(NodeValue(nodeWithoutTrailing)), newContext)
    }

    private fun getOrInitializeState(context: VisitorContext): PrintlnState {
        return when (val opt = context.get(PrintlnState::class)) {
            is Option.Some -> opt.value
            is Option.None -> PrintlnState()
        }
    }

    private fun calculateNewlinesAfterPrintln(
        node: Node,
        extractedNewlines: List<Trivia>,
    ): List<Trivia> {
        val currentNewlineCount = extractedNewlines.size
        val requiredNewlineCount = lineBreaks + 1

        return when {
            currentNewlineCount > requiredNewlineCount -> {
                extractedNewlines.take(requiredNewlineCount)
            }
            currentNewlineCount < requiredNewlineCount -> {
                val additionalNeeded = requiredNewlineCount - currentNewlineCount
                val additional = List(additionalNeeded) {
                    Trivia(NewlineTrivia, "\n", node.span)
                }
                extractedNewlines + additional
            }
            else -> {
                extractedNewlines
            }
        }
    }
}
