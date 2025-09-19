package reporter

import span.Span

data class Diagnostic(
    val severity: Severity,
    val message: String,
    val span: Span,
)
