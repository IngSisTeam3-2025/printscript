package interpreter.internal.visitor

import interpreter.internal.model.category.TypeMismatch
import interpreter.internal.model.error.RuntimeError
import interpreter.internal.model.error.SystemError
import interpreter.internal.util.SymbolTable
import model.node.AssignNode
import model.node.IdentifierNode
import model.node.LetDeclarationStatementNode
import model.node.Node
import model.node.NodeType
import model.value.NoneValue
import model.value.type.ValueType
import model.visitor.context.ContextVisitor
import model.visitor.context.ContextVisitorTable
import model.visitor.context.VisitResult
import model.visitor.context.VisitorContext
import type.option.getOrElse
import type.outcome.Outcome
import type.outcome.getOrElse

internal class LetDeclarationVisitor(
    private val typeMap: Map<NodeType, ValueType>,
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
        if (node.type != LetDeclarationStatementNode) {
            return VisitResult(Outcome.Ok(NoneValue), context)
        }

        val symbolTable = context.get(SymbolTable::class).getOrElse {
            val message = "Internal runtime error"
            val error = SystemError(message)
            return VisitResult(Outcome.Error(error), context)
        }

        val children = node.children.toList()
        val identifierNodes = children.filter { it.type == IdentifierNode }
        if (identifierNodes.isEmpty()) {
            return VisitResult(Outcome.Ok(NoneValue), context)
        }

        val identifierNode = identifierNodes[0]
        val identifierName = when (identifierNode) {
            is Node.Leaf -> identifierNode.value.format()
            is Node.Composite -> return VisitResult(Outcome.Ok(NoneValue), context)
        }

        val assignNodes = children.filter { it.type == AssignNode }

        val value = if (assignNodes.isEmpty()) {
            NoneValue
        } else {
            val assignIndex = children.indexOf(assignNodes[0])
            val exprNodes = children.drop(assignIndex + 1)
            if (exprNodes.isEmpty()) {
                return VisitResult(Outcome.Ok(NoneValue), context)
            }

            val exprNode = exprNodes[0]
            val exprVisit = table.dispatch(exprNode, context)
            if (exprVisit.outcome is Outcome.Error) return exprVisit

            val exprValue = exprVisit.outcome.getOrElse { return exprVisit }

            val typeNodes = children.filter { it.type in typeMap.keys }
            if (typeNodes.isNotEmpty()) {
                val typeNode = typeNodes[0]
                val expectedType = typeMap[typeNode.type]

                if (expectedType != null) {
                    val actualType = exprValue.type
                    if (expectedType != actualType) {
                        val message = "Expected ${expectedType.name}, got ${actualType.name}"
                        val error = RuntimeError(
                            message,
                            TypeMismatch,
                            node.span,
                        )
                        return VisitResult(Outcome.Error(error), context)
                    }
                }
            }

            exprValue
        }

        val newTable = symbolTable.set(identifierName, value)
        val newContext = context.register(SymbolTable::class, newTable)
        return VisitResult(Outcome.Ok(NoneValue), newContext)
    }
}
