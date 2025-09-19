package visitor

import node.AstNode

interface AstVisitor<R> {
    fun visit(node: AstNode.Leaf): R
    fun visit(node: AstNode.Composite): R
}
