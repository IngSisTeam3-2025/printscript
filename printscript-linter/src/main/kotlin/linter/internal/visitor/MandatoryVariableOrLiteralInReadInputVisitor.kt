package linter.internal.visitor

import linter.internal.model.lint.Lint
import linter.internal.model.rule.MandatoryIdentifierOrLiteralInReadInputRule
import model.diagnostic.Diagnostic
import model.diagnostic.severity.Warning
import model.node.Node
import model.node.ReadInputExpressionNode
import model.value.NoneValue
import model.value.Value
import model.visitor.Visitor
import model.visitor.VisitorTable
import type.outcome.Outcome

internal class MandatoryVariableOrLiteralInReadInputVisitor(
    private val enforce: Boolean,
) : Visitor {

    override fun visit(
        node: Node.Leaf,
        table: VisitorTable,
    ): Outcome<Value, Diagnostic> = Outcome.Ok(node.value)

    override fun visit(
        node: Node.Composite,
        table: VisitorTable,
    ): Outcome<Value, Diagnostic> {
        if (!enforce) return Outcome.Ok(NoneValue)

        if (node.type == ReadInputExpressionNode) {
            if (node.children.size >= 3) {
                val argument = node.children.toList()[2]

                if (argument is Node.Composite) {
                    return Outcome.Error(
                        Lint(
                            message = "readInput() must take an identifier or literal argument",
                            span = argument.span,
                            rule = MandatoryIdentifierOrLiteralInReadInputRule,
                            severity = Warning,
                        ),
                    )
                }
            }
        }
        return Outcome.Ok(NoneValue)
    }
}
