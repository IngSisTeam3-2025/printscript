package lexer

import Token
import token.TokenType


class Tokenizer(private val source: String) {
    private var column: Int = 0
    private var currentChar : Char? = source[0]
    private val RESERVED_KEYWORDS = mapOf(
        "let" to TokenType.LET,
        "number" to TokenType.INT,
        "string" to TokenType.STRING,
        "println" to TokenType.PRINTLN
    )

    private fun isIdStart(c: Char) = c.isLetter() || c == '_'
    private fun isIdPart(c: Char)  = c.isLetterOrDigit() || c == '_'

    fun column() : Int {
        return column
    }

    fun _id(): Token {
        var res = ""
        while (currentChar != null && isIdPart(currentChar!!)) {
            res += currentChar
            advance()
        }
        val lexeme = res
        val type = RESERVED_KEYWORDS[lexeme] ?: TokenType.ID
        return Token(type, lexeme)
    }

    fun peek(): Char? {
        return if (column + 1 > source.length - 1) {
            null
        } else {
            source[column + 1]
        }
    }

    private fun string(): Token {
        val quote = currentChar
        advance()
        var res = ""
        while (currentChar != null && currentChar != quote) {
            res += currentChar
            advance()
        }
        if (currentChar == quote) {
            advance()
        } else {
            throw Error("String sin cerrar en columna $column")
        }
        return Token(TokenType.STRING, res)
    }

    private fun number(): Int {
        var res = ""
        while (currentChar != null && currentChar!!.isDigit()) {
            res += currentChar.toString()
            advance()
        }
        if (currentChar == '.') {
            res += currentChar
            advance()
            while (currentChar != null && currentChar!!.isDigit()) {
                res += currentChar
                advance()
            }
        }
        return res.toInt()
    }

    fun getNextToken() : Token {
        while (currentChar != null) {

            if (currentChar!!.isWhitespace()) {
                val ch = currentChar!!
                advance()
                return Token(TokenType.WHITESPACE, ch.toString())
            }

            if (currentChar!!.isDigit()) {
                return Token(TokenType.INT, number().toString())
            }

            if (currentChar!! == '=') {
                advance()
                return Token(TokenType.ASSIGN, "=")
            }

            if (currentChar!! == ';') {
                advance()
                return Token(TokenType.SEMI, ";")
            }

            if (currentChar!! == ':') {
                advance()
                return Token(TokenType.COLON, ":")
            }

            if (currentChar!! == '.') {
                advance()
                return Token(TokenType.DOT, ".")
            }

            if (isIdStart(currentChar!!)) {
                return _id()
            }


            if (currentChar == '"' || currentChar == '\'') {
                return string()
            }

            if (currentChar!! == '+') {
                advance()
                return Token(TokenType.ADD, "+")
            }

            if (currentChar!! == '-') {
                advance()
                return Token(TokenType.SUB, "-")
            }

            if (currentChar!! == '/') {
                advance()
                return Token(TokenType.DIV, "/")
            }

            if (currentChar!! == '*') {
                advance()
                return Token(TokenType.MUL, "*")
            }

            if (currentChar!! == '(') {
                advance()
                return Token(TokenType.LPAREN, "(")
            }

            if (currentChar!! == ')') {
                advance()
                return Token(TokenType.RPAREN, ")")
            }
            error()
        }
        return Token(TokenType.EOF, "")
    }

    private fun error() {
        throw Error("Col: $column -> Lexical error:: Invalid character: '$currentChar'")
    }

    private fun advance() {
        column++

        currentChar = if (column > source.length - 1) {
            null
        } else {
            source[column]
        }

    }

}