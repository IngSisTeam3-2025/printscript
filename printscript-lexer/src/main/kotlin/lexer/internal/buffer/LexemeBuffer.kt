package lexer.internal.buffer

internal class LexemeBuffer(chars: Sequence<Char>) {
    private val iterator = chars.iterator()
    private val inner = StringBuilder()

    init {
        fill()
    }

    private fun fill() {
        while (inner.length < LOOKAHEAD && iterator.hasNext()) inner.append(iterator.next())
    }

    fun peek(n: Int = 1): String {
        while (inner.length < n && iterator.hasNext()) {
            inner.append(iterator.next())
        }
        return inner.substring(0, minOf(n, inner.length))
    }

    fun hasNext(): Boolean = inner.isNotEmpty() || iterator.hasNext()

    fun isEmpty(): Boolean = inner.isEmpty() && !iterator.hasNext()

    fun consume(n: Int) {
        inner.delete(0, n.coerceAtMost(inner.length))
        fill()
    }

    companion object {
        private const val LOOKAHEAD = 64
    }
}
