import lexer.TokenSource
import lexer.Tokenizer
import token.TokenType


class ParserTokenStream(private val tokenizer: Tokenizer) : TokenSource {
    private var lookahead: Token? = null

    private fun readNonWhiteSpaces(): Token {
        var token = tokenizer.getNextToken()
        while (token.type == TokenType.WHITESPACE) {
            token = tokenizer.getNextToken()
        }
        return token
    }

    override fun next(): Token {
        val token = lookahead ?: readNonWhiteSpaces()
        lookahead = null
        return token
    }

    override fun peek(): Token {
        if (lookahead == null) lookahead = readNonWhiteSpaces()
        return lookahead!!
    }
}
