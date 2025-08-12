package common

import Token
import TokenType

class Interpreter (private val source: String) {
    private var column : Int = 0
    private var currentChar: Char? = source[0]
    private var currentToken : Token = Token(TokenType.SOF, "")
    private var opTable = arrayOf(
        TokenType.ADDITION,
        TokenType.SUBTRACTION
    )

    private fun error() {
        throw Error("Col: $column -> Error parsing source")
    }

    private fun next() {
        column++

        currentChar = if (column > source.length - 1) {
            null
        } else {
            source[column]
        }

    }

    private fun skipWhiteSpace() {
        while (currentChar != null && currentChar!!.isWhitespace()) {
            next()
        }
    }

    private fun integer() : Int {
        var res = ""
        while (currentChar != null && currentChar!!.isDigit()) {
            res += currentChar.toString()
            next()
        }
        return res.toInt();
    }

    private fun getNextToken() : Token {
        while (currentChar != null) {
            if (currentChar!!.isWhitespace()) {
                skipWhiteSpace()
                continue
            }

            if (currentChar!!.isDigit()) {
                return Token(TokenType.INTEGER, integer().toString())
            }

            if (currentChar!! == '+') {
                next()
                return Token(TokenType.ADDITION, "+")
            }

            if (currentChar!! == '-') {
                next()
                return Token(TokenType.SUBTRACTION, "-")
            }

            error()
        }
        return Token(TokenType.EOF, "")
    }

    private fun parse(tokenType: TokenType) {
        if (tokenType == currentToken.type) {
            currentToken = getNextToken()
        }
        else error()
    }

    private fun parseTerm() : Int {
        val token = currentToken
        parse(TokenType.INTEGER)
        return token.lexeme.toInt()
    }

    fun expr() : Int {
        currentToken = getNextToken()

        var res = parseTerm()

        while (opTable.contains(currentToken.type)) {
            if (currentToken.type == TokenType.ADDITION) {
                parse(TokenType.ADDITION)
                res += parseTerm()
            }
            if (currentToken.type == TokenType.SUBTRACTION) {
                parse(TokenType.SUBTRACTION)
                res -= parseTerm()
            }
        }

        return res
    }


}
