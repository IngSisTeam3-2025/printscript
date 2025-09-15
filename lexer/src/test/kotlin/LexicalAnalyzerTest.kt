import lexer.LexicalAnalyzer
import token.Token
import kotlin.test.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue

class LexicalAnalyzerTest {

    private fun tokenize(input: String): Pair<List<Token>, List<String>> {
        val reporter = TestReporter()
        val lexer = LexicalAnalyzer(
            input.iterator(),
            PrintScriptLexers.allLexers,
            reporter
        )
        val tokens = mutableListOf<Token>()
        while (lexer.hasNext()) {
            val result = lexer.next()
            result.onSuccess { tokens.add(it) }
        }
        return tokens to reporter.errors
    }

    @Test
    fun testKeywords() {
        val code = "let x: number = 5; const y: boolean = true;"
        val (tokens, errors) = tokenize(code)

        val expectedTypes = listOf(
            "LET", "IDENTIFIER", "COLON", "NUMBER_TYPE", "NUMBER_LITERAL",
            "SEMICOLON", "CONST", "IDENTIFIER", "COLON", "BOOLEAN_TYPE", "TRUE", "SEMICOLON"
        )

        assertEquals(14, tokens.size)
        assertEquals(0, errors.size)
        assertEquals(expectedTypes, tokens.map { it.type.name })
    }

    @Test
    fun testNumbers() {
        val code = "12 3.14 0.5 100."
        val (tokens, errors) = tokenize(code)

        val expectedLexemes = listOf("12", "3.14", "0.5", "100")
        assertEquals(4, tokens.size)
        assertEquals(expectedLexemes, tokens.map { it.lexeme })
        assertEquals(0, errors.size)
    }

    @Test
    fun testStrings() {
        val code = "\"hello\" 'world' \"multi\nline\""
        val (tokens, errors) = tokenize(code)

        val expectedTypes = listOf("STRING", "STRING", "STRING")
        assertEquals(3, tokens.size)
        assertEquals(expectedTypes, tokens.map { it.type.name })
        assertEquals(0, errors.size)
    }

    @Test
    fun testOperatorsAndPunctuation() {
        val code = "+ - * / = : ; ( )"
        val (tokens, errors) = tokenize(code)

        val expectedTypes = listOf(
            "PLUS", "MINUS", "MULTIPLY", "DIVIDE",
            "ASSIGN", "COLON", "SEMICOLON", "LPAREN", "RPAREN"
        )
        assertEquals(expectedTypes, tokens.map { it.type.name })
        assertEquals(0, errors.size)
    }

    @Test
    fun testWhitespaceSkipping() {
        val code = "   let   x  :  number ;  "
        val (tokens, errors) = tokenize(code)

        val expectedTypes = listOf("LET", "IDENTIFIER", "COLON", "NUMBER", "SEMICOLON")
        assertEquals(expectedTypes, tokens.map { it.type.name })
        assertEquals(0, errors.size)
    }

    @Test
    fun testMultiLineCode() {
        val code = """
            let a: number;
            let b: number;
            a = 10;
            b = a + 5;
        """.trimIndent()

        val (tokens, errors) = tokenize(code)
        val expectedTypes = listOf(
            "LET", "IDENTIFIER", "COLON", "NUMBER_TYPE", "SEMICOLON",
            "LET", "IDENTIFIER", "COLON", "NUMBER_TYPE", "SEMICOLON",
            "IDENTIFIER", "ASSIGN", "NUMBER_LITERAL", "SEMICOLON",
            "IDENTIFIER", "ASSIGN", "IDENTIFIER", "PLUS", "NUMBER_LITERAL", "SEMICOLON"
        )
        assertEquals(expectedTypes, tokens.map { it.type.name })
        assertEquals(0, errors.size)
    }

    @Test
    fun testErrorReporting() {
        val code = "let x: number = $"
        val (_, errors) = tokenize(code)

        assertTrue(errors.isNotEmpty())
        assertTrue(errors[0].contains("Unexpected character"))
    }
}