import model.diagnostic.Diagnostic
import model.token.Token
import type.outcome.Outcome

interface Lexer {
    fun lex(
        version: String,
        chars: Sequence<Char>,
    ): Sequence<Outcome<Token, Diagnostic>>
}
