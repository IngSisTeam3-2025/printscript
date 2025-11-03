package io.reader.input

interface InputReader {
    fun read(): Sequence<Char>
}
