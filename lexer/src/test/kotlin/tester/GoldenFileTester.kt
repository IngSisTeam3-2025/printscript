package lexer.tests.tester

import org.junit.jupiter.api.Assertions.assertEquals
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class GoldenFileTester(
    private val goldenDir: String,
    private val updateGolden: Boolean = System.getProperty("updateGolden") == "true"
) {

    fun assertMatchesGolden(
        testName: String,
        actualOutput: String
    ) {
        val path: Path = Paths.get(goldenDir, "$testName.txt")

        if (!Files.exists(path)) {
            throw IllegalArgumentException("Golden file not found: $path")
        }

        if (updateGolden || !Files.exists(path)) {
            Files.createDirectories(path.parent)
            Files.write(path, actualOutput.toByteArray())
            println("Golden file created/updated: $path")
        } else {
            val expected = Files.readString(path).trim()
            val actual = actualOutput.trim()
            assertEquals(expected, actual, "Output does not match golden file: $testName")
        }
    }
}
