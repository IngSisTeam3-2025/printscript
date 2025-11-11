package io.writer

import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

class FileOutputWriter(
    private val filePath: String,
) : OutputWriter {

    override fun write(input: Sequence<String>) {
        val file = File(filePath)

        val parentDir = file.parentFile
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs()
        }

        if (!file.exists()) {
            file.createNewFile()
        }

        BufferedWriter(FileWriter(file)).use { writer ->
            for (line in input) {
                writer.write(line)
                writer.newLine()
            }
        }
    }
}
