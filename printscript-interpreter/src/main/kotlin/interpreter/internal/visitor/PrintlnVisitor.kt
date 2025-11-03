package interpreter.internal.visitor

import interpreter.internal.model.error.IOError
import io.writer.OutputWriter
import model.node.LeftParenthesisNode
import model.node.Node
import model.node.PrintlnStatementNode
import model.node.RightParenthesisNode
import model.value.NoneValue
import model.visitor.context.ContextVisitor
import model.visitor.context.ContextVisitorTable
import model.visitor.context.VisitResult
import model.visitor.context.VisitorContext
import type.option.getOrElse
import type.outcome.Outcome
import type.outcome.getOrElse

internal class PrintlnVisitor : ContextVisitor {

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
        if (node.type != PrintlnStatementNode) {
            return VisitResult(Outcome.Ok(NoneValue), context)
        }

        val output = context.get(OutputWriter::class).getOrElse {
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
            output.write(sequenceOf("\n"))
            return VisitResult(Outcome.Ok(NoneValue), context)
        }

        val argument = arguments[0]
        val visit = table.dispatch(argument, context)
        if (visit.outcome is Outcome.Error) return visit

        val value = visit.outcome.getOrElse { return visit }

        output.write(sequenceOf(value.format() + "\n"))
        return VisitResult(Outcome.Ok(NoneValue), visit.context)
    }
}
