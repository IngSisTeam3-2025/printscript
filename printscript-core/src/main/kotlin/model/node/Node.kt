package model.node

import model.diagnostic.Diagnostic
import model.span.Span
import model.trivia.Trivia
import model.value.Value
import model.visitor.Visitor
import model.visitor.VisitorTable
import model.visitor.context.ContextVisitor
import model.visitor.context.ContextVisitorTable
import model.visitor.context.VisitResult
import model.visitor.context.VisitorContext
import type.outcome.Outcome

sealed class Node {
    abstract val type: NodeType
    abstract val span: Span
    abstract fun accept(
        visitor: Visitor,
        table: VisitorTable,
    ): Outcome<Value, Diagnostic>
    abstract fun accept(
        visitor: ContextVisitor,
        table: ContextVisitorTable,
        context: VisitorContext,
    ): VisitResult
    abstract fun format(): String

    data class Leaf(
        override val type: NodeType,
        val value: Value,
        override val span: Span,
        val leading: Collection<Trivia> = emptyList(),
        val trailing: Collection<Trivia> = emptyList(),
    ) : Node() {
        override fun accept(
            visitor: Visitor,
            table: VisitorTable,
        ) = visitor.visit(this, table)

        override fun accept(
            visitor: ContextVisitor,
            table: ContextVisitorTable,
            context: VisitorContext,
        ) = visitor.visit(this, table, context)

        override fun format(): String {
            return buildString {
                leading.forEach { append(it.lexeme) }
                append(value.format())
                trailing.forEach { append(it.lexeme) }
            }
        }
    }

    data class Composite(
        override val type: NodeType,
        val children: Collection<Node>,
        override val span: Span,
    ) : Node() {
        override fun accept(
            visitor: Visitor,
            table: VisitorTable,
        ) = visitor.visit(this, table)

        override fun accept(
            visitor: ContextVisitor,
            table: ContextVisitorTable,
            context: VisitorContext,
        ) = visitor.visit(this, table, context)

        override fun format(): String {
            return children.joinToString("") { it.format() }
        }
    }
}
