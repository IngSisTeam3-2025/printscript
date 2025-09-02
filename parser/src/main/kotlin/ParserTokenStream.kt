import source.TokenSource
import token.TokenTemp

class ParserTokenStream : TokenSource {
    private var lookahead: TokenTemp? = null

    private fun readNonWhiteSpaces(): TokenTemp {
        return lookahead!!
    }

    override fun next(): TokenTemp {
        val token = lookahead ?: readNonWhiteSpaces()
        lookahead = null
        return token
    }

    override fun peek(): TokenTemp {
        if (lookahead == null) lookahead = readNonWhiteSpaces()
        return lookahead!!
    }
}
