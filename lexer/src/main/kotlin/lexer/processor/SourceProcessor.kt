package lexer.processor

import location.SourceLocation

class SourceProcessor(private val iter: Iterator<Char>) {
    private val buffer = ArrayDeque<Char>()
    private var location: SourceLocation = SourceLocation()
        private set

    fun eof(): Boolean = buffer.isEmpty() && !iter.hasNext()

    fun peekSlice(n: Int): CharSequence {
        while (buffer.size < n && iter.hasNext()) buffer.addLast(iter.next())
        return buffer.joinToString("").take(n)
    }

    /** Advance n characters, updating location */
    fun advance(n: Int) {
        repeat(n) {
            val c = if (buffer.isNotEmpty()) buffer.removeFirst() else iter.next()
            location = location.advance(c)
        }
    }

    fun location(): SourceLocation = location
}
