package io.writer

class ConsoleOutputWriter : OutputWriter {
    override fun write(input: Sequence<String>) {
        input.forEach { print(it) }
    }
}
