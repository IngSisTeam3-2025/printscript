import lexer.PrintScriptLexer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.GoldenTester
import kotlin.io.path.Path

class PrintScriptV11GoldenTest {
    companion object {
        const val VERSION = "1.1"
    }

    private fun run(testName: String) {
        val lexer = PrintScriptLexer()
        val mainPath = Path("src/test/resources/$VERSION/$testName/main.ps")
        val goldenPath = Path("src/test/resources/$VERSION/$testName/golden.ps")

        val main = GoldenTester.read(mainPath)
        val source = main.asSequence()

        val results = lexer.lex(VERSION, source)
        val actual = GoldenTester.format(results)
        val expected = GoldenTester.read(goldenPath)

        assertEquals(expected.trim(), actual.trim(), "FAILURE: $testName")
    }

    @Test
    fun `test case 1`() {
        run("const-declaration")
    }

    @Test
    fun `test case 2`() {
        run("if-else-block")
    }

    @Test
    fun `test case 3`() {
        run("readEnv-statement")
    }

    @Test
    fun `test case 4`() {
        run("readInput-statement")
    }

    @Test
    fun `test case 5`() {
        run("string-keyword-tie")
    }
}
