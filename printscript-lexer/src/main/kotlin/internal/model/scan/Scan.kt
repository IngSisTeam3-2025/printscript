package internal.model.scan

import model.span.Span
import model.token.TokenType

internal sealed interface Scan {
    data object Empty : Scan
    data object Error : Scan
    data class Ok(
        val type: TokenType,
        val lexeme: String,
        val span: Span,
    ) : Scan
}