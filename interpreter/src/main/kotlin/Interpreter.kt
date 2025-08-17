package interpreter

import ast.AbstractSyntaxTree
import ast.BinOp
import ast.Num
import ast.UnaryOp

class Interpreter : NodeVisitor {


    override fun visitNum(node: Num): Int {
        return node.value
    }

    override fun visitBinOp(node: BinOp): Int {
        val left = visit(node.left)
        val right = visit(node.right)
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

    override fun visitUnaryOp(node: UnaryOp): Int {
        if (node.op.type == TokenType.ADD) {
            return +visit(node.expr)
        }
        if (node.op.type == TokenType.SUB) {
            return -visit(node.expr)
        }
        error("Unsupported unary op: ${node.op.type}")
    }

    fun interpret(ast: AbstractSyntaxTree): Int {
        return visit(ast)
    }
}
