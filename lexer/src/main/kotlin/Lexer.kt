import error.LexError
import result.Result
import token.Token

@Suppress("UNUSED_PARAMETER")
class Lexer {

    fun lex(source: Sequence<Char>): Sequence<Result<Token, LexError>> = sequence {}
}
