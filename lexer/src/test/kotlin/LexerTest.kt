import lexer.LexResult
import lexer.Lexer
import matcher.TokenRuleMatcher
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.DefaultAsserter.fail

class LexerTest {

    @Test
    fun `Given JSON input When lexed Then produces correct tokens`() {
        // Given
        val input = """{ "key": true, "num": 123 }"""
        val matcher = TokenRuleMatcher(JsonAlphabet.alphabet)
        val lexer = Lexer(StringSourceReader(input), matcher)

        val expected = listOf(
            JsonTokenType.LBRACE to "{",
            JsonTokenType.STRING to "\"key\"",
            JsonTokenType.COLON to ":",
            JsonTokenType.TRUE to "true",
            JsonTokenType.COMMA to ",",
            JsonTokenType.STRING to "\"num\"",
            JsonTokenType.COLON to ":",
            JsonTokenType.NUMBER to "123",
            JsonTokenType.RBRACE to "}",
        )

        // When / Then
        for ((expectedType, expectedLexeme) in expected) {
            when (val result = lexer.lex()) {
                is LexResult.Success -> {
                    assertEquals(expectedType, result.token.type)
                    assertEquals(expectedLexeme, result.token.lexeme)
                }
                else -> fail("Expected $expectedType but got $result")
            }
        }
        assertEquals(LexResult.EOF, lexer.lex())
    }

    @Test
    fun `Given Python input When lexed Then produces correct tokens`() {
        // Given
        val input = "def add(x, y): return x + y"
        val matcher = TokenRuleMatcher(PythonAlphabet.alphabet)
        val lexer = Lexer(StringSourceReader(input), matcher)

        val expected = listOf(
            PythonTokenType.DEF to "def",
            PythonTokenType.IDENTIFIER to "add",
            PythonTokenType.LPAREN to "(",
            PythonTokenType.IDENTIFIER to "x",
            PythonTokenType.OPERATOR to ",",
            PythonTokenType.IDENTIFIER to "y",
            PythonTokenType.RPAREN to ")",
            PythonTokenType.COLON to ":",
            PythonTokenType.RETURN to "return",
            PythonTokenType.IDENTIFIER to "x",
            PythonTokenType.OPERATOR to "+",
            PythonTokenType.IDENTIFIER to "y",
        )

        // When / Then
        for ((expectedType, expectedLexeme) in expected) {
            when (val result = lexer.lex()) {
                is LexResult.Success -> {
                    assertEquals(expectedType, result.token.type)
                    assertEquals(expectedLexeme, result.token.lexeme)
                }
                else -> fail("Expected $expectedType but got $result")
            }
        }
    }

    @Test
    fun `Given JavaScript input When lexed Then produces correct tokens`() {
        // Given
        val input = "let total = 3 + 5;"
        val matcher = TokenRuleMatcher(JavaScriptAlphabet.alphabet)
        val lexer = Lexer(StringSourceReader(input), matcher)

        val expected = listOf(
            JsTokenType.LET to "let",
            JsTokenType.IDENTIFIER to "total",
            JsTokenType.ASSIGN to "=",
            JsTokenType.NUMBER to "3",
            JsTokenType.OPERATOR to "+",
            JsTokenType.NUMBER to "5",
            JsTokenType.SEMICOLON to ";",
        )

        // When / Then
        for ((expectedType, expectedLexeme) in expected) {
            when (val result = lexer.lex()) {
                is LexResult.Success -> {
                    assertEquals(expectedType, result.token.type)
                    assertEquals(expectedLexeme, result.token.lexeme)
                }
                else -> fail("Expected $expectedType but got $result")
            }
        }
    }

    @Test
    fun `Given arithmetic input When lexed Then produces correct tokens`() {
        // Given
        val input = "(3 + 4.5) * 2"
        val matcher = TokenRuleMatcher(ArithmeticAlphabet.alphabet)
        val lexer = Lexer(StringSourceReader(input), matcher)

        val expected = listOf(
            ArithmeticTokenType.LPAREN to "(",
            ArithmeticTokenType.NUMBER to "3",
            ArithmeticTokenType.OPERATOR to "+",
            ArithmeticTokenType.NUMBER to "4.5",
            ArithmeticTokenType.RPAREN to ")",
            ArithmeticTokenType.OPERATOR to "*",
            ArithmeticTokenType.NUMBER to "2",
        )

        // When / Then
        for ((expectedType, expectedLexeme) in expected) {
            when (val result = lexer.lex()) {
                is LexResult.Success -> {
                    assertEquals(expectedType, result.token.type)
                    assertEquals(expectedLexeme, result.token.lexeme)
                }
                else -> fail("Expected $expectedType but got $result")
            }
        }
    }
}
