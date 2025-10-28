package interpreter.internal.visitor

import interpreter.internal.model.error.IOError
import io.reader.input.InputReader
import io.writer.OutputWriter
import model.node.LeftParenthesisNode
import model.node.Node
import model.node.ReadInputStatementNode
import model.node.RightParenthesisNode
import model.value.NoneValue
import model.value.transformer.ValueTransformer
import model.visitor.context.ContextVisitor
import model.visitor.context.ContextVisitorTable
import model.visitor.context.VisitResult
import model.visitor.context.VisitorContext
import type.option.Option
import type.option.getOrElse
import type.outcome.Outcome
import type.outcome.getOrElse

internal class ReadInputVisitor(
    private val parsers: Collection<ValueTransformer>,
) : ContextVisitor {

    override fun visit(
        node: Node.Leaf,
        table: ContextVisitorTable,
        context: VisitorContext,
    ): VisitResult = VisitResult(Outcome.Ok(NoneValue), context)

    override fun visit(
        node: Node.Composite,
        table: ContextVisitorTable,
        context: VisitorContext,
    ): VisitResult {
        if (node.type != ReadInputStatementNode) {
            return VisitResult(Outcome.Ok(NoneValue), context)
        }

        val reader = context.get(InputReader::class).getOrElse {
            val message = "Failed to access input channel"
            val error = IOError(message)
            return VisitResult(Outcome.Error(error), context)
        }

        val writer = context.get(OutputWriter::class).getOrElse {
            val message = "Failed to access output channel"
            val error = IOError(message)
            return VisitResult(Outcome.Error(error), context)
        }

        val children = node.children.toList()
        val arguments = children
            .dropWhile { it.type != LeftParenthesisNode }
            .drop(1)
            .takeWhile { it.type != RightParenthesisNode }

        if (arguments.isEmpty()) {
            return VisitResult(Outcome.Ok(NoneValue), context)
        }

        val promptNode = arguments[0]
        val promptVisit = table.dispatch(promptNode, context)
        if (promptVisit.outcome is Outcome.Error) return promptVisit

        val promptValue = promptVisit.outcome.getOrElse { return promptVisit }
        val prompt = promptValue.format()

        writer.write(sequenceOf(prompt))

        val input = reader.read().joinToString("") { it.toString() }

        for (parser in parsers) {
            val parsed = parser.parse(input)
            when (parsed) {
                is Option.Some -> return VisitResult(Outcome.Ok(parsed.value), context)
                is Option.None -> continue
            }
        }

        val message = "Could not transform input: $input"
        val error = IOError(message)
        return VisitResult(Outcome.Error(error), context)
    }
}
