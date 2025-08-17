import interpreter.Interpreter
import lexer.Tokenizer
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import parser.Parser

class AppTest {

    private fun evaluate(src: String): Int {
        val tokenizer = Tokenizer(src)
        val parser = Parser(tokenizer)
        val ast = parser.parseProgram()
        return Interpreter().interpret(ast)
    }

    @Test
    fun `should parse and evaluate addition and subtraction correctly`() {
        assertEquals(6, evaluate("3 + 5 - 2"))
    }

    @Test
    fun `should handle unary operators`() {
        assertEquals(-5, evaluate("-5"))
        assertEquals(8, evaluate("-3 * -2 + 2"))
    }


    @Test
    fun `should handle single operand correctly`() {
        assertEquals(10, evaluate("10"))
    }

    @Test
    fun `should throw error on invalid expression`() {
        assertThrows(Error::class.java) {
            evaluate("3 +")
        }
    }

    @Test
    fun `should handle extra spaces correctly between tokens`() {
        assertEquals(6, evaluate(" 3   +   5 -  2 "))
    }

    @Test
    fun `should throw error on invalid characters`() {
        assertThrows(Error::class.java) {
            evaluate("3 & 5")
        }
    }

    @Test
    fun `should throw error on incomplete expression`() {
        assertThrows(Error::class.java) {
            evaluate("3 + 5 -")
        }
    }

    @Test
    fun `should handle multiple mult operand correctly`() {
        assertEquals(18, evaluate("3 + 5 * 3"))
    }

    @Test
    fun `should handle simple mult operand correctly`() {
        assertEquals(15, evaluate("5 * 3"))
    }

    @Test
    fun `should handle multiple div operand correctly`() {
        assertEquals(2, evaluate("20 / 5 / 2"))
    }

    @Test
    fun `should handle simple div operand correctly`() {
        assertEquals(5, evaluate("10 / 2"))
    }

    @Test
    fun `should handle multiple operations correctly`() {
        assertEquals(151, evaluate("15 * 10 + 10 / 5 - 2 + 5 / 5"))
    }

    @Test
    fun `should handle multiple spaces correctly`() {
        assertEquals(148, evaluate("15       *     10      + 5    /  5   - 3  "))
    }

    @Test
    fun `should handle no spaces correctly`() {
        assertEquals(148, evaluate("15*10+5/5-3"))
    }

    @Test
    fun `should handle parentheses around single number`() {
        assertEquals(10, evaluate("(10)"))
    }

    @Test
    fun `should honor precedence with parentheses`() {
        assertEquals(14, evaluate("2 * (3 + 4)"))
    }

    @Test
    fun `should handle nested parentheses correctly`() {
        assertEquals(22, evaluate("7 + 3 * (10 / (12 / (3 + 1) - 1))"))
    }

    @Test
    fun `should handle spaces with parentheses`() {
        assertEquals(15, evaluate(" (  2+3 ) * ( 4 - 1 ) "))
    }

    @Test
    fun `should throw error on unmatched opening parenthesis`() {
        assertThrows(Error::class.java) { evaluate("(1 + 2") }
    }

    @Test
    fun `should throw error on unmatched closing parenthesis`() {
        assertThrows(Error::class.java) { evaluate("1 + 2)") }
    }

    @Test
    fun `should throw error on empty parentheses`() {
        assertThrows(Error::class.java) { evaluate("()") }
    }

    @Test
    fun `should throw error on missing operator between parentheses`() {
        assertThrows(Error::class.java) { evaluate("(1 + 2)(3 + 4)") }
    }

    @Test
    fun `should handle redundant nested parentheses`() {
        assertEquals(15, evaluate("((15))"))
    }

    @Test
    fun `should handle operations with parentheses`() {
        assertEquals(19, evaluate("3 * (2 + 5) - (4 / 2)"))
    }
}
