import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import token.TokenType
import java.io.File

class FileSourceTokenizerTests {

    @Test
    fun testFileSourceWithProgramPrints() {
        val tempFile = File.createTempFile("program", ".prints")
        tempFile.writeText(
            """
            let x: number = 42;
            let message: string = "Hello World";
            let result: number = x + 8;
            println(message);
            println(result);
            """.trimIndent(),
        )

        val tokenizer = Tokenizer(FileSource(tempFile.absolutePath))

        assertEquals(Token(TokenType.LET, "let"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.ID, "x"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.COLON, ":"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.INT, "number"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.ASSIGN, "="), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.INT, "42"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.SEMI, ";"), tokenizer.getNextToken())

        tempFile.delete()
        tokenizer.close()
    }

    @Test
    fun testFileSourceWithStringDeclaration() {
        val tempFile = File.createTempFile("test", ".prints")
        tempFile.writeText("let message: string = \"Hello World\";")

        val tokenizer = Tokenizer(FileSource(tempFile.absolutePath))

        tokenizer.getNextToken() // let
        tokenizer.getNextToken() // espacio
        tokenizer.getNextToken() // message
        tokenizer.getNextToken() // :
        tokenizer.getNextToken() // espacio
        tokenizer.getNextToken() // string (palabra reservada)
        tokenizer.getNextToken() // espacio
        tokenizer.getNextToken() // =
        tokenizer.getNextToken() // espacio

        assertEquals(Token(TokenType.STRING, "Hello World"), tokenizer.getNextToken())

        tempFile.delete()
        tokenizer.close()
    }

    @Test
    fun testFileSourceWithArithmeticExpression() {
        val tempFile = File.createTempFile("arithmetic", ".prints")
        tempFile.writeText("let result: number = x + 8;")

        val tokenizer = Tokenizer(FileSource(tempFile.absolutePath))

        tokenizer.getNextToken() // let
        tokenizer.getNextToken() // espacio
        tokenizer.getNextToken() // result
        tokenizer.getNextToken() // :
        tokenizer.getNextToken() // espacio
        tokenizer.getNextToken() // number
        tokenizer.getNextToken() // espacio
        tokenizer.getNextToken() // =
        tokenizer.getNextToken() // espacio
        tokenizer.getNextToken() // x (ID)
        tokenizer.getNextToken() // espacio

        assertEquals(Token(TokenType.ADD, "+"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.INT, "8"), tokenizer.getNextToken())

        tempFile.delete()
        tokenizer.close()
    }

    @Test
    fun testFileSourceWithPrintlnStatements() {
        val tempFile = File.createTempFile("println", ".prints")
        tempFile.writeText("println(message);")

        val tokenizer = Tokenizer(FileSource(tempFile.absolutePath))

        assertEquals(Token(TokenType.PRINTLN, "println"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.LPAREN, "("), tokenizer.getNextToken())
        assertEquals(Token(TokenType.ID, "message"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.RPAREN, ")"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.SEMI, ";"), tokenizer.getNextToken())

        tempFile.delete()
        tokenizer.close()
    }

    @Test
    fun testFileSourceEOF() {
        val tempFile = File.createTempFile("eof", ".prints")
        tempFile.writeText("let x = 1;")

        val tokenizer = Tokenizer(FileSource(tempFile.absolutePath))

        tokenizer.getNextToken() // let
        tokenizer.getNextToken() // espacio
        tokenizer.getNextToken() // x
        tokenizer.getNextToken() // espacio
        tokenizer.getNextToken() // =
        tokenizer.getNextToken() // espacio
        tokenizer.getNextToken() // 1
        tokenizer.getNextToken() // ;

        assertEquals(Token(TokenType.EOF, ""), tokenizer.getNextToken())

        tempFile.delete()
        tokenizer.close()
    }
}
