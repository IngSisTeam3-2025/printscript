import lexer.StringSource
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import lexer.Tokenizer

class AstPrinterTest {

    private fun printAst(src: String): String {
        val tokenizer = Tokenizer(StringSource(src))
        val stream = ParserTokenStream(tokenizer)
        val parser = Parser(stream)
        val ast = parser.parseProgram()
        return AstPrinter().print(ast)
    }

    @Test
    fun `should print single number without extra wrapping`() {
        val expected = """
            10
        """.trimIndent()
        assertEquals(expected, printAst("10;"))
    }

    @Test
    fun `should print simple addition as parenthesized PLUS with two indented numbers`() {
        val expected = """
            (ADD
              3
              5
            )
        """.trimIndent()
        assertEquals(expected, printAst("3 + 5;"))
    }

    @Test
    fun `should print unary minus as parenthesized MINUS with one indented expr`() {
        val expected = """
            (SUB
              5
            )
        """.trimIndent()
        assertEquals(expected, printAst("-5;"))
    }

    @Test
    fun `should print nested binary operations respecting precedence`() {
        // 3 + 5 * 2  => PLUS(3, MUL(5, 2))
        val expected = """
            (ADD
              3
              (MUL
                5
                2
              )
            )
        """.trimIndent()
        assertEquals(expected, printAst("3 + 5 * 2;"))
    }

    @Test
    fun `should print parentheses affecting precedence`() {
        // (3 + 5) * 2  => MUL(PLUS(3,5), 2)
        val expected = """
            (MUL
              (ADD
                3
                5
              )
              2
            )
        """.trimIndent()
        assertEquals(expected, printAst("(3 + 5) * 2;"))
    }

    @Test
    fun `should print nested parentheses`() {
        // 7 + 3 * (10 / (12 / (3 + 1) - 1))
        val expected = """
            (ADD
              7
              (MUL
                3
                (DIV
                  10
                  (SUB
                    (DIV
                      12
                      (ADD
                        3
                        1
                      )
                    )
                    1
                  )
                )
              )
            )
        """.trimIndent()
        assertEquals(expected, printAst("7 + 3 * (10 / (12 / (3 + 1) - 1));"))
    }

    @Test
    fun `should print expression with surrounding spaces identically to no spaces`() {
        val expected = """
            (SUB
              (MUL
                (ADD
                  2
                  3
                )
                4
              )
              (DIV
                10
                5
              )
            )
        """.trimIndent()
        val withSpaces = " (  (2 + 3) * 4 ) - ( 10 / 5 ) ;"
        val noSpaces  = "((2+3)*4)-(10/5);"
        assertEquals(expected, printAst(withSpaces))
        assertEquals(expected, printAst(noSpaces))
    }

    @Test
    fun `should print unary applied to parenthesized expression`() {
        // -(3 + 2) => MINUS(PLUS(3,2))
        val expected = """
            (SUB
              (ADD
                3
                2
              )
            )
        """.trimIndent()
        assertEquals(expected, printAst("-(3 + 2);"))
    }

    @Test
    fun `should print combined operations with all operators`() {
        // 15 * 10 + 10 / 5 - 2 + 5 / 5
        // => PLUS( MINUS( PLUS( MUL(15,10), DIV(10,5) ), 2 ), DIV(5,5) )
        val expected = """
            (ADD
              (SUB
                (ADD
                  (MUL
                    15
                    10
                  )
                  (DIV
                    10
                    5
                  )
                )
                2
              )
              (DIV
                5
                5
              )
            )
        """.trimIndent()
        assertEquals(expected, printAst("15 * 10 + 10 / 5 - 2 + 5 / 5;"))
    }

    @Test
    fun `should print simple number variable declaration`() {
        val expected = """
            (VAR_DECL
              NAME x
              TYPE number
              INIT
                10
            )
        """.trimIndent()
        assertEquals(expected, printAst("let x: number = 10;"))
    }

    @Test
    fun `should print simple string variable declaration`() {
        val expected = """
            (VAR_DECL
              NAME message
              TYPE string
              INIT
                "hello world"
            )
        """.trimIndent()
        assertEquals(expected, printAst("let message: string = \"hello world\";"))
    }

    @Test
    fun `should print variable declaration without type annotation`() {
        val expected = """
            (VAR_DECL
              NAME x
              TYPE number
              INIT
                42
            )
        """.trimIndent()
        assertEquals(expected, printAst("let x: number = 42;"))
    }

    @Test
    fun `should print variable declaration without initialization`() {
        val expected = """
            (VAR_DECL
              NAME x
              TYPE number
            )
        """.trimIndent()
        assertEquals(expected, printAst("let x: number;"))
    }

    // Tests para uso de variables
    @Test
    fun `should print simple variable reference`() {
        val expected = """
            x
        """.trimIndent()
        assertEquals(expected, printAst("x;"))
    }

    @Test
    fun `should print variable in binary operation`() {
        val expected = """
            (ADD
              x
              5
            )
        """.trimIndent()
        assertEquals(expected, printAst("x + 5;"))
    }

