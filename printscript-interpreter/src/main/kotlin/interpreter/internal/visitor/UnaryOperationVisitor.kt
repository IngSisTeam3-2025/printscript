package interpreter.internal.visitor

import interpreter.internal.model.category.InvalidOperandType
import interpreter.internal.model.category.UnsupportedOperator
import interpreter.internal.model.error.IOError
import interpreter.internal.model.error.RuntimeError
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
        val operator = children[0]
        val operand = children[1]

        val visit = table.dispatch(operand, context)
        if (visit.outcome is Outcome.Error) return visit

        val value = visit.outcome.getOrElse { return visit }
        val type = operator.type
        val opSymbol = (operator as Node.Leaf).value.format()

        if (!operations.containsKey(type)) {
            val message = "Unsupported unary operator '$opSymbol'"
            val error = RuntimeError(
                message,
                UnsupportedOperator,
                operator.span,
            )
            return VisitResult(Outcome.Error(error), visit.context)
        }

        val ops = operations.getValue(type)
        for (op in ops) {
            when (val result = op.apply(value)) {
                is OperationResult.Ok -> {
                    return VisitResult(Outcome.Ok(result.value), visit.context)
                }
                is OperationResult.Error -> {
                    val error = IOError(result.message)
                    return VisitResult(Outcome.Error(error), visit.context)
                }
                is OperationResult.Unsupported -> {
                    continue
                }
            }
        }

        val operandType = value.type.name
        val message = "Cannot apply operator '$opSymbol' to operand of type $operandType"
        val error = RuntimeError(
            message,
            InvalidOperandType,
            operand.span,
        )
        return VisitResult(Outcome.Error(error), visit.context)
    }
}
