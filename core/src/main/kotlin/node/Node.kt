package node

import span.Span

interface Node {
    val type: NodeType
    val span: Span
}
