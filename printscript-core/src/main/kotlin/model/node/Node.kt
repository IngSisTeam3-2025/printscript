package model.node

import model.span.Span
import model.trivia.Trivia
import visitor.Visitor

sealed class Node {
    abstract val type: model.node.NodeType
    abstract val span: Span

    abstract fun toSourceString(): String
    abstract fun <R> accept(visitor: Visitor<R>): R

    data class Leaf(
        val value: Any,
        val leading: Collection<Trivia> = emptyList(),
        val trailing: Collection<Trivia> = emptyList(),
        override val type: model.node.NodeType,
        override val span: Span
    ) : model.node.Node() {
        override fun toSourceString() =
            leading.joinToString("") { it.lexeme } +
                    value.toString() +
                    trailing.joinToString("") { it.lexeme }

        override fun <R> accept(visitor: Visitor<R>) = visitor.visit(this)
    }

    data class Composite(
        val children: List<model.node.Node>,
        override val type: model.node.NodeType,
        override val span: Span
    ) : model.node.Node() {
        override fun toSourceString() = children.joinToString("") { it.toSourceString() }

        override fun <R> accept(visitor: Visitor<R>) = visitor.visit(this)
    }
}


