import source.SourcePosition
import source.SourceReadResult
import source.ISourceReader
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException

class FileSourceReader(file: File) : ISourceReader {
    private val reader = BufferedReader(FileReader(file))
    private val buffer = ArrayList<Char>()
    private var line = 1
    private var column = 1
    private var eof = false

    override fun peek(offset: Int): SourceReadResult {
        try {
            while (buffer.size < offset && !eof) {
                val c = reader.read()
                if (c == -1) {
                    eof = true
                    break
                }
                buffer.add(c.toChar())
            }
            return if (offset <= buffer.size) {
                SourceReadResult.Success(buffer[offset - 1], SourcePosition(line, column + offset - 1))
            } else {
                SourceReadResult.EOF
            }
        } catch (e: IOException) {
            return SourceReadResult.Failure(e.message ?: "Unknown IO error")
        }
    }

    override fun advance(offset: Int): SourceReadResult {
        var last: Char? = null
        repeat(offset) {
            val c = if (buffer.isNotEmpty()) {
                buffer.removeAt(0)
            } else {
                reader.read().takeIf { it != -1 }?.toChar() ?: return SourceReadResult.EOF
            }

            if (c == '\n') {
                line++
                column = 1
            } else {
                column++
            }
            last = c
        }
        return last?.let { SourceReadResult.Success(it, position()) } ?: SourceReadResult.EOF
    }

    override fun position(): SourcePosition = SourcePosition(line, column)
    override fun isEOF(): Boolean = eof && buffer.isEmpty()
    override fun close() = reader.close()
}
