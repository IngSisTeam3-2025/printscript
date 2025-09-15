package lexer.processor

class TriviaProcessor(private val source: SourceProcessor) {

    fun capturePrefix(): String {
        val sb = StringBuilder()
        while (!source.eof() && source.peek().first().isWhitespace()) {
            sb.append(source.peek())
            source.advance(1)
        }
        return sb.toString()
    }

    fun captureSuffix(): String {
        val sb = StringBuilder()
        while (!source.eof() && source.peek().first().isWhitespace()) {
            sb.append(source.peek())
            source.advance(1)
        }
        return sb.toString()
    }
}
