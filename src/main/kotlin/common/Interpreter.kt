package common

import Token
import TokenType
import scanning.Tokenizer

class Interpreter (private val tokenizer: Tokenizer) {
    private var currentToken : Token = tokenizer.getNextToken()
    private var opTable = arrayOf(
        TokenType.ADD,
        TokenType.SUB
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

    private fun operand() : Int {
        val token = currentToken
        parse(TokenType.INT)
        return token.lexeme.toInt()
    }

    fun expr() : Int {

        var res : Int = operand()

        while (opTable.contains(currentToken.type)) {
            if (currentToken.type == TokenType.ADD) {
                parse(TokenType.ADD)
                res += operand()
            }
            if (currentToken.type == TokenType.SUB) {
                parse(TokenType.SUB)
                res -= operand()
            }
        }

        if (currentToken.type != TokenType.EOF) {
            error()
        }

        return res
    }

}
