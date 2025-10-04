package internal.scanner

import model.span.Span

internal data class ScanErr(val message: String, val span: Span, val consumed: Int)
