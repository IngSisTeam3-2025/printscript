package io.reader.input

class MockInputReader(private val inputs: List<String>) : InputReader {
    private var index = 0

    override fun read(): Sequence<Char> {
        if (index >= inputs.size) return emptySequence()
        val next = inputs[index++]
        return next.asSequence()
    }
}
