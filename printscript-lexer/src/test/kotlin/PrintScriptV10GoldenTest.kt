import lexer.PrintScriptLexer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.GoldenTester
import kotlin.io.path.Path

class PrintScriptV10GoldenTest {
    companion object {
        const val VERSION = "1.0"
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
        run("basic-arithmetic-expression")
    }

    @Test
    fun `test case 2`() {
        run("integers-and-floats")
    }

    @Test
    fun `test case 3`() {
        run("invalid-identifier")
    }

    @Test
    fun `test case 4`() {
        run("leading-and-trailing-trivia")
    }

    @Test
    fun `test case 5`() {
        run("nested-string")
    }

    @Test
    fun `test case 6`() {
        run("println-statement")
    }

    @Test
    fun `test case 7`() {
        run("string-declaration")
    }

    @Test
    fun `test case 8`() {
        run("string-keyword-tie")
    }

    @Test
    fun `test case 9`() {
        run("tokens-as-string")
    }

    @Test
    fun `test case 10`() {
        run("trivia-with-no-tokens")
    }

    @Test
    fun `test case 11`() {
        run("unterminated-dq-string")
    }

    @Test
    fun `test case 12`() {
        run("unterminated-sq-string")
    }
}
