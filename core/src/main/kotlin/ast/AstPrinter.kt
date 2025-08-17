import ast.AbstractSyntaxTree
import ast.BinOp
import ast.Num
import ast.UnaryOp

class AstPrinter {
    fun print(node: AbstractSyntaxTree, indent: String = ""): String {
        if (node is Num) {
            return "$indent${node.value}"
        }
        if (node is BinOp) {
            val left = print(node.left, "$indent  ")
            val right = print(node.right, "$indent  ")
            return buildString {
                append("$indent(${node.op.type}\n")
                append(left).append("\n")
                append(right).append("\n")
                append(indent).append(")")
            }
        }
        if (node is UnaryOp) {
            val expr = print(node.expr, "$indent  ")
            return buildString {
                append("$indent(${node.op.type}\n")
                append(expr).append("\n")
                append(indent).append(")")
            }
        }
        error("Nodo desconocido: ${node::class.simpleName}")
    }
}
