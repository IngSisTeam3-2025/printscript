package visitor

import node.CstNode

interface CstVisitor<R> {
    fun visit(node: CstNode.Leaf): R
    fun visit(node: CstNode.Composite): R
}
