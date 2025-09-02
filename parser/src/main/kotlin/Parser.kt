import ast.AbstractSyntaxTree
import ast.Assign
import ast.BinOp
import ast.ExprStmt
import ast.Num
import ast.PrintlnStmt
import ast.Program
import ast.Stmt
import ast.Str
import ast.UnaryOp
import ast.Var
import ast.VarDecl
import source.TokenSource
import token.TokenTemp
import token.TokenTypeTemp

class Parser(private val ts: TokenSource) {
    private var current: TokenTemp = ts.peek()

    private fun error(msg: String = "Parsing error"): Nothing = throw Error(msg)
    private fun advance() {
        current = ts.next()
    }
    private fun check(t: TokenTypeTemp) = current.type == t
    private fun expect(t: TokenTypeTemp) {
        if (current.type != t) error("Expected $t, got ${current.type}")
        advance()
    }

    fun parseProgram(): Program {
        advance()
        val stmts = mutableListOf<Stmt>()
        while (!check(TokenTypeTemp.EOF)) {
            stmts += parseStatement()
        }
        return Program(stmts)
    }

    private fun parseStatement(): Stmt = when {
        check(TokenTypeTemp.LET) -> parseVarDecl()
        check(TokenTypeTemp.PRINTLN) -> parsePrintln()
        else -> parseExprStmt()
    }

    private fun parseVarDecl(): Stmt {
        val letTok = current
        expect(TokenTypeTemp.LET)
        val nameTok = current
        expect(TokenTypeTemp.ID)

        if (!check(TokenTypeTemp.COLON)) {
            error("Variable '${nameTok.lexeme}' must have an explicit type")
        }
        expect(TokenTypeTemp.COLON)

        val typeTok: TokenTemp
        when (current.type) {
            TokenTypeTemp.INT, TokenTypeTemp.STRING -> {
                typeTok = current
                advance()
            }
            TokenTypeTemp.ID -> {
                if (current.lexeme == "number") {
                    typeTok = TokenTemp(TokenTypeTemp.INT, "number")
                    advance()
                } else if (current.lexeme == "string") {
                    typeTok = TokenTemp(TokenTypeTemp.STRING, "string")
                    advance()
                } else {
                    error("Unknown type '${current.lexeme}'")
                }
            }
            else -> error("Expected type after ':'")
        }

        var init: AbstractSyntaxTree? = null
        if (check(TokenTypeTemp.ASSIGN)) {
            advance()
            init = parseExpr()
        }

        expect(TokenTypeTemp.SEMI)

        return VarDecl(letTok, nameTok, typeTok, init)
    }

    private fun parsePrintln(): Stmt {
        val kw = current
        expect(TokenTypeTemp.PRINTLN)
        val lp = current
        expect(TokenTypeTemp.LPAREN)
        val arg = if (!check(TokenTypeTemp.RPAREN)) parseExpr() else null
        val rp = current
        expect(TokenTypeTemp.RPAREN)
        val semi = current
        expect(TokenTypeTemp.SEMI)
        return PrintlnStmt(kw, lp, arg, rp, semi)
    }

    private fun parseExprStmt(): Stmt {
        val e = parseExpr()
        val semi = current
        expect(TokenTypeTemp.SEMI)
        return ExprStmt(e, semi)
    }

    private fun parseExpr(): AbstractSyntaxTree = parseAssignment()

    private fun parseAssignment(): AbstractSyntaxTree {
        val left = parseAdditive()
        return if (check(TokenTypeTemp.ASSIGN)) {
            if (left is Var) {
                expect(TokenTypeTemp.ASSIGN)
                val value = parseAssignment()
                Assign(left.name, value)
            } else {
                error("Left side of '=' must be an identifier")
            }
        } else {
            left
        }
    }

    private fun parseAdditive(): AbstractSyntaxTree {
        var node = parseMultiplicative()
        while (check(TokenTypeTemp.ADD) || check(TokenTypeTemp.SUB)) {
            val op = current
            advance()
            node = BinOp(node, op, parseMultiplicative())
        }
        return node
    }

    private fun parseUnary(): AbstractSyntaxTree = when (current.type) {
        TokenTypeTemp.ADD -> {
            val op = current
            expect(TokenTypeTemp.ADD)
            UnaryOp(op, parseUnary())
        }
        TokenTypeTemp.SUB -> {
            val op = current
            expect(TokenTypeTemp.SUB)
            UnaryOp(op, parseUnary())
        }
        else -> parsePrimary()
    }

    private fun parseMultiplicative(): AbstractSyntaxTree {
        var node = parseUnary()
        while (check(TokenTypeTemp.MUL) || check(TokenTypeTemp.DIV)) {
            val op = current
            advance()
            node = BinOp(node, op, parseUnary())
        }
        return node
    }

    private fun parsePrimary(): AbstractSyntaxTree = when (current.type) {
        TokenTypeTemp.INT -> {
            val t = current
            advance()
            val value = if (t.lexeme.contains('.')) {
                t.lexeme.toDouble().toInt()
            } else {
                t.lexeme.toInt()
            }
            Num(t, value)
        }
        TokenTypeTemp.STRING -> {
            val t = current
            advance()
            Str(t, t.lexeme)
        }
        TokenTypeTemp.ID -> {
            val id = current
            advance()
            Var(id)
        }
        TokenTypeTemp.LPAREN -> {
            expect(TokenTypeTemp.LPAREN)
            val e = parseExpr()
            expect(TokenTypeTemp.RPAREN)
            e
        }
        else -> error("Unexpected token in primary: ${current.type}")
    }
}
