import lexer.Lexer
import lexer.matcher.token.BooleanLiteralMatcher
import lexer.matcher.token.IdentifierMatcher
import lexer.matcher.token.KeywordMatcher
import lexer.matcher.token.NumberMatcher
import lexer.matcher.token.StringMatcher
import lexer.matcher.token.SymbolMatcher
import lexer.matcher.trivia.WhitespaceMatcher
import org.junit.jupiter.api.Assertions.assertEquals
import token.Token
import token.TokenType
import java.nio.file.Files
import java.nio.file.Paths

abstract class BaseLexerGoldenTest {

    private fun buildLexer(code: String): Lexer {
        val tokenMatchers = listOf(
            KeywordMatcher("let", TokenType.LET),
            KeywordMatcher("const", TokenType.CONST),
            KeywordMatcher("if", TokenType.IF),
            KeywordMatcher("else", TokenType.ELSE),
            KeywordMatcher("println", TokenType.PRINT_LN),
            KeywordMatcher("readInput", TokenType.READ_INPUT),
            KeywordMatcher("readEnv", TokenType.READ_ENV),
            IdentifierMatcher(),
            NumberMatcher(),
            StringMatcher(),
            BooleanLiteralMatcher(),
            SymbolMatcher(';', TokenType.SEMICOLON),
            SymbolMatcher(':', TokenType.COLON),
            SymbolMatcher('=', TokenType.ASSIGN),
            SymbolMatcher('+', TokenType.PLUS),
            SymbolMatcher('-', TokenType.MINUS),
            SymbolMatcher('*', TokenType.MULTIPLY),
            SymbolMatcher('/', TokenType.DIVIDE),
            SymbolMatcher('{', TokenType.LBRACE),
            SymbolMatcher('}', TokenType.RBRACE),
            SymbolMatcher('(', TokenType.LPAREN),
            SymbolMatcher(')', TokenType.RPAREN),
        )
        val triviaMatchers = listOf(WhitespaceMatcher())
        return Lexer(code.iterator(), tokenMatchers, triviaMatchers)
    }

    protected fun runGoldenTest(version: String, case: String) {
        val inputPath = Paths.get("src/test/resources/$version/$case.ps")
        val expectedPath = Paths.get("src/test/resources/$version/$case.golden")

        val code = Files.readString(inputPath)
        val lexer = buildLexer(code)

        fun formatTokenResult(result: Result<Token>): String =
            result.fold(
                onSuccess = { token -> "${token.type}(${token.value})" },
                onFailure = { ex ->
                    val msg = ex.message ?: "Unknown error"
                    val badChar = Regex("'.'").find(msg)?.value ?: "<err>"
                    "<ERROR $badChar>"
                },
            )

        val tokenList = mutableListOf<String>()

        for (result in lexer) {
            val formatted = formatTokenResult(result)
            tokenList.add(formatted)

            if (result.isFailure) break
        }

        val tokens = tokenList.joinToString("\n")
        val expectedLines = Files.readAllLines(expectedPath).map { it.trimEnd() }
        val actualLines = tokens.lines().map { it.trimEnd() }

        assertEquals(expectedLines, actualLines, "Golden test failed for $version/$case")
    }
}
