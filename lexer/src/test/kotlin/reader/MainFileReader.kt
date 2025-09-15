package lexer.tests.reader

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class MainFileReader(
    private val mainDir: String = "src/test/resources/main/lexer"
) {

    fun readAsString(testName: String): String {
        val path: Path = Paths.get(mainDir, "$testName.txt")

        if (!Files.exists(path)) {
            throw IllegalArgumentException("Main file not found: $path")
        }

        return Files.readString(path).trim()
    }

}
