package node

import span.Span

sealed class AstNode : Node {
    data class Leaf(
        val value: Any,
        override val type: NodeType,
        override val span: Span,
    ) : AstNode()

    data class Composite(
        val children: List<AstNode>,
        override val type: NodeType,
        override val span: Span = TODO("not implemented"),
    ) : AstNode()
}
