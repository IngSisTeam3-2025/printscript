package internal.buffer

import model.token.Token

internal class TokenBuffer(tokens: Sequence<Token>) {
    private val iter = tokens.iterator()
    private val inner = mutableListOf<Token>()

    init { fill(64) }

    private fun fill(n: Int = 64) {
        while (inner.size < n && iter.hasNext()) inner.add(iter.next())
    }

    fun peek(): List<Token> = inner.toList()
    fun hasNext() = inner.isNotEmpty() || iter.hasNext()
    fun advance(n: Int) {
        repeat(n.coerceAtMost(inner.size)) { inner.removeAt(0) }
        fill(64)
    }
}

