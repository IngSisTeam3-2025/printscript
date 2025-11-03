package parser.internal.model.scan

import model.diagnostic.category.Category
import model.node.Node

internal sealed interface NodeScan {
    data object Empty : NodeScan
    data class Error(
        val message: String,
        val category: Category,
        val consumed: Int,
    ) : NodeScan
    data class Ok(
        val node: Node,
        val consumed: Int,
    ) : NodeScan
}
