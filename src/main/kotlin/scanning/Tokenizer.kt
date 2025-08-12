package scanning

import Token

class Tokenizer(private val source: String) {
    private var column: Int = 0
    private var currentChar : Char? = source[0]

    fun column() : Int {
        return column
    }

    fun getNextToken() : Token {
        while (currentChar != null) {
            if (currentChar!!.isWhitespace()) {
                advance()
                continue
            }

            if (currentChar!!.isDigit()) {
                return Token(TokenType.INT, integer().toString())
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
                return Token(TokenType.MUL, "(")
            }

            if (currentChar!! == ')') {
                advance()
                return Token(TokenType.MUL, ")")
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

    private fun integer() : Int {
        var res = ""
        while (currentChar != null && currentChar!!.isDigit()) {
            res += currentChar.toString()
            advance()
        }
        return res.toInt();
    }

}