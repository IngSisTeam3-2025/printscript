package interpreter.internal.visitor

import interpreter.internal.model.error.IOError
import interpreter.internal.model.error.SystemError
import io.reader.env.EnvReader
import model.node.LeftParenthesisNode
import model.node.Node
import model.node.ReadEnvExpressionNode
import model.node.RightParenthesisNode
import model.value.NoneValue
import model.value.StringValue
import model.visitor.context.ContextVisitor
import model.visitor.context.ContextVisitorTable
import model.visitor.context.VisitResult
import model.visitor.context.VisitorContext
import type.option.getOrElse
import type.outcome.Outcome
import type.outcome.getOrElse

internal class ReadEnvVisitor : ContextVisitor {

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
        if (node.type != ReadEnvExpressionNode) {
            return VisitResult(Outcome.Ok(NoneValue), context)
        }

        val envReader = context.get(EnvReader::class).getOrElse {
            val message = "Internal runtime error"
            val error = SystemError(message)
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

        val keyNode = arguments[0]
        val keyVisit = table.dispatch(keyNode, context)
        if (keyVisit.outcome is Outcome.Error) return keyVisit

        val keyValue = keyVisit.outcome.getOrElse { return keyVisit }

        if (keyValue !is StringValue) {
            val message = "readEnv() argument must be a string, got ${keyValue.type.name}"
            val error = IOError(message)
            return VisitResult(Outcome.Error(error), context)
        }

        val key = keyValue.value

        val envValue = envReader.read(key).getOrElse {
            val message = "Environment variable '$key' not found"
            val error = IOError(message)
            return VisitResult(Outcome.Error(error), context)
        }

        return VisitResult(Outcome.Ok(envValue), context)
    }
}
