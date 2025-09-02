package source

interface SourceReader {

    fun peek(offset: Int = 1): SourceReadResult
    fun advance(offset: Int = 1): SourceReadResult
    fun position(): SourcePosition
    fun isEOF(): Boolean
    fun close()
}
