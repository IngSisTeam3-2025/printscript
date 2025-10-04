package internal.buffer

internal class LexemeBuffer(chars: Sequence<Char>) {
    private val iter = chars.iterator()
    private val inner = StringBuilder()

    init {
        fill()
    }

    private fun fill() {
        while (inner.length < LOOKAHEAD && iter.hasNext()) inner.append(iter.next())
    }

    fun peek(n: Int): String {
        while (inner.length < n && iter.hasNext()) {
            inner.append(iter.next())
        }
        return inner.substring(0, minOf(n, inner.length))
    }

    fun hasNext(): Boolean = inner.isNotEmpty() || iter.hasNext()

    fun isEmpty(): Boolean = inner.isEmpty() && !iter.hasNext()

    fun consume(n: Int) {
        inner.delete(0, n.coerceAtMost(inner.length))
        fill()
    }

    companion object { private const val LOOKAHEAD = 64 }
}