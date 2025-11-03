package validator.internal.visitor

import model.node.IdentifierNode
import model.node.Node
import model.value.NoneValue
import model.value.StringValue
import model.visitor.context.ContextVisitor
import model.visitor.context.ContextVisitorTable
import model.visitor.context.VisitResult
import model.visitor.context.VisitorContext
import type.option.getOrElse
import type.outcome.Outcome
import validator.internal.model.category.UndefinedIdentifier
import validator.internal.model.error.SystemError
import validator.internal.model.error.ValidationError
import validator.internal.model.value.RuntimeValue
import validator.internal.util.StaticSymbolTable

internal class IdentifierVisitor : ContextVisitor {

    override fun visit(
        node: Node.Leaf,
        table: ContextVisitorTable,
        context: VisitorContext,
    ): VisitResult {
        if (node.type != IdentifierNode) {
            return VisitResult(Outcome.Ok(node.value), context)
        }

        val symbolTable = context.get(StaticSymbolTable::class).getOrElse {
            val error = SystemError("Internal validation error")
            return VisitResult(Outcome.Error(error), context)
        }

        val identifierName = when (val value = node.value) {
            is StringValue -> value.value
            else -> node.value.format()
        }

        val symbolInfo = symbolTable.get(identifierName).getOrElse {
            val message = "Identifier '$identifierName' not declared in this scope"
            val error = ValidationError(
                message,
                UndefinedIdentifier,
                node.span,
            )
            return VisitResult(Outcome.Error(error), context)
        }

        val resultValue = if (symbolInfo.value is RuntimeValue) {
            RuntimeValue(symbolInfo.declaredType)
        } else {
            symbolInfo.value
        }

        return VisitResult(Outcome.Ok(resultValue), context)
    }

    override fun visit(
        node: Node.Composite,
        table: ContextVisitorTable,
        context: VisitorContext,
    ): VisitResult = VisitResult(Outcome.Ok(NoneValue), context)
}
