import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import parser.Parser
import lexer.Tokenizer

class AstPrinterTest {


    // Metodo para eliminar espacios en blanco del texto hasta que este configurado el parser
    private fun removeSpaces(text: String): String {
        return text.replace(" ", "")
    }

    private fun printAst(src: String): String {
        val cleanSrc = removeSpaces(src)
        val tokenizer = Tokenizer(cleanSrc)
        val parser = Parser(tokenizer)
        val ast = parser.parseProgram()
        return AstPrinter().print(ast)
    }

    @Test
    fun `should print single number without extra wrapping`() {
        val expected = """
            10
        """.trimIndent()
        assertEquals(expected, printAst("10"))
    }

    @Test
    fun `should print simple addition as parenthesized PLUS with two indented numbers`() {
        val expected = """
            (ADD
              3
              5
            )
        """.trimIndent()
        assertEquals(expected, printAst("3 + 5"))
    }

    @Test
    fun `should print unary minus as parenthesized MINUS with one indented expr`() {
        val expected = """
            (SUB
              5
            )
        """.trimIndent()
        assertEquals(expected, printAst("-5"))
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
        assertEquals(expected, printAst("3 + 5 * 2"))
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
        assertEquals(expected, printAst("(3 + 5) * 2"))
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
        assertEquals(expected, printAst("7 + 3 * (10 / (12 / (3 + 1) - 1))"))
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
        val withSpaces = " (  (2 + 3) * 4 ) - ( 10 / 5 ) "
        val noSpaces  = "((2+3)*4)-(10/5)"
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
        assertEquals(expected, printAst("-(3 + 2)"))
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
        assertEquals(expected, printAst("15 * 10 + 10 / 5 - 2 + 5 / 5"))
    }
}
