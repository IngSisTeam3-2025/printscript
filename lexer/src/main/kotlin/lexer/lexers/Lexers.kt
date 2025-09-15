package lexer.lexer.lexers

import lexer.Lexer
import lexer.matcher.Match
import token.TokenType

class CharLexer(private val char: Char, override val type: TokenType) : Lexer() {
    override fun lex(input: String) =
        if (input.isNotEmpty() && input[0] == char) Match(1, type, true)
        else Match(0, TokenType("", ignore = true), false)
}

class DigitLexer(override val type: TokenType = TokenType("DIGIT")) : Lexer() {
    override fun lex(input: String) =
        if (input.isNotEmpty() && input[0].isDigit()) Match(1, type, true)
        else Match(0, TokenType("", ignore = true), false)
}

class LetterLexer(override val type: TokenType = TokenType("LETTER")) : Lexer() {
    override fun lex(input: String) =
        if (input.isNotEmpty() && input[0].isLetter()) Match(1, type, true)
        else Match(0, TokenType("", ignore = true), false)
}

class KeywordLexer(private val keyword: String, override val type: TokenType = TokenType(keyword.uppercase(), priority = 20)) : Lexer() {
    override fun lex(input: String) =
        if (input.startsWith(keyword)) Match(keyword.length, type, true)
        else Match(0, TokenType("", ignore = true), false)
}


class SequenceLexer(private val first: Lexer, private val second: Lexer, override val type: TokenType) : Lexer() {
    override fun lex(input: String): Match {
        val m1 = first.lex(input)
        if (!m1.complete) return Match(0, TokenType("", ignore = true), false)

        val m2 = second.lex(input.substring(m1.length))
        if (!m2.complete) return Match(0, TokenType("", ignore = true), false)

        return Match(m1.length + m2.length, type, true)
    }
}

class RepeatLexer(private val inner: Lexer, override val type: TokenType) : Lexer() {
    override fun lex(input: String): Match {
        var pos = 0
        while (pos < input.length) {
            val m = inner.lex(input.substring(pos))
            if (!m.complete || m.length == 0) break
            pos += m.length
        }
        return Match(pos, type, true)
    }
}

class OrLexer(private val a: Lexer, private val b: Lexer) : Lexer() {
    override val type: TokenType get() = a.type
    override fun lex(input: String): Match {
        val ma = a.lex(input)
        val mb = b.lex(input)
        return if (ma.length > mb.length) ma else mb
    }
}
