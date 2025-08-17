package ast

import Token

interface AbstractSyntaxTree

data class Num(val token: Token, val value: Int) : AbstractSyntaxTree
data class BinOp(val left: AbstractSyntaxTree, val op: Token, val right: AbstractSyntaxTree) : AbstractSyntaxTree
data class UnaryOp(val op: Token, val expr: AbstractSyntaxTree) : AbstractSyntaxTree
