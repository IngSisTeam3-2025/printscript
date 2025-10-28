package formatter.internal.visitor

import formatter.internal.model.value.DocValue
import model.diagnostic.Diagnostic
import model.doc.Doc
import model.node.AssignNode
import model.node.Node
import model.trivia.SpaceTrivia
import model.trivia.TabTrivia
import model.trivia.Trivia
import model.value.NoneValue
import model.value.Value
import model.visitor.Visitor
import model.visitor.VisitorTable
import type.outcome.Outcome

internal class NoSpacingAroundEqualsVisitor(
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

        val formattedChildren = node.children.mapIndexed { index, child ->
            when {
                child is Node.Leaf && child.type == AssignNode -> {
                    Doc(
                        text = child.value.format(),
                        span = child.span,
                        leading = removeSpacing(child.leading),
                        trailing = removeSpacing(child.trailing),
                    )
                }

                index == assignIndex + 1 -> {
                    when (child) {
                        is Node.Leaf -> Doc(
                            text = child.value.format(),
                            span = child.span,
                            leading = removeSpacing(child.leading),
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

                index == assignIndex - 1 && child is Node.Leaf -> {
                    Doc(
                        text = child.value.format(),
                        span = child.span,
                        leading = child.leading,
                        trailing = removeSpacing(child.trailing),
                    )
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

    private fun removeSpacing(trivia: Collection<Trivia>): Collection<Trivia> {
        return trivia.filter {
            it.type != SpaceTrivia && it.type != TabTrivia
        }
    }
}
