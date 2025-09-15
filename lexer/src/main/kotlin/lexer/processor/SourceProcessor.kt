package lexer.processor

class SourceProcessor(private val iter: Iterator<Char>) {
    private val lookahead = ArrayDeque<Char>()
    var position = 0
        private set

    fun eof(): Boolean = !iter.hasNext() && lookahead.isEmpty()

    fun peek(n: Int = 1): String {
        while (lookahead.size < n && iter.hasNext()) {
            lookahead.addLast(iter.next())
        }
        return lookahead.take(n).joinToString("")
    }

    fun advance(n: Int) {
        repeat(n) {
            if (lookahead.isNotEmpty()) {
                lookahead.removeFirst()
            } else if (iter.hasNext()) {
                iter.next()
            }
            position++
        }
    }

    fun remainingString(limit: Int = 64): String {
        peek(limit)
        return lookahead.take(limit).joinToString("")
    }
}
