package ast

import value.Value

interface AstNode {
    fun evaluate(): Value
    fun children(): List<AstNode>
}
