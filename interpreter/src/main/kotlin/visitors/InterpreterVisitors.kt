package visitors

import ast.AssignmentNode
import ast.AstNode
import ast.BinaryOp
import ast.BinaryOpNode
import ast.IfNode
import ast.LiteralNode
import ast.PrintNode
import ast.ReadEnvNode
import ast.ReadInputNode
import ast.VarDeclNode
import ast.VariableNode
import provider.DependencyProvider
import table.SymbolTable
import visitor.AstVisitor
import visitor.VisitResult
import visitor.VisitorDispatcher
import java.io.IOError

class LiteralVisitor : AstVisitor {
    override fun visit(node: AstNode, dispatcher: VisitorDispatcher, provider: DependencyProvider): VisitResult {
        if (node !is LiteralNode) return VisitResult.NotHandled(node)
        return VisitResult.Success(node.value)
    }
}

class VariableVisitor : AstVisitor {
    override fun visit(node: AstNode, dispatcher: VisitorDispatcher, provider: DependencyProvider): VisitResult {
        if (node !is VariableNode) return VisitResult.NotHandled(node)

        val table = provider.get(SymbolTable::class.java).getOrElse {
            return VisitResult.Error("Missing symbol table", node.start(), node.end())
        }

        return table.lookup(node.name).fold(
            { VisitResult.Success(it) },
            { VisitResult.Error(it.message ?: "Undefined variable ${node.name}", node.start(), node.end()) },
        )
    }
}

class BinaryOpVisitor : AstVisitor {
    override fun visit(node: AstNode, dispatcher: VisitorDispatcher, provider: DependencyProvider): VisitResult {
        if (node !is BinaryOpNode) return VisitResult.NotHandled(node)

        val left = when (val res = dispatcher.dispatch(node.left, provider)) {
            is VisitResult.Success -> res.value
            is VisitResult.Error -> return res
            else -> return VisitResult.Error("Left operand failed", node.start(), node.end())
        }

        val right = when (val res = dispatcher.dispatch(node.right, provider)) {
            is VisitResult.Success -> res.value
            is VisitResult.Error -> return res
            else -> return VisitResult.Error("Right operand failed", node.start(), node.end())
        }

        return try {
            when (node.op) {
                BinaryOp.ADD -> {
                    when {
                        left is String || right is String -> VisitResult.Success(left.toString() + right.toString())
                        left is Number && right is Number -> VisitResult.Success(left.toDouble() + right.toDouble())
                        else -> VisitResult.Error("Invalid operands for +", node.start(), node.end())
                    }
                }
                BinaryOp.SUB -> if (left is Number && right is Number) {
                    VisitResult.Success(left.toDouble() - right.toDouble())
                } else {
                    VisitResult.Error("Invalid operands for -", node.start(), node.end())
                }

                BinaryOp.MUL -> if (left is Number && right is Number) {
                    VisitResult.Success(left.toDouble() * right.toDouble())
                } else {
                    VisitResult.Error("Invalid operands for *", node.start(), node.end())
                }

                BinaryOp.DIV -> if (left is Number && right is Number) {
                    VisitResult.Success(left.toDouble() / right.toDouble())
                } else {
                    VisitResult.Error("Invalid operands for /", node.start(), node.end())
                }
            }
        } catch (e: UnsupportedOperationException) {
            VisitResult.Error("Error in binary operation: ${e.message}", node.start(), node.end())
        }
    }
}

class IfVisitor : AstVisitor {
    override fun visit(node: AstNode, dispatcher: VisitorDispatcher, provider: DependencyProvider): VisitResult {
        if (node !is IfNode) return VisitResult.NotHandled(node)

        val condition = when (val res = dispatcher.dispatch(node.condition, provider)) {
            is VisitResult.Success -> res.value
            is VisitResult.Error -> return res
            else -> return VisitResult.Error("Condition failed", node.start(), node.end())
        }

        if (condition !is Boolean) {
            return VisitResult.Error("Condition must be boolean", node.start(), node.end())
        }

        val block = if (condition) node.thenBlock else node.elseBlock ?: emptyList()

        for (stmt in block) {
            val res = dispatcher.dispatch(stmt, provider)
            if (res is VisitResult.Error) return res
        }

        return VisitResult.Success(Unit)
    }
}

