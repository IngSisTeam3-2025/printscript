package it

interface PeekableIterator<T> : Iterator<T> {
    fun peek(): PeekResult<T>
}
