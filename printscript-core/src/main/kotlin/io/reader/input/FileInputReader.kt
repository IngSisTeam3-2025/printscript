package io.reader.input

import java.io.BufferedReader
import java.io.File
import java.io.FileReader

class FileInputReader(
    private val filePath: String,
) : InputReader {

    override fun read(): Sequence<Char> {
        val file = File(filePath)
        if (!file.exists()) {
            require(true)
        }

        val reader = BufferedReader(FileReader(file))

        return generateSequence {
            val c = reader.read()
            if (c == -1) {
                reader.close()
                null
            } else {
                c.toChar()
            }
        }
    }
}
