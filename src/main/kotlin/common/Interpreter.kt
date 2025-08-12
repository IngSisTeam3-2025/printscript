package common

import Token
import TokenType
import scanning.Tokenizer

class Interpreter (private val tokenizer: Tokenizer) {
    private var currentToken : Token = tokenizer.getNextToken()
    private var opTable = arrayOf(
        TokenType.ADD,
        TokenType.SUB,
    )

    private fun error() {
        val column = tokenizer.column() - 1
        throw Error("Col: $column -> Parsing error:: Invalid expression")
    }

    private fun parse(tokenType: TokenType) {

        if (tokenType == currentToken.type) {
            currentToken = tokenizer.getNextToken()
        }
        else error()
    }

    private fun factor() : Int {

        val token = this.currentToken
        this.parse(TokenType.INT)
        return token.lexeme.toInt()

    }

    private fun term(): Int {

        var res : Int = this.factor()

        while (currentToken.type == TokenType.MUL || currentToken.type == TokenType.DIV) {
            if (currentToken.type == TokenType.MUL) {
                this.parse(TokenType.MUL)
                res *= this.factor()
            }
            if (currentToken.type == TokenType.DIV) {
                this.parse(TokenType.DIV)
                res /= this.factor()
            }
        }

        return res

    }

    fun expr(): Int {
        var res = term()
        while (opTable.contains(currentToken.type)) {
            if (currentToken.type == TokenType.ADD) {
                parse(TokenType.ADD); res += term()
            }
            if (currentToken.type == TokenType.SUB) {
                parse(TokenType.SUB); res -= term()
            }
        }
        if (currentToken.type != TokenType.EOF) error()
        return res
    }

}
