package validator.internal.visitor

import model.node.Node
import model.node.NodeType
import model.node.UnaryOperationNode
import model.value.NoneValue
import model.value.operation.OperationResult
import model.value.operation.UnaryValueOperation
import model.visitor.context.ContextVisitor
import model.visitor.context.ContextVisitorTable
import model.visitor.context.VisitResult
import model.visitor.context.VisitorContext
import type.outcome.Outcome
import type.outcome.getOrElse
import validator.internal.model.category.InvalidValue
import validator.internal.model.category.TypeMismatch
import validator.internal.model.category.UnsupportedOperator
import validator.internal.model.error.ValidationError
import validator.internal.model.value.RuntimeValue

internal class UnaryOperationVisitor(
    private val operations: Map<NodeType, Collection<UnaryValueOperation>>,
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
        if (node.type != UnaryOperationNode) {
            return VisitResult(Outcome.Ok(NoneValue), context)
        }

        val children = node.children.toList()
        val operatorNode = children[0]
        val operandNode = children[1]

        val visit = table.dispatch(operandNode, context)
        if (visit.outcome is Outcome.Error) return visit

        val operandValue = visit.outcome.getOrElse { return visit }
        val operatorType = operatorNode.type

        val ops = operations[operatorType] ?: run {
            val symbol = (operatorNode as Node.Leaf).value.format()
            val error = ValidationError(
                "Unsupported unary operator '$symbol'",
                UnsupportedOperator,
                operatorNode.span,
            )
            return VisitResult(Outcome.Error(error), visit.context)
        }

        if (operandValue is RuntimeValue) {
            for (op in ops) {
                if (op.supports(operandValue.type)) {
                    return VisitResult(Outcome.Ok(RuntimeValue(op.resultType)), visit.context)
                }
            }

            val symbol = (operatorNode as Node.Leaf).value.format()
            val operandType = operandValue.type.name
            val error = ValidationError(
                "Cannot apply operator '$symbol' to operand of type $operandType",
                TypeMismatch,
                operandNode.span,
            )
            return VisitResult(Outcome.Error(error), visit.context)
        }

        for (op in ops) {
            when (val result = op.apply(operandValue)) {
                is OperationResult.Ok -> {
                    return VisitResult(Outcome.Ok(result.value), visit.context)
                }
                is OperationResult.Error -> {
                    val error = ValidationError(result.message, InvalidValue, operandNode.span)
                    return VisitResult(Outcome.Error(error), visit.context)
                }
                is OperationResult.Unsupported -> continue
            }
        }

        val symbol = (operatorNode as Node.Leaf).value.format()
        val operandType = operandValue.type.name
        val error = ValidationError(
            "Cannot apply operator '$symbol' to operand of type $operandType",
            TypeMismatch,
            operandNode.span,
        )
        return VisitResult(Outcome.Error(error), visit.context)
    }
}
