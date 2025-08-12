package interpreter

import TokenType
import common.AbstractSyntaxTree
import common.BinOp
import common.Num
import common.UnaryOp
import parser.Parser

class Interpreter(private val parser: Parser) {

    fun eval(node: AbstractSyntaxTree): Int {

        if (node is Num) {
            return node.value
        }
        else if (node is BinOp) {
            val left = eval(node.left)
            val right = eval(node.right)
            if (node.op.type == TokenType.ADD) {
                return left + right
            }
            if (node.op.type == TokenType.SUB) {
                return left - right
            }
            if (node.op.type == TokenType.MUL) {
                return left * right
            }
            if (node.op.type == TokenType.DIV) {
                return left / right
            }
            error("Unsupported operator: ${node.op.type}")
        }
        else if (node is UnaryOp) {
            if (node.op.type == TokenType.ADD) {
                return +eval(node.expr)
            }
            if (node.op.type == TokenType.SUB) {
                return -eval(node.expr)
            }
            error("Unsupported unary op: ${node.op.type}")
        }

        return 0

    }
}
