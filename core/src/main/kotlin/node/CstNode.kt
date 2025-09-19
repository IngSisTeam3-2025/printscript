package node

import span.Span
import trivia.Trivia

sealed class CstNode : Node {
    data class Leaf(
        val value: Any,
        val leadingTrivia: List<Trivia> = emptyList(),
        val trailingTrivia: List<Trivia> = emptyList(),
        override val type: NodeType,
        override val span: Span,
    ) : CstNode()

    data class Composite(
        val children: List<CstNode>,
        override val type: NodeType,
        override val span: Span = TODO("not implemented"),
    ) : CstNode()
}
