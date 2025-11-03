package lexer.internal.model.scan

import model.span.Span
import model.token.TokenType

internal sealed interface TokenScan {
    data class Ok(
        val type: TokenType,
        val lexeme: String,
        val span: Span,
    ) : TokenScan
    data object Error : TokenScan
    data object Empty : TokenScan
}
