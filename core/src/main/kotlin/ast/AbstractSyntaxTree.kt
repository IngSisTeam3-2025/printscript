package ast

import Token

interface AbstractSyntaxTree

data class Num(val token: Token, val value: Int) : AbstractSyntaxTree
data class BinOp(val left: AbstractSyntaxTree, val op: Token, val right: AbstractSyntaxTree) : AbstractSyntaxTree
data class UnaryOp(val op: Token, val expr: AbstractSyntaxTree) : AbstractSyntaxTree
data class Str(val token: Token, val value: String) : AbstractSyntaxTree

// Identificador usado en expresiones (x, y, etc.)
data class Var(val name: Token) : AbstractSyntaxTree

// Asignación como expresión: x = 1 + 2
data class Assign(val name: Token, val value: AbstractSyntaxTree) : AbstractSyntaxTree

// Sentencias
sealed interface Stmt : AbstractSyntaxTree

// let x: number = 1 + 2;
data class VarDecl(
    val letTok: Token,
    val name: Token,
    val annotatedType: Token,
    val init: AbstractSyntaxTree?,
) : Stmt

// println(expr);
data class PrintlnStmt(
    val kw: Token,
    val lparen: Token,
    val arg: AbstractSyntaxTree?,
    val rparen: Token,
    val semi: Token,
) : Stmt

// stmt que es solo una expresión seguida de ';'
data class ExprStmt(
    val expr: AbstractSyntaxTree,
    val semi: Token,
) : Stmt

// Programa = lista de sentencias
data class Program(val statements: List<Stmt>) : AbstractSyntaxTree
