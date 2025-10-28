package linter.internal.model.issue

import linter.internal.model.origin.LinterCategory
import model.diagnostic.Diagnostic
import model.diagnostic.category.Category
import model.diagnostic.severity.Severity
import model.rule.Rule
import model.span.Span

internal data class LintIssue(
    override val message: String,
    val span: Span,
    val rule: Rule,
    override val severity: Severity,
    override val category: Category = LinterCategory,
) : Diagnostic {
    override fun format(): String = "${span.format()}:$severity: $message [$rule] [${category.name}]"
}
