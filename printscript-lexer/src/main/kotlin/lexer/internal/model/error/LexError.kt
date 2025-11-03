package lexer.internal.model.error

import model.diagnostic.Diagnostic
import model.diagnostic.category.Category
import model.diagnostic.severity.Error
import model.diagnostic.severity.Severity
import model.span.Span

data class LexError(
    override val message: String,
    override val category: Category,
    val span: Span,
    override val severity: Severity = Error,
) : Diagnostic {
    override fun format(): String {
        return "${span.format()} -> ${severity.name} (${category.name}): $message"
    }
}
