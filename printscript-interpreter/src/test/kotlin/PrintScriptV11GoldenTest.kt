import interpreter.PrintScriptInterpreter
import io.reader.env.MockEnvReader
import io.reader.input.MockInputReader
import io.writer.MockOutputWriter
import model.value.IntegerValue
import model.value.StringValue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import util.GoldenTester
import util.PrintScriptV11TestSnippets
import util.TestSnippetRegistry
import kotlin.io.path.Path

class PrintScriptV11GoldenTest {
    companion object {
        const val VERSION = "1.1"
        private val interpreter = PrintScriptInterpreter()

        @JvmStatic
        @BeforeAll
        fun setup() {
            PrintScriptV11TestSnippets
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
    fun `test case 1`() {
        run("basic-const-assignment")
    }

    @Test
    fun `test case 2`() {
        run("if-else-with-println-statement")
    }

    @Test
    fun `test case 3`() {
        run("invalid-const-assignment")
    }

    @Test
    fun `test case 4`() {
        run("invalid-readEnv-statement")
    }

    @Test
    fun `test case 5`() {
        run("missing-readEnv-key")
    }

    @Test
    fun `test case 6`() {
        val envReader = MockEnvReader(mapOf("NUMBER" to IntegerValue(7)))
        run(
            "readEnv-inside-expression",
            envReader = envReader,
        )
    }

    @Test
    fun `test case 7`() {
        val envReader = MockEnvReader(mapOf("STRING" to StringValue("Hello, World!")))
        run(
            "readEnv-statement",
            envReader = envReader,
        )
    }

    @Test
    fun `test case 8`() {
        val inputReader = MockInputReader(listOf("Juan"))
        run(
            "readInput-inside-expression",
            inputReader = inputReader,
        )
    }

    @Test
    fun `test case 9`() {
        val inputReader = MockInputReader(listOf("Buenos Aires"))
        run(
            "readInput-statement",
            inputReader = inputReader,
        )
    }
}
