package lexer.matcher.token

import lexer.matcher.Matcher
import token.TokenType

class KeywordMatcher(private val keyword: String, private val type: TokenType) :
    Matcher<TokenMatch> {
    override fun match(input: CharSequence): TokenMatch? {
        if (!input.startsWith(keyword)) return null
        if (input.length > keyword.length && input[keyword.length].isLetterOrDigit()) return null
        return object : TokenMatch {
            override val type = this@KeywordMatcher.type
            override val value = keyword
            override val length = keyword.length
        }
    }
}

class IdentifierMatcher : Matcher<TokenMatch> {
    override fun match(input: CharSequence): TokenMatch? {
        if (input.isEmpty() || !input[0].isLetter()) return null
        var len = 1
        while (len < input.length && input[len].isLetterOrDigit()) len++
        val value = input.subSequence(0, len).toString()
        return object : TokenMatch {
            override val type = TokenType.IDENTIFIER
            override val value = value
            override val length = len
        }
    }
}

class NumberMatcher : Matcher<TokenMatch> {
    private val regex = Regex("""\d+(\.\d+)?""")

    override fun match(input: CharSequence): TokenMatch? {
        val match = regex.find(input, 0) ?: return null
        if (match.range.first != 0) return null

        return object : TokenMatch {
            override val type = TokenType.NUMBER
            override val value = match.value
            override val length = match.value.length
        }
    }
}

class StringMatcher : Matcher<TokenMatch> {
    override fun match(input: CharSequence): TokenMatch? {
        if (input.isEmpty() || (input[0] != '"' && input[0] != '\'')) return null
        val quote = input[0]
        var len = 1
        while (len < input.length && input[len] != quote) len++
        if (len >= input.length) return null
        len++
        return object : TokenMatch {
            override val type = TokenType.STRING
            override val value = input.subSequence(0, len).toString()
            override val length = len
        }
    }
}

class BooleanLiteralMatcher : Matcher<TokenMatch> {
    private val literals = listOf("true", "false")
    override fun match(input: CharSequence): TokenMatch? {
        val lit = literals.find { input.startsWith(it) } ?: return null
        return object : TokenMatch {
            override val type = TokenType.BOOLEAN_LITERAL
            override val value = lit
            override val length = lit.length
        }
    }
}

class SymbolMatcher(private val symbol: Char, private val type: TokenType) : Matcher<TokenMatch> {
    override fun match(input: CharSequence): TokenMatch? {
        if (input.isEmpty() || input[0] != symbol) return null
        return object : TokenMatch {
            override val type = this@SymbolMatcher.type
            override val value = symbol.toString()
            override val length = 1
        }
    }
}
