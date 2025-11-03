package parser.internal.model.error

import model.diagnostic.Diagnostic
import model.diagnostic.category.Category
import model.diagnostic.severity.Error
import model.diagnostic.severity.Severity
import model.span.Span

internal data class ParseError(
    override val message: String,
    override val category: Category,
    val span: Span,
    override val severity: Severity = Error,
) : Diagnostic {
    override fun format(): String {
        return "${span.format()} -> ${severity.name} (${category.name}): $message"
    }
}
