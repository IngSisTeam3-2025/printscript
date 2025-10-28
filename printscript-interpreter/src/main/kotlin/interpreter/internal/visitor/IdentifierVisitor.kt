package interpreter.internal.visitor

import interpreter.internal.model.category.UndefinedIdentifier
import interpreter.internal.model.error.RuntimeError
import interpreter.internal.model.error.SystemError
import interpreter.internal.util.SymbolTable
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

internal class IdentifierVisitor : ContextVisitor {

    override fun visit(
        node: Node.Leaf,
        table: ContextVisitorTable,
        context: VisitorContext,
    ): VisitResult {
        if (node.type != IdentifierNode) return VisitResult(Outcome.Ok(NoneValue), context)

        val symbolTable = context.get(SymbolTable::class).getOrElse {
            val message = "Internal runtime error"
            return VisitResult(Outcome.Error(SystemError(message)), context)
        }

        val name = (node.value as StringValue).value

        val value = symbolTable.get(name).getOrElse {
            val message = "Identifier '$name' is undefined"
            val error = RuntimeError(
                message,
                UndefinedIdentifier,
                node.span,
            )
            return VisitResult(Outcome.Error(error), context)
        }
        return VisitResult(Outcome.Ok(value), context)
    }

    override fun visit(
        node: Node.Composite,
        table: ContextVisitorTable,
        context: VisitorContext,
    ): VisitResult = VisitResult(Outcome.Ok(NoneValue), context)
}
