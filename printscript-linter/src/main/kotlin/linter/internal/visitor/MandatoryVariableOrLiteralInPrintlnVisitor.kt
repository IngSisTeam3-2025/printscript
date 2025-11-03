package linter.internal.visitor

import linter.internal.model.lint.Lint
import linter.internal.model.rule.MandatoryIdentifierOrLiteralInPrintlnRule
import model.diagnostic.Diagnostic
import model.diagnostic.severity.Warning
import model.node.Node
import model.node.PrintlnStatementNode
import model.value.NoneValue
import model.value.Value
import model.visitor.Visitor
import model.visitor.VisitorTable
import type.outcome.Outcome

internal class MandatoryVariableOrLiteralInPrintlnVisitor(
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

        if (node.type == PrintlnStatementNode) {
            if (node.children.size >= 3) {
                val argument = node.children.toList()[2]

                if (argument is Node.Composite) {
                    return Outcome.Error(
                        Lint(
                            message = "println() must take an identifier or literal argument",
                            span = argument.span,
                            rule = MandatoryIdentifierOrLiteralInPrintlnRule,
                            severity = Warning,
                        ),
                    )
                }
            }
        }
        return Outcome.Ok(NoneValue)
    }
}
