import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

import util.PrintScriptV1_1Terminals
import internal.table.TerminalTableRegistry
import util.GoldenTester

class PrintScriptV1_1GoldenTest {
    companion object { const val VERSION = "1.1" }

    private fun run(testName: String) {
        TerminalTableRegistry.register(VERSION, PrintScriptV1_1Terminals)
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