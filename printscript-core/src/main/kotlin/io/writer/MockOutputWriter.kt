package io.writer

class MockOutputWriter : OutputWriter {
    private val buffer = mutableListOf<String>()

    override fun write(input: Sequence<String>) {
        buffer.addAll(input.toList())
    }

    fun get(): String = buffer.joinToString("")
}
