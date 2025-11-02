package linter.internal.visitor

import linter.internal.model.lint.Lint
import linter.internal.model.rule.IdentifierFormatRule
import model.diagnostic.Diagnostic
import model.diagnostic.severity.Warning
import model.node.IdentifierNode
import model.node.Node
import model.value.Value
import model.visitor.Visitor
import model.visitor.VisitorTable
import type.outcome.Outcome

internal class IdentifierFormatVisitor(
    private val formatName: String,
    private val regex: Regex,
) : Visitor {

    override fun visit(
        node: Node.Leaf,
        table: VisitorTable,
    ): Outcome<Value, Diagnostic> {
        if (node.type != IdentifierNode) {
            return Outcome.Ok(node.value)
        }

        val identifier = node.value.format()
        val isValid = regex.matches(identifier)

        return if (isValid) {
            Outcome.Ok(node.value)
        } else {
            Outcome.Error(
                Lint(
                    message = "Identifier '$identifier' does not follow $formatName format",
                    span = node.span,
                    rule = IdentifierFormatRule,
                    severity = Warning,
                ),
            )
        }
    }

    override fun visit(
        node: Node.Composite,
        table: VisitorTable,
    ): Outcome<Value, Diagnostic> = Outcome.Ok(model.value.NoneValue)
}
