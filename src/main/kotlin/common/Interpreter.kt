package common

import Token
import TokenType
import scanning.Tokenizer

class Interpreter (private val tokenizer: Tokenizer) {
    private var currentToken : Token = tokenizer.getNextToken()
    private var depth = 0

    private fun error() {
        val column = tokenizer.column() - 1
        throw Error("Col: $column -> Parsing error:: Invalid expression")
    }

    private fun parse(tokenType: TokenType) {
        if (tokenType == this.currentToken.type) {
            this.currentToken = tokenizer.getNextToken()
        }
        else {
            error()
        }
    }

    private fun factor(): Int {
        if (currentToken.type == TokenType.INT) {
            val value = currentToken.lexeme.toInt()
            parse(TokenType.INT)
            return value
        }

        if (currentToken.type == TokenType.LPAREN) {
            parse(TokenType.LPAREN)
            depth++
            val value = expr()
            parse(TokenType.RPAREN)
            depth--
            return value
        }

        error()
        return 0
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

    internal fun expr(): Int {
        var res = term()
        while (currentToken.type == TokenType.ADD || currentToken.type == TokenType.SUB) {
            if (currentToken.type == TokenType.ADD) {
                parse(TokenType.ADD);
                res += term();
                continue
            }
            if (currentToken.type == TokenType.SUB) {
                parse(TokenType.SUB);
                res -= term();
                continue
            }
        }
        if (depth == 0 && currentToken.type != TokenType.EOF) error()
        return res
    }

    fun eval(): Int {
        val v = expr()
        if (currentToken.type != TokenType.EOF) error()
        return v
    }

}
