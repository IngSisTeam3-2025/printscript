import interpreter.PrintScriptInterpreter
import io.reader.env.MockEnvReader
import io.reader.input.MockInputReader
import io.writer.MockOutputWriter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import util.GoldenTester
import util.PrintScriptV10TestSnippets
import util.TestSnippetRegistry
import kotlin.io.path.Path

class PrintScriptV10GoldenTest {

    companion object {
        const val VERSION = "1.0"
        private val interpreter = PrintScriptInterpreter()

        @JvmStatic
        @BeforeAll
        fun setup() {
            PrintScriptV10TestSnippets
        }

        fun getSnippet(testName: String) = TestSnippetRegistry.get(testName).asSequence()
    }

    private fun run(
        testName: String,
        inputReader: MockInputReader = MockInputReader(emptyList()),
        envReader: MockEnvReader = MockEnvReader(emptyMap()),
    ) {
        val goldenPath = Path("src/test/resources/$VERSION/$testName/golden.ps")
        val outputWriter = MockOutputWriter()
        val nodes = getSnippet(testName)

        val results = interpreter.interpret(
            VERSION,
            nodes,
            inputReader,
            outputWriter,
            envReader,
        )

        val actual = GoldenTester.format(results, outputWriter)
        val expected = GoldenTester.read(goldenPath)

        assertEquals(expected.trim(), actual.trim(), "FAILURE: $testName")
    }

    @Test
    fun `basic-assignment`() {
        run("basic-assignment")
    }

    @Test
    fun `basic-binary-operation`() {
        run("basic-binary-operation")
    }

    @Test
    fun `basic-unary-operation`() {
        run("basic-unary-operation")
    }

    @Test
    fun `test case 1`() {
        run("empty-println")
    }

    @Test
    fun `test case 2`() {
        run("invalid-binary-operation")
    }

    @Test
    fun `test case 3`() {
        run("invalid-unary-operation")
    }

    @Test
    fun `test case 4`() {
        run("let-invalid-assignment")
    }

    @Test
    fun `test case 5`() {
        run("none-value")
    }

    @Test
    fun `test case 6`() {
        run("println-statement")
    }

    @Test
    fun `test case 7`() {
        run("string-coercion")
    }

    @Test
    fun `test case 8`() {
        run("undefined-identifier")
    }
}
