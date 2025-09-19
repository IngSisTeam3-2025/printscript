package error

import span.Span

data class RuntimeError(val message: String, val span: Span)
