import source.SourcePosition
import source.SourceReadResult
import source.ISourceReader

class StringSourceReader(private val content: String) : ISourceReader {
    private var index = 0
    private var line = 1
    private var column = 1

    override fun peek(offset: Int): SourceReadResult {
        val targetIndex = index + offset
        return if (targetIndex < 0 || targetIndex >= content.length) {
            SourceReadResult.EOF
        } else {
            val char = content[targetIndex]
            SourceReadResult.Success(char, SourcePosition(line, column + offset))
        }
    }

    override fun advance(offset: Int): SourceReadResult {
        var lastChar: Char? = null
        repeat(offset) {
            if (index >= content.length) return SourceReadResult.EOF
            val char = content[index++]
            if (char == '\n') {
                line++
                column = 1
            } else {
                column++
            }
            lastChar = char
        }

        return if (lastChar != null) {
            SourceReadResult.Success(lastChar!!, position())
        } else {
            SourceReadResult.EOF
        }
    }

    override fun position(): SourcePosition = SourcePosition(line, column)

    override fun isEOF(): Boolean = index >= content.length

    override fun close() {
        return
    }
}
