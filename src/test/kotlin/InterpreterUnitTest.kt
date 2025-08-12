import common.Interpreter
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import scanning.Tokenizer

class InterpreterUnitTest {

    private val tokenizer = Tokenizer("3 + 5 - 2")
    private val interpreter = Interpreter(tokenizer)

    @Test
    fun `should parse and evaluate addition and subtraction correctly`() {
        val result = interpreter.expr()
        assertEquals(6, result)
    }

    @Test
    fun `should handle single operand correctly`() {
        val tokenizerSingle = Tokenizer("10")
        val interpreterSingle = Interpreter(tokenizerSingle)
        val result = interpreterSingle.expr()
        assertEquals(10, result)
    }

    @Test
    fun `should throw error on invalid expression`() {
        val tokenizerInvalid = Tokenizer("3 +")
        val interpreterInvalid = Interpreter(tokenizerInvalid)

        assertThrows(Error::class.java) {
            interpreterInvalid.expr()
        }
    }

    @Test
    fun `should handle extra spaces correctly between tokens`() {
        val tokenizerWithSpaces = Tokenizer(" 3   +   5 -  2 ")
        val interpreterWithSpaces = Interpreter(tokenizerWithSpaces)

        val result = interpreterWithSpaces.expr()
        assertEquals(6, result)
    }

    @Test
    fun `should throw error on invalid characters`() {
        val tokenizerInvalidChar = Tokenizer("3 & 5")
        val interpreterInvalidChar = Interpreter(tokenizerInvalidChar)

        assertThrows(Error::class.java) {
            interpreterInvalidChar.expr()
        }
    }

    @Test
    fun `should throw error on incomplete expression`() {
        val tokenizerIncomplete = Tokenizer("3 + 5 -")
        val interpreterIncomplete = Interpreter(tokenizerIncomplete)

        assertThrows(Error::class.java) {
            interpreterIncomplete.expr()
        }
    }

}
