import ast.AbstractSyntaxTree
import ast.BinOp
import ast.Num
import ast.UnaryOp

import ast.*
import token.TokenType

class AstPrinter {
    fun print(node: AbstractSyntaxTree, indent: String = ""): String = when (node) {

        is Program -> node.statements.joinToString("\n") { s -> print(s, indent) }

        is ExprStmt -> print(node.expr, indent)

        is VarDecl -> buildString {
            append("$indent(VAR_DECL\n")
            append("$indent  NAME ${node.name.lexeme}\n")
            node.annotatedType?.let { ty ->
                append("$indent  TYPE ${ty.lexeme}\n")
            }
            node.init?.let { init ->
                append("$indent  INIT\n")
                append(print(init, "$indent    ")).append("\n")
            }
            append("$indent)")
        }

        is PrintlnStmt -> {
            if (node.arg == null) "$indent(PRINTLN)"
            else buildString {
                append("$indent(PRINTLN\n")
                append(print(node.arg, "$indent  ")).append("\n")
                append("$indent)")
            }
        }

        is Num -> "$indent${node.value}"

        is Str -> "$indent\"${node.value}\""

        is Var -> "$indent${node.name.lexeme}"

        is Assign -> buildString {
            append("$indent(ASSIGN\n")
            append("$indent  ${node.name.lexeme}\n")
            append(print(node.value, "$indent  ")).append("\n")
            append("$indent)")
        }

        is BinOp -> {
            val left = print(node.left, "$indent  ")
            val right = print(node.right, "$indent  ")
            buildString {
                append("$indent(${node.op.type}\n")
                append(left).append("\n")
                append(right).append("\n")
                append("$indent)")
            }
        }

        is UnaryOp -> {
            val expr = print(node.expr, "$indent  ")
            buildString {
                append("$indent(${node.op.type}\n")
                append(expr).append("\n")
                append("$indent)")
            }
        }

        else -> error("Nodo desconocido: ${node::class.simpleName}")
    }
}
