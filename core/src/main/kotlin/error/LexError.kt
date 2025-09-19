package error

import span.Span

data class LexError(val message: String, val span: Span)
