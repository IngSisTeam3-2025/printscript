package linter.internal.model.lint

import linter.internal.model.origin.Lint
import model.diagnostic.Diagnostic
import model.diagnostic.category.Category
import model.diagnostic.severity.Severity
import model.rule.RuleType
import model.span.Span

internal data class Lint(
    override val message: String,
    val span: Span,
    val rule: RuleType,
    override val severity: Severity,
    override val category: Category = Lint,
) : Diagnostic {
    override fun format(): String {
        return "${span.format()} -> $severity (${category.name}): [$rule] $message"
    }
}
