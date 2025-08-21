import lexer.Tokenizer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows

class TokenizerTest {

    @Test
    fun testSimpleVariableDeclaration() {
        val tokenizer = Tokenizer("let x = 5;")

        assertEquals(Token(TokenType.LET, "let"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.ID, "x"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.ASSIGN, "="), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.INT, "5"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.SEMI, ";"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.EOF, ""), tokenizer.getNextToken())
    }

    @Test
    fun testStringVariableDeclaration() {
        val tokenizer = Tokenizer("let name = \"Hello World\";")

        assertEquals(Token(TokenType.LET, "let"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.ID, "name"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.ASSIGN, "="), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.STRING, "Hello World"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.SEMI, ";"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.EOF, ""), tokenizer.getNextToken())
    }

    @Test
    fun testPrintStatement() {
        val tokenizer = Tokenizer("println(\"Hello\");")

        assertEquals(Token(TokenType.PRINTLN, "println"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.LPAREN, "("), tokenizer.getNextToken())
        assertEquals(Token(TokenType.STRING, "Hello"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.RPAREN, ")"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.SEMI, ";"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.EOF, ""), tokenizer.getNextToken())
    }

    @Test
    fun testArithmeticExpression() {
        val tokenizer = Tokenizer("let result = 10 + 5 - 2 * 3 / 1;")

        assertEquals(Token(TokenType.LET, "let"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.ID, "result"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.ASSIGN, "="), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.INT, "10"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.ADD, "+"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.INT, "5"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.SUB, "-"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.INT, "2"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.MUL, "*"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.INT, "3"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.DIV, "/"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.INT, "1"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.SEMI, ";"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.EOF, ""), tokenizer.getNextToken())
    }

    @Test
    fun testIdentifiers() {
        val tokenizer = Tokenizer("let _variable1 = myFunction123;")

        assertEquals(Token(TokenType.LET, "let"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.ID, "_variable1"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.ASSIGN, "="), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.ID, "myFunction123"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.SEMI, ";"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.EOF, ""), tokenizer.getNextToken())
    }

    @Test
    fun testSingleQuoteString() {
        val tokenizer = Tokenizer("let msg = 'Hello';")

        assertEquals(Token(TokenType.LET, "let"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.ID, "msg"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.ASSIGN, "="), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.STRING, "Hello"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.SEMI, ";"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.EOF, ""), tokenizer.getNextToken())
    }

    @Test
    fun testEmptyString() {
        val tokenizer = Tokenizer("let empty = \"\";")

        assertEquals(Token(TokenType.LET, "let"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.ID, "empty"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.ASSIGN, "="), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.STRING, ""), tokenizer.getNextToken())
        assertEquals(Token(TokenType.SEMI, ";"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.EOF, ""), tokenizer.getNextToken())
    }

    @Test
    fun testWhitespaceHandling() {
        val tokenizer = Tokenizer("   let   x   =   5   ;   ")

        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.LET, "let"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.ID, "x"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.ASSIGN, "="), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.INT, "5"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.SEMI, ";"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.EOF, ""), tokenizer.getNextToken())
    }

    @Test
    fun testTypeDeclarations() {
        val tokenizer = Tokenizer("let age: number = 25; let name: string = \"John\";")

        assertEquals(Token(TokenType.LET, "let"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.ID, "age"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.COLON, ":"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.INT, "number"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.ASSIGN, "="), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.INT, "25"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.SEMI, ";"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.LET, "let"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.ID, "name"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.COLON, ":"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.STRING, "string"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.ASSIGN, "="), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.STRING, "John"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.SEMI, ";"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.EOF, ""), tokenizer.getNextToken())
    }

    @Test
    fun testComplexExpression() {
        val tokenizer = Tokenizer("println((10 + 5) * 2);")

        assertEquals(Token(TokenType.PRINTLN, "println"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.LPAREN, "("), tokenizer.getNextToken())
        assertEquals(Token(TokenType.LPAREN, "("), tokenizer.getNextToken())
        assertEquals(Token(TokenType.INT, "10"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.ADD, "+"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.INT, "5"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.RPAREN, ")"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.MUL, "*"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.INT, "2"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.RPAREN, ")"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.SEMI, ";"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.EOF, ""), tokenizer.getNextToken())
    }

    @Test
    fun testOnlyWhitespace() {
        val tokenizer = Tokenizer("   \t  \n  ")
        for (i in 1..9) {
            assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        }
        assertEquals(Token(TokenType.EOF, ""), tokenizer.getNextToken())
    }

    // Tests para casos de error
    @Test
    fun testUnterminatedString() {
        val tokenizer = Tokenizer("let msg = \"Hello")

        assertEquals(Token(TokenType.LET, "let"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.ID, "msg"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.ASSIGN, "="), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())

        assertThrows<Error> {
            tokenizer.getNextToken() // Debería lanzar error por string sin cerrar
        }
    }

    @Test
    fun testInvalidCharacter() {
        val tokenizer = Tokenizer("let x = 5 & 3;") // '&' no es un carácter válido

        assertEquals(Token(TokenType.LET, "let"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.ID, "x"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.ASSIGN, "="), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())
        assertEquals(Token(TokenType.INT, "5"), tokenizer.getNextToken())
        assertEquals(Token(TokenType.WHITESPACE, " "), tokenizer.getNextToken())

        assertThrows<Error> {
            tokenizer.getNextToken() // Debería lanzar error por carácter inválido '&'
        }
    }

    // Tests para verificar funciones auxiliares
    @Test
    fun testPeekFunction() {
        val tokenizer = Tokenizer("ab")
        assertEquals('b', tokenizer.peek()) // Debería ver el siguiente carácter sin avanzar
        assertEquals(Token(TokenType.ID, "ab"), tokenizer.getNextToken()) // Debería tokenizar todo el identificador
    }

    @Test
    fun testColumnTracking() {
        val tokenizer = Tokenizer("let x")
        assertEquals(0, tokenizer.column()) // Posición inicial

        tokenizer.getNextToken() // "let"
        assertEquals(3, tokenizer.column()) // Después de "let"

        tokenizer.getNextToken() // " " (whitespace)
        assertEquals(4, tokenizer.column()) // Después del whitespace

        tokenizer.getNextToken() // "x"
        assertEquals(5, tokenizer.column()) // Después de "x"
    }

    // Test estilo Python REPL como en tu ejemplo
    @Test
    fun testPythonStyleExample() {
        val tokenizer = Tokenizer("let a = 2;")

        // Simula el comportamiento del ejemplo de Python
        var token = tokenizer.getNextToken()
        assertEquals("Token(LET, 'let')", "Token(${token.type}, '${token.lexeme}')")

        token = tokenizer.getNextToken() // whitespace
        assertEquals("Token(WHITESPACE, ' ')", "Token(${token.type}, '${token.lexeme}')")

        token = tokenizer.getNextToken()
        assertEquals("Token(ID, 'a')", "Token(${token.type}, '${token.lexeme}')")

        token = tokenizer.getNextToken() // whitespace
        assertEquals("Token(WHITESPACE, ' ')", "Token(${token.type}, '${token.lexeme}')")

        token = tokenizer.getNextToken()
        assertEquals("Token(ASSIGN, '=')", "Token(${token.type}, '${token.lexeme}')")

        token = tokenizer.getNextToken() // whitespace
        assertEquals("Token(WHITESPACE, ' ')", "Token(${token.type}, '${token.lexeme}')")

        token = tokenizer.getNextToken()
        assertEquals("Token(INT, '2')", "Token(${token.type}, '${token.lexeme}')")

        token = tokenizer.getNextToken()
        assertEquals("Token(SEMI, ';')", "Token(${token.type}, '${token.lexeme}')")

        token = tokenizer.getNextToken()
        assertEquals("Token(EOF, '')", "Token(${token.type}, '${token.lexeme}')")
    }
}