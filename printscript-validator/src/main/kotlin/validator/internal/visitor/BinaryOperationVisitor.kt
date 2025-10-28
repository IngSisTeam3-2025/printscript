package validator.internal.visitor

import model.node.BinaryOperationExpressionNode
import model.node.Node
import model.node.NodeType
import model.value.NoneValue
import model.value.operation.BinaryValueOperation
import model.value.operation.OperationResult
import model.visitor.context.ContextVisitor
import model.visitor.context.ContextVisitorTable
import model.visitor.context.VisitResult
import model.visitor.context.VisitorContext
import type.outcome.Outcome
import validator.internal.model.category.InvalidValue
import validator.internal.model.category.TypeMismatch
import validator.internal.model.category.UnsupportedOperator
import validator.internal.model.error.ValidationError
import validator.internal.model.value.RuntimeValue

internal class BinaryOperationVisitor(
    private val operations: Map<NodeType, Collection<BinaryValueOperation>>,
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
        if (node.type != BinaryOperationExpressionNode) {
            return VisitResult(Outcome.Ok(NoneValue), context)
        }

        val children = node.children.toList()
        val lhsNode = children[0]
        val operatorNode = children[1]
        val rhsNode = children[2]

        val lhsVisit = table.dispatch(lhsNode, context)
        if (lhsVisit.outcome is Outcome.Error) return lhsVisit

        val rhsVisit = table.dispatch(rhsNode, lhsVisit.context)
        if (rhsVisit.outcome is Outcome.Error) return rhsVisit

        val lhsValue = (lhsVisit.outcome as Outcome.Ok).value
        val rhsValue = (rhsVisit.outcome as Outcome.Ok).value

        val operatorType = operatorNode.type
        val symbol = (operatorNode as Node.Leaf).value.format()

        if (!operations.containsKey(operatorType)) {
            val error = ValidationError(
                "Unsupported binary operator '$symbol'",
                UnsupportedOperator,
                operatorNode.span,
            )
            return VisitResult(Outcome.Error(error), rhsVisit.context)
        }

        if (lhsValue is RuntimeValue || rhsValue is RuntimeValue) {
            val lhsType = if (lhsValue is RuntimeValue) lhsValue.type else lhsValue.type
            val rhsType = if (rhsValue is RuntimeValue) rhsValue.type else rhsValue.type

            val ops = operations[operatorType] ?: run {
                val error = ValidationError(
                    "Unsupported binary operator '$symbol'",
                    UnsupportedOperator,
                    operatorNode.span,
                )
                return VisitResult(Outcome.Error(error), rhsVisit.context)
            }

            for (op in ops) {
                if (op.supports(lhsType, rhsType)) {
                    return VisitResult(Outcome.Ok(RuntimeValue(op.resultType)), rhsVisit.context)
                }
            }

            val lhs = lhsType.name
            val rhs = rhsType.name
            val error = ValidationError(
                "Cannot apply operator '$symbol' to operands of type $lhs and $rhs",
                TypeMismatch,
                node.span,
            )
            return VisitResult(Outcome.Error(error), rhsVisit.context)
        }

        val ops = operations.getValue(operatorType)
        for (op in ops) {
            when (val result = op.apply(lhsValue, rhsValue)) {
                is OperationResult.Ok -> {
                    return VisitResult(Outcome.Ok(result.value), rhsVisit.context)
                }
                is OperationResult.Error -> {
                    val error = ValidationError(
                        result.message,
                        InvalidValue,
                        operatorNode.span,
                    )
                    return VisitResult(Outcome.Error(error), rhsVisit.context)
                }
                is OperationResult.Unsupported -> continue
            }
        }

        val lhsType = lhsValue.type.name
        val rhsType = rhsValue.type.name
        val error = ValidationError(
            "Cannot apply operator '$symbol' to operands of type $lhsType and $rhsType",
            TypeMismatch,
            node.span,
        )
        return VisitResult(Outcome.Error(error), rhsVisit.context)
    }
}
