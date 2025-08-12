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

    @Test
    fun `should handle multiple mult operand correctly`() {
        val tokenizerMultiple = Tokenizer("3 + 5 * 3")
        val interpreterSingle = Interpreter(tokenizerMultiple)
        val result = interpreterSingle.expr()
        assertEquals(18, result)
    }

    @Test
    fun `should handle simple mult operand correctly`() {
        val tokenizerMultiple = Tokenizer("5 * 3")
        val interpreterSingle = Interpreter(tokenizerMultiple)
        val result = interpreterSingle.expr()
        assertEquals(15, result)
    }

    @Test
    fun `should handle multiple div operand correctly`() {
        val tokenizerMultiple = Tokenizer("20 / 5 / 2")
        val interpreterSingle = Interpreter(tokenizerMultiple)
        val result = interpreterSingle.expr()
        assertEquals(2, result)
    }

    @Test
    fun `should handle simple div operand correctly`() {
        val tokenizerMultiple = Tokenizer("10 / 2")
        val interpreterSingle = Interpreter(tokenizerMultiple)
        val result = interpreterSingle.expr()
        assertEquals(5, result)
    }

    @Test
    fun `should handle multiple operations correctly`() {
        val tokenizerMultiple = Tokenizer("15 * 10 + 10 / 5 - 2 + 5 / 5")
        val interpreterSingle = Interpreter(tokenizerMultiple)
        val result = interpreterSingle.expr()
        assertEquals(151, result)
    }

    @Test
    fun `should handle multiple spaces correctly`() {
        val tokenizerMultiple = Tokenizer("15       *     10      + 5    /  5   - 3  ")
        val interpreterSingle = Interpreter(tokenizerMultiple)
        val result = interpreterSingle.expr()
        assertEquals(148, result)
    }

    @Test
    fun `should handle no spaces correctly`() {
        val tokenizerMultiple = Tokenizer("15*10+5/5-3")
        val interpreterSingle = Interpreter(tokenizerMultiple)
        val result = interpreterSingle.expr()
        assertEquals(148, result)
    }

    @Test
    fun `should handle parentheses around single number`() {
        val tokenizerParens = Tokenizer("(10)")
        val interpreterParens = Interpreter(tokenizerParens)
        val result = interpreterParens.expr()
        assertEquals(10, result)
    }

    @Test
    fun `should honor precedence with parentheses`() {
        val tokenizerP = Tokenizer("2 * (3 + 4)")
        val interpreterP = Interpreter(tokenizerP)
        val result = interpreterP.expr()
        assertEquals(14, result)
    }

    @Test
    fun `should handle nested parentheses correctly`() {
        val tokenizerNested = Tokenizer("7 + 3 * (10 / (12 / (3 + 1) - 1))")
        val interpreterNested = Interpreter(tokenizerNested)
        val result = interpreterNested.expr()
        assertEquals(22, result)
    }

    @Test
    fun `should handle spaces with parentheses`() {
        val tokenizerSpaces = Tokenizer(" (  2+3 ) * ( 4 - 1 ) ")
        val interpreterSpaces = Interpreter(tokenizerSpaces)
        val result = interpreterSpaces.expr()
        assertEquals(15, result)
    }

    @Test
    fun `should throw error on unmatched opening parenthesis`() {
        val t = Tokenizer("(1 + 2")
        val i = Interpreter(t)
        assertThrows(Error::class.java) { i.expr() }
    }

    @Test
    fun `should throw error on unmatched closing parenthesis`() {
        val t = Tokenizer("1 + 2)")
        val i = Interpreter(t)
        assertThrows(Error::class.java) { i.expr() }
    }

    @Test
    fun `should throw error on empty parentheses`() {
        val t = Tokenizer("()")
        val i = Interpreter(t)
        assertThrows(Error::class.java) { i.expr() }
    }

    @Test
    fun `should throw error on missing operator between parentheses`() {
        val t = Tokenizer("(1 + 2)(3 + 4)")
        val i = Interpreter(t)
        assertThrows(Error::class.java) { i.expr() }
    }

    @Test
    fun `should handle redundant nested parentheses`() {
        val t = Tokenizer("((15))")
        val i = Interpreter(t)
        val r = i.expr()
        assertEquals(15, r)
    }

    @Test
    fun `should handle operations with parentheses`() {
        val t = Tokenizer("3 * (2 + 5) - (4 / 2)")
        val i = Interpreter(t)
        val r = i.expr()
        assertEquals(19, r)
    }


}
