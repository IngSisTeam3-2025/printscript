package error

import span.Span

data class ParseError(val message: String, val span: Span)
