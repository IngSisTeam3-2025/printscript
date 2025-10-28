package interpreter.internal.visitor

import interpreter.internal.model.category.InvalidOperandType
import interpreter.internal.model.category.UnsupportedOperator
import interpreter.internal.model.error.RuntimeError
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

        val lhs = (lhsVisit.outcome as Outcome.Ok).value
        val rhs = (rhsVisit.outcome as Outcome.Ok).value
        val operatorType = operatorNode.type
        val opSymbol = (operatorNode as Node.Leaf).value.format()

        if (!operations.containsKey(operatorType)) {
            val message = "Unsupported binary operator '$opSymbol'"
            val error = RuntimeError(message, UnsupportedOperator, operatorNode.span)
            return VisitResult(Outcome.Error(error), rhsVisit.context)
        }

        val operationList = operations.getValue(operatorType)
        for (operation in operationList) {
            when (val result = operation.apply(lhs, rhs)) {
                is OperationResult.Ok -> {
                    return VisitResult(Outcome.Ok(result.value), rhsVisit.context)
                }
                is OperationResult.Error -> {
                    val error = RuntimeError(
                        result.message,
                        InvalidOperandType,
                        operatorNode.span,
                    )
                    return VisitResult(Outcome.Error(error), rhsVisit.context)
                }
                is OperationResult.Unsupported -> continue
            }
        }

        val lhsType = lhs.type.name
        val rhsType = rhs.type.name
        val message = "Cannot apply operator '$opSymbol' to operands of type $lhsType and $rhsType"
        val error = RuntimeError(
            message,
            InvalidOperandType,
            node.span,
        )
        return VisitResult(Outcome.Error(error), rhsVisit.context)
    }
}
