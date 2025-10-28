package validator.internal.visitor

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
import type.outcome.Outcome
import type.outcome.getOrElse
import validator.internal.model.category.IncorrectMethodCall
import validator.internal.model.error.ValidationError
import validator.internal.model.value.RuntimeValue
import validator.internal.model.value.RuntimeValueType

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

        val children = node.children.toList()
        val arguments = children
            .dropWhile { it.type != LeftParenthesisNode }
            .drop(1)
            .takeWhile { it.type != RightParenthesisNode }

        if (arguments.isEmpty()) {
            val message = "readEnv() requires an argument"
            val error = ValidationError(message, IncorrectMethodCall, node.span)
            return VisitResult(Outcome.Error(error), context)
        }

        val argument = arguments[0]
        val visit = table.dispatch(argument, context)
        if (visit.outcome is Outcome.Error) return visit

        val value = visit.outcome.getOrElse { return visit }

        if (value is RuntimeValue) {
            return VisitResult(Outcome.Ok(RuntimeValue(RuntimeValueType)), visit.context)
        }

        if (value !is StringValue) {
            val message = "readEnv() argument must be a string, got ${value.type.name}"
            val error = ValidationError(message, IncorrectMethodCall, argument.span)
            return VisitResult(Outcome.Error(error), visit.context)
        }

        return VisitResult(Outcome.Ok(RuntimeValue(RuntimeValueType)), visit.context)
    }
}
