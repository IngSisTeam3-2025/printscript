package formatter.internal.type

import model.doc.Doc
import model.node.Node
import model.node.NodeType
import model.span.Span
import model.trivia.NewlineTrivia
import model.trivia.SpaceTrivia
import model.trivia.Trivia

internal fun Node.Composite.updateChildAt(
    index: Int,
    transform: (Node) -> Node,
): Node.Composite {
    val updated = children.toList().mapIndexed { i, child ->
        when (i) {
            index -> transform(child)
            else -> child
        }
    }
    return copy(children = updated)
}

internal fun Node.Composite.findChildIndex(
    predicate: (Node) -> Boolean,
): Int {
    return children.toList().indexOfFirst(predicate)
}

internal fun Node.Composite.getChildAt(index: Int): Node {
    return children.toList()[index]
}

internal fun Node.Composite.findChildOfType(type: NodeType): Int {
    return findChildIndex { it.type == type }
}

internal fun Collection<Trivia>.hasSpace(): Boolean {
    return any { it.type == SpaceTrivia }
}

internal fun Collection<Trivia>.hasNewline(): Boolean {
    return any { it.type == NewlineTrivia }
}

internal fun Collection<Trivia>.countNewlines(): Int {
    return count { it.type == NewlineTrivia }
}

internal fun Collection<Trivia>.countSpaces(): Int {
    return count { it.type == SpaceTrivia }
}

internal fun Node.Leaf.withLeading(trivia: Collection<Trivia>): Node.Leaf {
    return copy(leading = trivia)
}

internal fun Node.Leaf.withTrailing(trivia: Collection<Trivia>): Node.Leaf {
    return copy(trailing = trivia)
}

internal fun Node.Leaf.addLeadingSpace(span: Span): Node.Leaf {
    return when {
        leading.hasSpace() -> this
        else -> withLeading(leading + Trivia(SpaceTrivia, " ", span))
    }
}

internal fun Node.Leaf.addTrailingSpace(span: Span): Node.Leaf {
    return when {
        trailing.hasSpace() -> this
        else -> withTrailing(listOf(Trivia(SpaceTrivia, " ", span)) + trailing)
    }
}

internal fun Node.Leaf.removeLeadingSpaces(): Node.Leaf {
    return withLeading(leading.filter { it.type != SpaceTrivia })
}

internal fun Node.Leaf.removeTrailingSpaces(): Node.Leaf {
    return withTrailing(trailing.filter { it.type != SpaceTrivia })
}

internal fun Node.Leaf.ensureLeadingSpace(span: Span): Node.Leaf {
    val withoutSpaces = removeLeadingSpaces()
    return withoutSpaces.addLeadingSpace(span)
}

internal fun Node.Leaf.ensureTrailingSpace(span: Span): Node.Leaf {
    val withoutSpaces = removeTrailingSpaces()
    return withoutSpaces.addTrailingSpace(span)
}

internal fun Node.Leaf.addLeadingNewline(span: Span, count: Int = 1): Node.Leaf {
    val newlines = (1..count).map { Trivia(NewlineTrivia, "\n", span) }
    return withLeading(leading + newlines)
}

internal fun Node.Leaf.addTrailingNewline(span: Span): Node.Leaf {
    return withTrailing(
        listOf(Trivia(NewlineTrivia, "\n", span)) + trailing,
    )
}

internal fun Node.Leaf.ensureTrailingNewline(span: Span): Node.Leaf {
    return when {
        trailing.hasNewline() -> this
        else -> addTrailingNewline(span)
    }
}

internal fun Node.Leaf.setLeadingNewlines(
    span: Span,
    count: Int,
): Node.Leaf {
    val withoutNewlines = withLeading(
        leading.filter { it.type != NewlineTrivia },
    )
    return when {
        count == 0 -> withoutNewlines
        else -> withoutNewlines.addLeadingNewline(span, count)
    }
}

internal fun Node.Leaf.addIndentation(span: Span, spaces: Int): Node.Leaf {
    val indentation = (1..spaces).map { Trivia(SpaceTrivia, " ", span) }
    return withLeading(leading + indentation)
}

internal fun Node.Composite.hasSpaceBefore(index: Int): Boolean {
    val current = getChildAt(index)
    if (current !is Node.Leaf) return false

    if (current.leading.hasSpace()) return true

    return when {
        index > 0 -> when (val prev = getChildAt(index - 1)) {
            is Node.Leaf -> prev.trailing.hasSpace()
            else -> false
        }
        else -> false
    }
}

internal fun Node.Composite.hasSpaceAfter(index: Int): Boolean {
    val current = getChildAt(index)
    if (current !is Node.Leaf) return false

    if (current.trailing.hasSpace()) return true

    return when {
        index < children.size - 1 -> when (val next = getChildAt(index + 1)) {
            is Node.Leaf -> next.leading.hasSpace()
            else -> false
        }
        else -> false
    }
}

internal fun Node.Composite.hasNewlineBefore(index: Int): Boolean {
    val current = getChildAt(index)
    if (current !is Node.Leaf) return false

    if (current.leading.hasNewline()) return true

    return when {
        index > 0 -> when (val prev = getChildAt(index - 1)) {
            is Node.Leaf -> prev.trailing.hasNewline()
            else -> false
        }
        else -> false
    }
}

internal fun Node.Composite.toDoc(): Doc {
    return Doc(
        text = format(),
        span = span,
        leading = emptyList(),
        trailing = emptyList(),
    )
}

internal fun Node.Leaf.toDoc(): Doc {
    return Doc(
        text = format(),
        span = span,
        leading = leading,
        trailing = trailing,
    )
}

internal fun Node.toDoc(): Doc = when (this) {
    is Node.Leaf -> toDoc()
    is Node.Composite -> toDoc()
}

internal fun Node.Leaf.setTrailingNewlines(
    span: Span,
    count: Int,
): Node.Leaf {
    val withoutNewlines = removeTrailingNewlines()
    return when (count) {
        0 -> withoutNewlines
        else -> withoutNewlines.addTrailingNewline(span, count)
    }
}

internal fun Node.Leaf.removeTrailingNewlines(): Node.Leaf {
    return withTrailing(trailing.filter { it.type != NewlineTrivia })
}

internal fun Node.Leaf.addTrailingNewline(
    span: Span,
    count: Int = 1,
): Node.Leaf {
    val newlines = (1..count).map { Trivia(NewlineTrivia, "\n", span) }
    return withTrailing(trailing + newlines)
}