    @Test
    fun `should print two variables in operation`() {
        val expected = """
            (MUL
              x
              y
            )
        """.trimIndent()
        assertEquals(expected, printAst("x * y;"))
    }

    // Tests para asignaciones
    @Test
    fun `should print simple assignment`() {
        val expected = """
            (ASSIGN
              x
              25
            )
        """.trimIndent()
        assertEquals(expected, printAst("x = 25;"))
    }

    @Test
    fun `should print assignment with expression`() {
        val expected = """
            (ASSIGN
              result
              (ADD
                x
                y
              )
            )
        """.trimIndent()
        assertEquals(expected, printAst("result = x + y;"))
    }

    @Test
    fun `should print assignment with complex expression`() {
        val expected = """
            (ASSIGN
              total
              (MUL
                (ADD
                  a
                  b
                )
                c
              )
            )
        """.trimIndent()
        assertEquals(expected, printAst("total = (a + b) * c;"))
    }

    // Tests para println
    @Test
    fun `should print println with variable`() {
        val expected = """
            (PRINTLN
              x
            )
        """.trimIndent()
        assertEquals(expected, printAst("println(x);"))
    }

    @Test
    fun `should print println with string literal`() {
        val expected = """
            (PRINTLN
              "Hello, World!"
            )
        """.trimIndent()
        assertEquals(expected, printAst("println(\"Hello, World!\");"))
    }

    @Test
    fun `should print println with expression`() {
        val expected = """
            (PRINTLN
              (ADD
                x
                y
              )
            )
        """.trimIndent()
        assertEquals(expected, printAst("println(x + y);"))
    }

    @Test
    fun `should print println without arguments`() {
        val expected = """
            (PRINTLN)
        """.trimIndent()
        assertEquals(expected, printAst("println();"))
    }

    // Tests para múltiples statements
    @Test
    fun `should print multiple variable declarations`() {
        val expected = """
            (VAR_DECL
              NAME x
              TYPE number
              INIT
                10
            )
            (VAR_DECL
              NAME y
              TYPE number
              INIT
                20
            )
        """.trimIndent()
        assertEquals(expected, printAst("let x: number = 10; let y: number = 20;"))
    }

    @Test
    fun `should print declaration followed by usage`() {
        val expected = """
            (VAR_DECL
              NAME x
              TYPE number
              INIT
                10
            )
            x
        """.trimIndent()
        assertEquals(expected, printAst("let x: number = 10; x;"))
    }

    @Test
    fun `should print declaration assignment and usage`() {
        val expected = """
            (VAR_DECL
              NAME x
              TYPE number
              INIT
                10
            )
            (ASSIGN
              x
              20
            )
            x
        """.trimIndent()
        assertEquals(expected, printAst("let x: number = 10; x = 20; x;"))
    }

    // Tests complejos combinando todo
    @Test
    fun `should print complex program with variables and operations`() {
        val expected = """
            (VAR_DECL
              NAME a
              TYPE number
              INIT
                5
            )
            (VAR_DECL
              NAME b
              TYPE number
              INIT
                10
            )
            (VAR_DECL
              NAME result
              TYPE number
              INIT
                (MUL
                  (ADD
                    a
                    b
                  )
                  2
                )
            )
            (PRINTLN
              result
            )
        """.trimIndent()
        assertEquals(expected, printAst("let a: number = 5; let b: number = 10; let result: number = (a + b) * 2; println(result);"))
    }

    @Test
    fun `should print string and number variables together`() {
        val expected = """
            (VAR_DECL
              NAME name
              TYPE string
              INIT
                "Alice"
            )
            (VAR_DECL
              NAME age
              TYPE number
              INIT
                25
            )
            (PRINTLN
              name
            )
            (PRINTLN
              age
            )
        """.trimIndent()
        assertEquals(expected, printAst("let name: string = \"Alice\"; let age: number = 25; println(name); println(age);"))
    }

    // Tests para casos especiales
    @Test
    fun `should print variable with unary operation`() {
        val expected = """
            (VAR_DECL
              NAME x
              TYPE number
              INIT
                (SUB
                  5
                )
            )
        """.trimIndent()
        assertEquals(expected, printAst("let x: number = -5;"))
    }

    @Test
    fun `should print unary operation on variable`() {
        val expected = """
            (SUB
              x
            )
        """.trimIndent()
        assertEquals(expected, printAst("-x;"))
    }

    @Test
    fun `should print nested variable operations`() {
        val expected = """
            (ADD
              (MUL
                x
                y
              )
              (DIV
                z
                w
              )
            )
        """.trimIndent()
        assertEquals(expected, printAst("x * y + z / w;"))
    }

    @Test
    fun `should print variables in parenthesized expressions`() {
        val expected = """
            (MUL
              (ADD
                x
                y
              )
              (SUB
                z
                w
              )
            )
        """.trimIndent()
        assertEquals(expected, printAst("(x + y) * (z - w);"))
    }


}
