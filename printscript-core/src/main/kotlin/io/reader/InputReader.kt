package io.reader

interface InputReader {
    fun read(): Sequence<Char>
}
