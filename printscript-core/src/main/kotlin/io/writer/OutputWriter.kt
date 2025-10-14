package io.writer

interface OutputWriter {
    fun write(lines: Sequence<String>)
}
