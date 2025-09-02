import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class AppTest {

    private fun evaluate(src: String): Any {
        val tokenizer = Tokenizer(StringSourceReader(src))
        val stream = ParserTokenStream(tokenizer)
        val parser = parser.Parser(stream)
        val program = parser.parseProgram()

        val rv = Interpreter().visit(program)
        return when (rv) {
            is RuntimeValue.Num -> rv.v
            is RuntimeValue.Void -> error("La última sentencia no produce valor (Void)")
            is RuntimeValue.Str -> rv.v
            else -> {}
        }
    }

    @Test
    fun `should parse and evaluate addition and subtraction correctly`() {
        assertEquals(6, evaluate("3 + 5 - 2;"))
    }

    @Test
    fun `should handle unary operators`() {
        assertEquals(-5, evaluate("-5;"))
        assertEquals(8, evaluate("-3 * -2 + 2;"))
    }

    @Test
    fun `should handle single operand correctly`() {
        assertEquals(10, evaluate("10;"))
    }

    @Test
    fun `should throw error on invalid expression`() {
        assertThrows(Error::class.java) {
            evaluate("3 +;")
        }
    }

    @Test
    fun `should handle extra spaces correctly between tokens`() {
        assertEquals(6, evaluate(" 3   +   5 -  2;"))
    }

    @Test
    fun `should throw error on invalid characters`() {
        assertThrows(Error::class.java) {
            evaluate("3 & 5;")
        }
    }

    @Test
    fun `should throw error on incomplete expression`() {
        assertThrows(Error::class.java) {
            evaluate("3 + 5 -;")
        }
    }

    @Test
    fun `should handle multiple mult operand correctly`() {
        assertEquals(18, evaluate("3 + 5 * 3;"))
    }

    @Test
    fun `should handle simple mult operand correctly`() {
        assertEquals(15, evaluate("5 * 3;"))
    }

    @Test
    fun `should handle multiple div operand correctly`() {
        assertEquals(2, evaluate("20 / 5 / 2;"))
    }

    @Test
    fun `should handle simple div operand correctly`() {
        assertEquals(5, evaluate("10 / 2;"))
    }

    @Test
    fun `should handle multiple operations correctly`() {
        assertEquals(151, evaluate("15 * 10 + 10 / 5 - 2 + 5 / 5;"))
    }

    @Test
    fun `should handle multiple spaces correctly`() {
        assertEquals(148, evaluate("15       *     10      + 5    /  5   - 3  ;"))
    }

    @Test
    fun `should handle no spaces correctly`() {
        assertEquals(148, evaluate("15*10+5/5-3;"))
    }

    @Test
    fun `should handle parentheses around single number`() {
        assertEquals(10, evaluate("(10);"))
    }

    @Test
    fun `should honor precedence with parentheses`() {
        assertEquals(14, evaluate("2 * (3 + 4);"))
    }

    @Test
    fun `should handle nested parentheses correctly`() {
        assertEquals(22, evaluate("7 + 3 * (10 / (12 / (3 + 1) - 1));"))
    }

    @Test
    fun `should handle spaces with parentheses`() {
        assertEquals(15, evaluate(" (  2+3 ) * ( 4 - 1 ) ;"))
    }

    @Test
    fun `should throw error on unmatched opening parenthesis`() {
        assertThrows(Error::class.java) { evaluate("(1 + 2;") }
    }

    @Test
    fun `should throw error on unmatched closing parenthesis`() {
        assertThrows(Error::class.java) { evaluate("1 + 2);") }
    }

    @Test
    fun `should throw error on empty parentheses`() {
        assertThrows(Error::class.java) { evaluate("();") }
    }

    @Test
    fun `should throw error on missing operator between parentheses`() {
        assertThrows(Error::class.java) { evaluate("(1 + 2)(3 + 4);") }
    }

    @Test
    fun `should handle redundant nested parentheses`() {
        assertEquals(15, evaluate("((15));"))
    }

    @Test
    fun `should handle operations with parentheses`() {
        assertEquals(19, evaluate("3 * (2 + 5) - (4 / 2);"))
    }

    // Tests para declaraciones de variables con strings
    @Test
    fun `should declare and evaluate string variable`() {
        assertEquals("hola", evaluate("let x: string = \"hola\"; x;"))
    }

    @Test
    fun `should declare string variable and print it`() {
        // Asumiendo que println retorna el valor que imprime
        assertEquals("hola", evaluate("let x: string = \"hola\"; x;"))
    }

    @Test
    fun `should handle string with spaces`() {
        assertEquals("hello world", evaluate("let message: string = \"hello world\"; message;"))
    }

    @Test
    fun `should handle empty string`() {
        assertEquals("", evaluate("let empty: string = \"\"; empty;"))
    }

    // Tests para declaraciones de variables con números
    @Test
    fun `should declare and evaluate number variable`() {
        assertEquals(20, evaluate("let y: number = 20; y;"))
    }

    @Test
    fun `should declare number variable and print it`() {
        assertEquals(20, evaluate("let y: number = 20; y;"))
    }

    @Test
    fun `should handle negative number variable`() {
        assertEquals(-15, evaluate("let negative: number = -15; negative;"))
    }

    @Test
    fun `should handle zero value`() {
        assertEquals(0, evaluate("let zero: number = 0; zero;"))
    }

    // Tests para operaciones con múltiples variables
    @Test
    fun `should multiply two declared variables`() {
        assertEquals(1000, evaluate("let z: number = 20; let y: number = 50; y * z;"))
    }

    @Test
    fun `should add two declared variables`() {
        assertEquals(70, evaluate("let z: number = 20; let y: number = 50; y + z;"))
    }

    @Test
    fun `should subtract two declared variables`() {
        assertEquals(30, evaluate("let z: number = 20; let y: number = 50; y - z;"))
    }

    @Test
    fun `should divide two declared variables`() {
        assertEquals(2, evaluate("let z: number = 25; let y: number = 50; y / z;"))
    }

    @Test
    fun `should handle complex operations with variables`() {
        assertEquals(115, evaluate("let a: number = 10; let b: number = 20; let c: number = 5; a + b * c + c;"))
    }

    @Test
    fun `should handle operations with parentheses and variables`() {
        assertEquals(150, evaluate("let x: number = 5; let y: number = 10; (x + y) * y;"))
    }

    // Tests para reasignación de variables (si está soportado)
    @Test
    fun `should handle variable reassignment`() {
        assertEquals(30, evaluate("let x: number = 10; x = 30; x;"))
    }

    @Test
    fun `should reassign with operation result`() {
        assertEquals(25, evaluate("let x: number = 10; let y: number = 15; x = x + y; x;"))
    }

    @Test
    fun `should concatenate string and number`() {
        assertEquals("hello5", evaluate("let x: string = \"hello\"; let y: number = 5; x + y;"))
    }

    @Test
    fun `should throw error when trying to multiply strings`() {
        assertThrows(Exception::class.java) {
            evaluate("let x: string = \"hello\"; let y: string = \"world\"; x * y;")
        }
    }

    @Test
    fun `should throw error when assigning wrong type`() {
        assertThrows(Exception::class.java) {
            evaluate("let x: number = \"hello\";")
        }
    }

    @Test
    fun `should throw error when assigning number to string variable`() {
        assertThrows(Exception::class.java) {
            evaluate("let x: string = 42;")
        }
    }

    // Tests para variables no declaradas
    @Test
    fun `should throw error on undeclared variable`() {
        assertThrows(Exception::class.java) {
            evaluate("x;")
        }
    }

    @Test
    fun `should throw error on undeclared variable in operation`() {
        assertThrows(Exception::class.java) {
            evaluate("let x: number = 10; x + y;")
        }
    }

    // Tests para múltiples declaraciones y uso
    @Test
    fun `should handle multiple variables in sequence`() {
        assertEquals(15, evaluate("let a: number = 5; let b: number = 10; let result: number = a + b; result;"))
    }

    @Test
    fun `should handle mixed variable types in separate operations`() {
        assertEquals("test", evaluate("let name: string = \"test\"; let age: number = 25; name;"))
    }

    // Tests para declaraciones sin inicialización (si está soportado)
    @Test
    fun `should handle variable declaration without initialization`() {
        assertThrows(Exception::class.java) {
            evaluate("let x: number; x;")
        }
    }

    // Tests para println con variables
    @Test
    fun `should print variable result correctly`() {
        assertEquals(100, evaluate("let x: number = 10; let y: number = 10; x * y;"))
    }

    @Test
    fun `should print string variable correctly`() {
        assertEquals("goodbye", evaluate("let farewell: string = \"goodbye\"; farewell;"))
    }
}
