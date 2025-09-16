package location

data class SourceLocation(
    val offset: Int = 0,
    val line: Int = 1,
    val column: Int = 1,
) {

    fun advance(c: Char): SourceLocation = if (c == '\n') {
        copy(offset = offset + 1, line = line + 1, column = 1)
    } else {
        copy(offset = offset + 1, line = line, column = column + 1)
    }
}
