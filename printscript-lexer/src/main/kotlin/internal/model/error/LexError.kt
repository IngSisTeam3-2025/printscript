package internal.model.error

import model.diagnostic.Diagnostic
import model.origin.LexerOrigin
import model.origin.Origin
import model.severity.ErrorSeverity
import model.severity.Severity
import model.span.Span

data class LexError(
    override val message: String,
    val span: Span,
    override val severity: Severity = ErrorSeverity,
    override val origin: Origin = LexerOrigin,
) : Diagnostic {
    override fun format(): String = "${span.format()}:${severity.name}: $message [${origin.name}]"
}