import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

import util.PrintScriptV1_0Terminals
import internal.table.TerminalTableRegistry
import util.GoldenTester

class PrintScriptV1_0GoldenTest {
    companion object { const val VERSION = "1.0" }

    private fun run(testName: String) {
        TerminalTableRegistry.register(VERSION, PrintScriptV1_0Terminals)
        val lexer = PrintScriptLexer(VERSION)
        val mainPath = Path("src/test/resources/$VERSION/$testName/main.ps")
        val goldenPath = Path("src/test/resources/$VERSION/$testName/golden.ps")

        val main = GoldenTester.read(mainPath)
        val golden = GoldenTester.read(goldenPath)

        val tokens = lexer.lex(main.asSequence())
        val output = GoldenTester.format(tokens)

        assertEquals(golden.trim(), output.trim(), "FAILURE: $testName")
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
        run("unterminated-string")
    }

}