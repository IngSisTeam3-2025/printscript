package formatter.internal.traversal

import model.node.Node
import model.node.NodeType

internal object NodeTraversal {

    fun endsWith(node: Node, nodeType: NodeType): Boolean {
        return findLastLeaf(node)?.type == nodeType
    }

    private fun findLastLeaf(node: Node): Node.Leaf? {
        return when (node) {
            is Node.Leaf -> node
            is Node.Composite -> {
                node.children.toList().reversed().firstNotNullOfOrNull { findLastLeaf(it) }
            }
        }
    }
}
