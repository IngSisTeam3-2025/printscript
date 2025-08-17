package parser

import Token
import TokenType
import ast.AbstractSyntaxTree
import ast.BinOp
import ast.Num
import ast.UnaryOp
import lexer.Tokenizer

class Parser(private val tokenizer: Tokenizer) {
    private var currentToken: Token = tokenizer.getNextToken()

    private fun error(msg: String = "Parsing error:: Invalid expression"): Nothing {
        val column = tokenizer.column() - 1
        throw Error("Col: $column -> $msg")
    }

    private fun parse(tokenType: TokenType) {
        if (tokenType == this.currentToken.type) {
            this.currentToken = tokenizer.getNextToken()
        } else {
            error("Expected $tokenType, got ${currentToken.type}")
        }
    }

    private fun factor(): AbstractSyntaxTree {
        if (currentToken.type == TokenType.ADD) {
            val token = currentToken
            parse(TokenType.ADD)
            return UnaryOp(token, factor())
        }
        if (currentToken.type == TokenType.SUB) {
            val token = currentToken
            parse(TokenType.SUB)
            return UnaryOp(token, factor())
        }
        if (currentToken.type == TokenType.INT) {
            val token = currentToken
            parse(TokenType.INT)
            return Num(token, token.lexeme.toInt())
        }
        if (currentToken.type == TokenType.LPAREN) {
            parse(TokenType.LPAREN)
            val node = expr()
            parse(TokenType.RPAREN)
            return node
        }
        error()
    }

    private fun term(): AbstractSyntaxTree {
        var node = factor()
        while (currentToken.type == TokenType.MUL || currentToken.type == TokenType.DIV) {
            val op = currentToken
            if (op.type == TokenType.MUL) parse(TokenType.MUL) else parse(TokenType.DIV)
            node = BinOp(node, op, factor())
        }
        return node
    }

    private fun expr(): AbstractSyntaxTree {
        var node = term()
        while (currentToken.type == TokenType.ADD || currentToken.type == TokenType.SUB) {
            val op = currentToken
            if (op.type == TokenType.ADD) parse(TokenType.ADD) else parse(TokenType.SUB)
            node = BinOp(node, op, term())
        }
        if (currentToken.type != TokenType.EOF && currentToken.type != TokenType.RPAREN) {
            error("Trailing input after expression")
        }
        return node
    }

    fun parseProgram(): AbstractSyntaxTree {
        val root = expr()
        if (currentToken.type != TokenType.EOF) {
            error("Unexpected tokens after expression")
        }
        return root
    }
}
