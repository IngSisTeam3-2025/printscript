package interpreter.internal.visitor

import interpreter.internal.model.category.UndefinedIdentifier
import interpreter.internal.model.error.RuntimeError
import interpreter.internal.model.error.SystemError
import interpreter.internal.util.SymbolTable
import model.node.AssignNode
import model.node.AssignStatementNode
import model.node.IdentifierNode
import model.node.Node
import model.node.SemicolonNode
import model.value.NoneValue
import model.value.StringValue
import model.visitor.context.ContextVisitor
import model.visitor.context.ContextVisitorTable
import model.visitor.context.VisitResult
import model.visitor.context.VisitorContext
import type.option.getOrElse
import type.outcome.Outcome

internal class AssignmentVisitor : ContextVisitor {

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
        if (node.type != AssignStatementNode) return VisitResult(Outcome.Ok(NoneValue), context)

        val symbolTable = context.get(SymbolTable::class).getOrElse {
            val message = "Internal runtime error"
            return VisitResult(Outcome.Error(SystemError(message)), context)
        }

        val innerNodes = node.children
            .dropWhile { it.type != IdentifierNode }
            .takeWhile { it.type != SemicolonNode }
            .toList()

        val identifierNode = innerNodes.first { it.type == IdentifierNode }
        val identifierName = ((identifierNode as Node.Leaf).value as StringValue).value

        if (!symbolTable.contains(identifierName)) {
            val message = "Identifier '$identifierName' is not defined"
            return VisitResult(
                Outcome.Error(
                    RuntimeError(
                        message,
                        UndefinedIdentifier,
                        node.span,
                    ),
                ),
                context,
            )
        }

        val equalsIndex = innerNodes.indexOfFirst { it.type == AssignNode }
        val exprNode = innerNodes[equalsIndex + 1]

        val visit = table.dispatch(exprNode, context)
        if (visit.outcome is Outcome.Error) return visit

        val value = (visit.outcome as Outcome.Ok).value
        val newTable = symbolTable.set(identifierName, value)
        val newContext = visit.context.register(SymbolTable::class, newTable)
        return VisitResult(Outcome.Ok(NoneValue), newContext)
    }
}
