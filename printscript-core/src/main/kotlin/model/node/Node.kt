package model.node

import model.span.Span
import model.trivia.Trivia

sealed class Node {
    abstract val type: NodeType
    abstract val span: Span
    abstract fun <R> accept(visitor: Visitor<R>): R
    abstract fun toSourceString(): String

    data class Leaf(
        override val type: NodeType,
        val value: Any,
        override val span: Span,
        val leading: Collection<Trivia> = emptyList(),
        val trailing: Collection<Trivia> = emptyList()
    ) : Node() {
        override fun <R> accept(visitor: Visitor<R>) = visitor.visit(this)
        override fun toSourceString() = leading.joinToString("") { it.lexeme } + value.toString() + trailing.joinToString("") { it.lexeme }
    }

    data class Composite(
        override val type: NodeType,
        val children: Collection<Node>,
        override val span: Span
    ) : Node() {
        override fun <R> accept(visitor: Visitor<R>) = visitor.visit(this)
        override fun toSourceString() = children.joinToString("") { it.toSourceString() }
    }
}
