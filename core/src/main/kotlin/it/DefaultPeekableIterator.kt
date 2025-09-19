package it

class DefaultPeekableIterator<T>(private val inner: Iterator<T>) : PeekableIterator<T> {
    private var peeked: T? = null
    private var hasPeeked = false

    override fun hasNext(): Boolean = hasPeeked || inner.hasNext()

    override fun next(): T = when {
        hasPeeked -> {
            hasPeeked = false
            peeked!!
        }
        else -> inner.next()
    }

    override fun peek(): PeekResult<T> = when {
        hasPeeked -> PeekResult.Value(peeked!!)
        inner.hasNext() -> {
            peeked = inner.next()
            hasPeeked = true
            PeekResult.Value(peeked!!)
        }
        else -> PeekResult.Empty
    }
}
