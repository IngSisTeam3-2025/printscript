package ast

import token.TokenTemp

interface AbstractSyntaxTree

data class Num(val token: TokenTemp, val value: Int) : AbstractSyntaxTree
data class BinOp(val left: AbstractSyntaxTree, val op: TokenTemp, val right: AbstractSyntaxTree) : AbstractSyntaxTree
data class UnaryOp(val op: TokenTemp, val expr: AbstractSyntaxTree) : AbstractSyntaxTree
data class Str(val token: TokenTemp, val value: String) : AbstractSyntaxTree

// Identificador usado en expresiones (x, y, etc.)
data class Var(val name: TokenTemp) : AbstractSyntaxTree

// Asignación como expresión: x = 1 + 2
data class Assign(val name: TokenTemp, val value: AbstractSyntaxTree) : AbstractSyntaxTree

// Sentencias
sealed interface Stmt : AbstractSyntaxTree

// let x: number = 1 + 2;
data class VarDecl(
    val letTok: TokenTemp,
    val name: TokenTemp,
    val annotatedType: TokenTemp,
    val init: AbstractSyntaxTree?,
) : Stmt

// println(expr);
data class PrintlnStmt(
    val kw: TokenTemp,
    val lparen: TokenTemp,
    val arg: AbstractSyntaxTree?,
    val rparen: TokenTemp,
    val semi: TokenTemp,
) : Stmt

// stmt que es solo una expresión seguida de ';'
data class ExprStmt(
    val expr: AbstractSyntaxTree,
    val semi: TokenTemp,
) : Stmt

// Programa = lista de sentencias
data class Program(val statements: List<Stmt>) : AbstractSyntaxTree
