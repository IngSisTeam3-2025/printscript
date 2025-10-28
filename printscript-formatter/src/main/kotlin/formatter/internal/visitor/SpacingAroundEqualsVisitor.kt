package formatter.internal.visitor

import formatter.internal.model.value.DocValue
import model.diagnostic.Diagnostic
import model.doc.Doc
import model.node.AssignNode
import model.node.Node
import model.span.Span
import model.trivia.SpaceTrivia
import model.trivia.Trivia
import model.value.NoneValue
import model.value.Value
import model.visitor.Visitor
import model.visitor.VisitorTable
import type.outcome.Outcome

internal class SpacingAroundEqualsVisitor(
    private val enforce: Boolean,
) : Visitor {

    override fun visit(node: Node.Leaf, table: VisitorTable): Outcome<Value, Diagnostic> {
        return Outcome.Ok(NoneValue)
    }

    override fun visit(node: Node.Composite, table: VisitorTable): Outcome<Value, Diagnostic> {
        if (!enforce) {
            return Outcome.Ok(DocValue(childToDoc(node)))
        }

        val assignIndex = node.children.indexOfFirst { child ->
            child is Node.Leaf && child.type == AssignNode
        }

        if (assignIndex == -1) {
            return Outcome.Ok(NoneValue)
        }

        val assignNode = node.children.toList()[assignIndex] as Node.Leaf

        val formattedChildren = node.children.mapIndexed { index, child ->
            when {
                child is Node.Leaf && child.type == AssignNode -> {
                    Doc(
                        text = child.value.format(),
                        span = child.span,
                        leading = emptyList(),
                        trailing = emptyList(),
                    )
                }

                index == assignIndex - 1 && child is Node.Leaf -> {
                    Doc(
                        text = child.value.format(),
                        span = child.span,
                        leading = child.leading,
                        trailing = ensureSingleSpace(child.trailing, child.span, assignNode.span),
                    )
                }

                index == assignIndex + 1 -> {
                    when (child) {
                        is Node.Leaf -> Doc(
                            text = child.value.format(),
                            span = child.span,
                            leading = ensureSingleSpace(child.leading, assignNode.span, child.span),
                            trailing = child.trailing,
                        )
                        is Node.Composite -> Doc(
                            text = child.format(),
                            span = child.span,
                            leading = emptyList(),
                            trailing = emptyList(),
                        )
                    }
                }
                else -> childToDoc(child)
            }
        }

        return Outcome.Ok(
            DocValue(
                Doc(
                    text = formattedChildren.joinToString("") { it.format() },
                    span = node.span,
                    leading = emptyList(),
                    trailing = emptyList(),
                ),
            ),
        )
    }

    private fun childToDoc(child: Node): Doc {
        return when (child) {
            is Node.Leaf -> Doc(
                text = child.value.format(),
                span = child.span,
                leading = child.leading,
                trailing = child.trailing,
            )
            is Node.Composite -> Doc(
                text = child.format(),
                span = child.span,
                leading = emptyList(),
                trailing = emptyList(),
            )
        }
    }

    private fun ensureSingleSpace(
        trivia: Collection<Trivia>,
        fromSpan: Span,
        toSpan: Span,
    ): Collection<Trivia> {
        val hasSpace = trivia.any { it.type == SpaceTrivia }

        if (hasSpace) {
            return trivia
        }

        val spaceSpan = Span(
            start = fromSpan.end,
            end = toSpan.start,
        )

        return trivia + Trivia(
            type = SpaceTrivia,
            lexeme = " ",
            span = spaceSpan,
        )
    }
}