class ReadEnvVisitor : AstVisitor {
    override fun visit(node: AstNode, dispatcher: VisitorDispatcher, provider: DependencyProvider): VisitResult {
        if (node !is ReadEnvNode) return VisitResult.NotHandled(node)

        val env = provider.get(Map::class.java as Class<Map<String, String>>).getOrElse {
            return VisitResult.Error("Missing environment provider", node.start(), node.end())
        }

        val value = env[node.key]
        return if (value == null) {
            VisitResult.Error("Environment variable ${node.key} not found", node.start(), node.end())
        } else {
            VisitResult.Success(value)
        }
    }
}

class ReadInputVisitor : AstVisitor {
    override fun visit(node: AstNode, dispatcher: VisitorDispatcher, provider: DependencyProvider): VisitResult {
        if (node !is ReadInputNode) return VisitResult.NotHandled(node)

        val input = provider.get(java.io.Reader::class.java).getOrElse {
            return VisitResult.Error("Missing input source", node.start(), node.end())
        }

        val output = provider.get(java.io.Writer::class.java).getOrElse {
            return VisitResult.Error("Missing output", node.start(), node.end())
        }

        try {
            output.write(node.prompt)
            output.flush()
            val buf = input.readLines().firstOrNull() ?: ""
            return VisitResult.Success(buf)
        } catch (e: IOError) {
            return VisitResult.Error("Input failed: ${e.message}", node.start(), node.end())
        }
    }
}

class PrintVisitor : AstVisitor {
    override fun visit(node: AstNode, dispatcher: VisitorDispatcher, provider: DependencyProvider): VisitResult {
        if (node !is PrintNode) return VisitResult.NotHandled(node)

        val out = provider.get(java.io.Writer::class.java).getOrElse {
            return VisitResult.Error("Missing output writer", node.start(), node.end())
        }

        return when (val res = dispatcher.dispatch(node.expr, provider)) {
            is VisitResult.Success -> {
                out.write(res.value.toString() + "\n")
                VisitResult.Success(Unit)
            }
            is VisitResult.Error -> res
            else -> VisitResult.Error("Cannot evaluate print expression", node.start(), node.end())
        }
    }
}

class VarDeclVisitor : AstVisitor {
    override fun visit(node: AstNode, dispatcher: VisitorDispatcher, provider: DependencyProvider): VisitResult {
        if (node !is VarDeclNode) return VisitResult.NotHandled(node)

        val table = provider.get(SymbolTable::class.java).getOrElse {
            return VisitResult.Error("Missing symbol table", node.start(), node.end())
        }

        val value = node.value?.let { expr ->
            when (val res = dispatcher.dispatch(expr, provider)) {
                is VisitResult.Success -> res.value
                is VisitResult.Error -> return res
                else -> null
            }
        }

        val result = table.declare(node.name, value, node.constant)
        return result.fold(
            { VisitResult.Success(Unit) },
            { VisitResult.Error(it.message ?: "Declaration failed", node.start(), node.end()) },
        )
    }
}

class AssignmentVisitor : AstVisitor {
    override fun visit(node: AstNode, dispatcher: VisitorDispatcher, provider: DependencyProvider): VisitResult {
        if (node !is AssignmentNode) return VisitResult.NotHandled(node)

        val table = provider.get(SymbolTable::class.java).getOrElse {
            return VisitResult.Error("Missing symbol table", node.start(), node.end())
        }

        val value = when (val res = dispatcher.dispatch(node.value, provider)) {
            is VisitResult.Success -> res.value
            is VisitResult.Error -> return res
            else -> null
        }

        val result = table.assign(node.name, value)
        return result.fold(
            { VisitResult.Success(Unit) },
            { VisitResult.Error(it.message ?: "Assignment failed", node.start(), node.end()) },
        )
    }
}
