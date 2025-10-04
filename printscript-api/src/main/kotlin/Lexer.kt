import model.diagnostic.Diagnostic
import model.token.Token
import util.outcome.Outcome

interface Lexer {
    fun lex(chars: Sequence<Char>): Sequence<Outcome<Token, Diagnostic>>
}
