package io.reader.input

class ConsoleInputReader : InputReader {
    override fun read(): Sequence<Char> {
        val input = readlnOrNull() ?: ""
        return input.asSequence()
    }
}
