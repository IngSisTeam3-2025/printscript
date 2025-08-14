package interpreter

import common.AbstractSyntaxTree
import common.BinOp
import common.Num
import common.UnaryOp

interface NodeVisitor {

    fun visit(node: AbstractSyntaxTree): Int {
        if (node is Num) {
            return visitNum(node)
        }
        if (node is BinOp) {
            return visitBinOp(node)
        }
        if (node is UnaryOp) {
            return visitUnaryOp(node)
        }
        error("No visit method for ${'$'}{node::class.simpleName}")
    }

    fun visitNum(node: Num): Int

    fun visitBinOp(node: BinOp): Int

    fun visitUnaryOp(node: UnaryOp): Int
}