package io.writer

import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.nio.file.Files
import java.nio.file.StandardCopyOption

class FileOutputWriter(
    private val filePath: String,
) : OutputWriter {

    @SuppressWarnings("TooGenericExceptionCaught")
    override fun write(input: Sequence<String>) {
        val targetFile = File(filePath)

        val parentDir = targetFile.parentFile
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs()
        }

        val tempFile = File.createTempFile(
            "format_temp_",
            ".tmp",
            parentDir ?: File("."),
        )

        try {
            BufferedWriter(FileWriter(tempFile, false)).use { writer ->
                for (line in input) {
                    writer.write(line)
                }
            }

            Files.move(
                tempFile.toPath(),
                targetFile.toPath(),
                StandardCopyOption.REPLACE_EXISTING,
                StandardCopyOption.ATOMIC_MOVE,
            )
        } catch (e: Exception) {
            tempFile.delete()
            throw e
        }
    }
}
