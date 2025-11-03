package validator.internal.visitor

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
import validator.internal.model.category.InvalidType
import validator.internal.model.category.Redeclaration
import validator.internal.model.category.TypeMismatch
import validator.internal.model.error.SystemError
import validator.internal.model.error.ValidationError
import validator.internal.model.symbol.StaticSymbol
import validator.internal.model.value.RuntimeValue
import validator.internal.model.value.RuntimeValueType
import validator.internal.util.StaticSymbolTable

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

        val symbolTable = context.get(StaticSymbolTable::class).getOrElse {
            val error = SystemError("Internal validation error")
            return VisitResult(Outcome.Error(error), context)
        }

        val children = node.children.toList()
        val identifierNode = children.first { it.type == IdentifierNode } as Node.Leaf
        val identifier = identifierNode.value.format()

        if (symbolTable.contains(identifier)) {
            val message = "Identifier '$identifier' already declared in this scope"
            val error = ValidationError(
                message,
                Redeclaration,
                identifierNode.span,
            )
            return VisitResult(Outcome.Error(error), context)
        }

        val typeNode = (children.first { it.type in typeMap.keys } as Node.Leaf)
        val declaredType = typeMap[typeNode.type] ?: run {
            val error = ValidationError(
                "Invalid type '${typeNode.value.format()}'",
                InvalidType,
                typeNode.span,
            )
            return VisitResult(Outcome.Error(error), context)
        }

        val assignNodes = children.filter { it.type == AssignNode }

        if (assignNodes.isEmpty()) {
            val symbol = StaticSymbol(
                identifier,
                NoneValue,
                declaredType,
                node.span,
                true,
            )
            val newTable = symbolTable.set(identifier, symbol)
            val newContext = context.register(StaticSymbolTable::class, newTable)
            return VisitResult(Outcome.Ok(NoneValue), newContext)
        }

        val assignNode = assignNodes[0]
        val assignIndex = children.indexOf(assignNode)
        val exprNode = children[assignIndex + 1]

        val exprVisit = table.dispatch(exprNode, context)

        if (exprVisit.outcome is Outcome.Error) {
            val symbol = StaticSymbol(
                identifier,
                RuntimeValue(declaredType),
                declaredType,
                node.span,
                true,
            )
            val newTable = symbolTable.set(identifier, symbol)
            val newContext = context.register(StaticSymbolTable::class, newTable)

            return VisitResult(exprVisit.outcome, newContext)
        }

        val exprValue = exprVisit.outcome.getOrElse { return exprVisit }

        val actualType = if (exprValue is RuntimeValue) {
            exprValue.type
        } else {
            exprValue.type
        }

        if (actualType != RuntimeValueType && declaredType != actualType) {
            val message = "Expected ${declaredType.name}, got ${actualType.name}"
            val error = ValidationError(message, TypeMismatch, exprNode.span)

            val symbol = StaticSymbol(
                identifier,
                exprValue,
                declaredType,
                node.span,
                true,
            )
            val newTable = symbolTable.set(identifier, symbol)
            val newContext = context.register(StaticSymbolTable::class, newTable)

            return VisitResult(Outcome.Error(error), newContext)
        }

        val symbol = StaticSymbol(
            identifier,
            exprValue,
            declaredType,
            node.span,
            true,
        )

        val newTable = symbolTable.set(identifier, symbol)
        val newContext = context.register(StaticSymbolTable::class, newTable)
        return VisitResult(Outcome.Ok(NoneValue), newContext)
    }
}
