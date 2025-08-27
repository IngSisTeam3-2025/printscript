import ast.Assign
import ast.BinOp
import ast.ExprStmt
import ast.Num
import ast.PrintlnStmt
import ast.Program
import ast.Str
import ast.UnaryOp
import ast.Var
import ast.VarDecl
import token.TokenType

class Interpreter : NodeVisitor {

    private val env = mutableMapOf<String, RuntimeValue?>()

    private fun num(value: RuntimeValue): Int {
        if (value is RuntimeValue.Num) {
            return value.v
        } else {
            error("Se esperaba número, llegó $value")
        }
    }

    private fun ensureDeclared(name: String) {
        if (name !in env) error("Variable '$name' no definida")
    }

    override fun visitNum(node: Num): RuntimeValue =
        RuntimeValue.Num(node.value)

    override fun visitStr(node: Str): RuntimeValue =
        RuntimeValue.Str(node.value)

    override fun visitBinOp(node: BinOp): RuntimeValue {
        val left = visit(node.left)
        val right = visit(node.right)
        return when (node.op.type) {
            TokenType.ADD -> {
                when {
                    left is RuntimeValue.Num && right is RuntimeValue.Num ->
                        RuntimeValue.Num(left.v + right.v)
                    left is RuntimeValue.Str && right is RuntimeValue.Str ->
                        RuntimeValue.Str(left.v + right.v)
                    left is RuntimeValue.Str && right is RuntimeValue.Num ->
                        RuntimeValue.Str(left.v + right.v.toString())
                    left is RuntimeValue.Num && right is RuntimeValue.Str ->
                        RuntimeValue.Str(left.v.toString() + right.v)
                    else -> error("Operador + no soportado para tipos: $left y $right")
                }
            }
            TokenType.SUB -> {
                val l = num(left)
                val r = num(right)
                RuntimeValue.Num(l - r)
            }
            TokenType.MUL -> {
                val l = num(left)
                val r = num(right)
                RuntimeValue.Num(l * r)
            }
            TokenType.DIV -> {
                val l = num(left)
                val r = num(right)
                RuntimeValue.Num(l / r)
            }
            else -> error("Operador no soportado: ${node.op.type}")
        }
    }

    override fun visitUnaryOp(node: UnaryOp): RuntimeValue {
        val value = num(visit(node.expr))
        val res = when (node.op.type) {
            TokenType.ADD -> +value
            TokenType.SUB -> -value
            else -> error("Unario no soportado: ${node.op.type}")
        }
        return RuntimeValue.Num(res)
    }

    override fun visitVar(node: Var): RuntimeValue {
        val name = node.name.lexeme
        val binding = env[name] ?: error("Variable '$name' no definida")
        return binding
    }

    override fun visitAssign(node: Assign): RuntimeValue {
        val name = node.name.lexeme
        ensureDeclared(name)
        val value = visit(node.value)
        env[name] = value
        return value
    }

    override fun visitVarDecl(node: VarDecl): RuntimeValue {
        val name = node.name.lexeme
        if (env.containsKey(name)) error("Variable '$name' ya declarada")

        val init = if (node.init != null) visit(node.init!!) else RuntimeValue.Void

        val ty = node.annotatedType.lexeme
        if (ty == "number") {
            if (init !is RuntimeValue.Num && init !is RuntimeValue.Void) {
                error("Type error: expected number, got ${init::class.simpleName}")
            }
        } else if (ty == "string") {
            if (init !is RuntimeValue.Str && init !is RuntimeValue.Void) {
                error("Type error: expected string, got ${init::class.simpleName}")
            }
        } else {
            error("Unknown type '$ty'")
        }
        env[name] = if (init is RuntimeValue.Void) null else init
        return RuntimeValue.Void
    }

    override fun visitPrintln(node: PrintlnStmt): RuntimeValue {
        val out: RuntimeValue?
        if (node.arg != null) {
            out = visit(node.arg!!)
        } else {
            out = null
        }

        val text = when (out) {
            is RuntimeValue.Num -> out.v.toString()
            is RuntimeValue.Str -> out.v
            else -> ""
        }

        println(text)
        return RuntimeValue.Void
    }

    override fun visitExprStmt(node: ExprStmt): RuntimeValue {
        return visit(node.expr)
    }

    override fun visitProgram(node: Program): RuntimeValue {
        var last: RuntimeValue = RuntimeValue.Void
        for (s in node.statements) {
            val r = visit(s)
            if (r !is RuntimeValue.Void) {
                last = r
            }
        }
        return last
    }
}
