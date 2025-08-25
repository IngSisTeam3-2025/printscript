import token.TokenType
import ast.*
import lexer.TokenSource

class Parser(private val ts: TokenSource) {
    private var current: Token = ts.peek()

    private fun error(msg: String = "Parsing error"): Nothing = throw Error(msg)
    private fun advance() { current = ts.next() }
    private fun check(t: TokenType) = current.type == t
    private fun expect(t: TokenType) { if (!check(t)) error("Expected $t, got ${current.type}"); advance() }

    fun parseProgram(): Program {
        advance()
        val stmts = mutableListOf<Stmt>()
        while (!check(TokenType.EOF)) {
            stmts += parseStatement()
        }
        return Program(stmts)
    }

    private fun parseStatement(): Stmt = when {
        check(TokenType.LET)     -> parseVarDecl()
        check(TokenType.PRINTLN) -> parsePrintln()
        else                     -> parseExprStmt()
    }

    private fun parseVarDecl(): Stmt {
        val letTok = current; expect(TokenType.LET)
        val nameTok = current; expect(TokenType.ID)

        if (!check(TokenType.COLON)) {
            error("Variable '${nameTok.lexeme}' must have an explicit type")
        }
        expect(TokenType.COLON)

        val typeTok: Token
        when (current.type) {
            TokenType.INT, TokenType.STRING -> {
                typeTok = current
                advance()
            }
            TokenType.ID -> {
                if (current.lexeme != "number" && current.lexeme != "string")
                    error("Unknown type '${current.lexeme}'")
                typeTok = current
                advance()
            }
            else -> error("Expected type after ':'")
        }

        var init: AbstractSyntaxTree? = null
        if (check(TokenType.ASSIGN)) { advance(); init = parseExpr() }

        expect(TokenType.SEMI)

        return VarDecl(letTok, nameTok, typeTok, init)
    }

    private fun parsePrintln(): Stmt {
        val kw = current; expect(TokenType.PRINTLN)
        val lp = current; expect(TokenType.LPAREN)
        val arg = if (!check(TokenType.RPAREN)) parseExpr() else null
        val rp = current; expect(TokenType.RPAREN)
        val semi = current; expect(TokenType.SEMI)
        return PrintlnStmt(kw, lp, arg, rp, semi)
    }

    private fun parseExprStmt(): Stmt {
        val e = parseExpr()
        val semi = current; expect(TokenType.SEMI)
        return ExprStmt(e, semi)
    }

    private fun parseExpr(): AbstractSyntaxTree = parseAssignment()

    private fun parseAssignment(): AbstractSyntaxTree {
        val left = parseAdditive()
        return if (check(TokenType.ASSIGN)) {
            if (left is Var) {
                expect(TokenType.ASSIGN)
                val value = parseAssignment()
                Assign(left.name, value)
            } else {
                error("Left side of '=' must be an identifier")
            }
        } else left
    }

    private fun parseAdditive(): AbstractSyntaxTree {
        var node = parseMultiplicative()
        while (check(TokenType.ADD) || check(TokenType.SUB)) {
            val op = current; advance()
            node = BinOp(node, op, parseMultiplicative())
        }
        return node
    }

    private fun parseUnary(): AbstractSyntaxTree = when (current.type) {
        TokenType.ADD -> { val op = current; expect(TokenType.ADD); UnaryOp(op, parseUnary()) }
        TokenType.SUB -> { val op = current; expect(TokenType.SUB); UnaryOp(op, parseUnary()) }
        else -> parsePrimary()
    }

    private fun parseMultiplicative(): AbstractSyntaxTree {
        var node = parseUnary()
        while (check(TokenType.MUL) || check(TokenType.DIV)) {
            val op = current; advance()
            node = BinOp(node, op, parseUnary())
        }
        return node
    }

    private fun parsePrimary(): AbstractSyntaxTree = when (current.type) {
        TokenType.INT -> {
            val t = current; advance(); Num(t, t.lexeme.toInt())
        }
        TokenType.STRING -> {
            val t = current; advance(); Str(t, t.lexeme)
        }
        TokenType.ID -> {
            val id = current; advance(); Var(id)
        }
        TokenType.LPAREN -> {
            expect(TokenType.LPAREN)
            val e = parseExpr()
            expect(TokenType.RPAREN)
            e
        }
        else -> error("Unexpected token in primary: ${current.type}")
    }
}
