//package lexer.tests
//
//import LexicalAnalyzer
//import matcher.matchers.MatcherPrimitives.literal
//import matcher.matchers.MatcherDefinitions.float
//import matcher.matchers.MatcherDefinitions.identifier
//import matcher.matchers.MatcherDefinitions.integer
//import matcher.matchers.MatcherDefinitions.string
//import matcher.matchers.MatcherDefinitions.whitespace
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.Assertions.*
//import token.TokenDefinitions.BOOLEAN_FALSE
//import token.TokenDefinitions.BOOLEAN_TRUE
//import token.TokenDefinitions.BOOLEAN_TYPE
//import token.TokenDefinitions.COLON
//import token.TokenDefinitions.CONST
//import token.TokenDefinitions.DIVIDE
//import token.TokenDefinitions.ELSE
//import token.TokenDefinitions.EQUALS
//import token.TokenDefinitions.FLOAT
//import token.TokenDefinitions.IDENTIFIER
//import token.TokenDefinitions.IF
//import token.TokenDefinitions.INTEGER
//import token.TokenDefinitions.LBRACE
//import token.TokenDefinitions.LET
//import token.TokenDefinitions.LPAREN
//import token.TokenDefinitions.MINUS
//import token.TokenDefinitions.MULTIPLY
//import token.TokenDefinitions.NUMBER_TYPE
//import token.TokenDefinitions.PLUS
//import token.TokenDefinitions.PRINT_LN
//import token.TokenDefinitions.RBRACE
//import token.TokenDefinitions.READ_ENV
//import token.TokenDefinitions.READ_INPUT
//import token.TokenDefinitions.RPAREN
//import token.TokenDefinitions.SEMICOLON
//import token.TokenDefinitions.STRING
//import token.TokenDefinitions.STRING_TYPE
//import token.TokenDefinitions.WHITESPACE
//
//class LexerUnitTest {
//
//    private val plus = literal("+")
//    private val minus = literal("-")
//    private val mul = literal("*")
//    private val div = literal("/")
//    private val colon = literal(":")
//    private val semicolon = literal(";")
//    private val lparen = literal("(")
//    private val rparen = literal(")")
//    private val lbrace = literal("{")
//    private val rbrace = literal("}")
//
//    private val let = literal("let")
//    private val const = literal("const")
//    private val typeString = literal("string")
//    private val typeNumber = literal("number")
//    private val typeBoolean = literal("boolean")
//    private val equals = literal("=")
//    private val ifKw = literal("if")
//    private val elseKw = literal("else")
//    private val printlnKw = literal("println")
//    private val readInputKw = literal("readInput")
//    private val readEnvKw = literal("readEnv")
//
//    private val matchers = listOf(
//        whitespace to WHITESPACE,
//        let to LET,
//        const to CONST,
//        ifKw to IF,
//        elseKw to ELSE,
//        printlnKw to PRINT_LN,
//        readInputKw to READ_INPUT,
//        readEnvKw to READ_ENV,
//        typeString to STRING_TYPE,
//        typeNumber to NUMBER_TYPE,
//        typeBoolean to BOOLEAN_TYPE,
//        plus to PLUS,
//        minus to MINUS,
//        mul to MULTIPLY,
//        div to DIVIDE,
//        colon to COLON,
//        equals to EQUALS,
//        semicolon to SEMICOLON,
//        lparen to LPAREN,
//        rparen to RPAREN,
//        lbrace to LBRACE,
//        rbrace to RBRACE,
//        float to FLOAT,
//        integer to INTEGER,
//        string to STRING,
//        identifier to IDENTIFIER,
//        // Boolean literals
//        literal("true") to BOOLEAN_TRUE,
//        literal("false") to BOOLEAN_FALSE,
//    )
//
//    private fun tokenize(input: String): List<String> {
//        val lexer = LexicalAnalyzer(input.iterator(), matchers)
//        val tokens = mutableListOf<String>()
//        while (lexer.hasNext()) {
//            val result = lexer.next()
//            result.onSuccess { token ->
//                if (!token.type.ignore) {
//                    tokens.add("${token.lexeme} -> ${token.type.name}")
//                }
//            }.onFailure {
//                fail("Lexer error: ${it.message}")
//            }
//        }
//        return tokens
//    }
//
//    @Test
//    fun testVariableDeclarationWithTypeAndAssignment() {
//        val input = "let name: string = \"Joe\";"
//        val expected = listOf(
//            "let -> LET", "name -> IDENTIFIER", ": -> COLON", "string -> STRING_TYPE", "= -> EQUALS", "\"Joe\" -> STRING", "; -> SEMICOLON"
//        )
//        assertEquals(expected, tokenize(input))
//    }
//
//    @Test
//    fun testConstBooleanDeclaration() {
//        val input = "const isValid: boolean = true;"
//        val expected = listOf(
//            "const -> CONST", "isValid -> IDENTIFIER", ": -> COLON", "boolean -> BOOLEAN_TYPE", "= -> EQUALS", "true -> BOOLEAN_TRUE", "; -> SEMICOLON"
//        )
//        assertEquals(expected, tokenize(input))
//    }
//
//    @Test
//    fun testAssignmentExpression() {
//        val input = "x = 5 + 3.568 - 1;"
//        val expected = listOf(
//            "x -> IDENTIFIER", "= -> EQUALS", "5 -> INTEGER", "+ -> PLUS", "3.568 -> FLOAT", "- -> MINUS", "1 -> INTEGER", "; -> SEMICOLON"
//        )
//        assertEquals(expected, tokenize(input))
//    }
//
//    @Test
//    fun testArithmeticExpressionWithDecimals() {
//        val input = "let result: number = 12.5 * 3 / 2;"
//        val expected = listOf(
//            "let -> LET", "result -> IDENTIFIER", ": -> COLON", "number -> NUMBER_TYPE", "= -> EQUALS", "12.5 -> FLOAT", "* -> MULTIPLY", "3 -> INTEGER", "/ -> DIVIDE", "2 -> INTEGER", "; -> SEMICOLON"
//        )
//        assertEquals(expected, tokenize(input))
//    }
//
//    @Test
//    fun testStringConcatenationExpression() {
//        val input = "println(name + \" \" + lastName);"
//        val expected = listOf(
//            "println -> PRINT_LN", "( -> LPAREN", "name -> IDENTIFIER", "+ -> PLUS", "\" \" -> STRING", "+ -> PLUS", "lastName -> IDENTIFIER", ") -> RPAREN", "; -> SEMICOLON"
//        )
//        assertEquals(expected, tokenize(input))
//    }
//
//    @Test
//    fun testIfElseBlock() {
//        val input = """
//            if (isValid) {
//                println("Valid");
//            } else {
//                println("Invalid");
//            }
//        """.trimIndent()
//
//        val expected = listOf(
//            "if -> IF", "( -> LPAREN", "isValid -> IDENTIFIER", ") -> RPAREN", "{ -> LBRACE",
//            "println -> PRINT_LN", "( -> LPAREN", "\"Valid\" -> STRING", ") -> RPAREN", "; -> SEMICOLON",
//            "} -> RBRACE", "else -> ELSE", "{ -> LBRACE",
//            "println -> PRINT_LN", "( -> LPAREN", "\"Invalid\" -> STRING", ") -> RPAREN", "; -> SEMICOLON",
//            "} -> RBRACE"
//        )
//        assertEquals(expected, tokenize(input))
//    }
//
//    @Test
//    fun testReadInputCall() {
//        val input = "let inputVal: string = readInput(\"Enter your name:\");"
//        val expected = listOf(
//            "let -> LET", "inputVal -> IDENTIFIER", ": -> COLON", "string -> STRING_TYPE", "= -> EQUALS",
//            "readInput -> READ_INPUT", "( -> LPAREN", "\"Enter your name:\" -> STRING", ") -> RPAREN", "; -> SEMICOLON"
//        )
//        assertEquals(expected, tokenize(input))
//    }
//
//    @Test
//    fun testReadEnvCall() {
//        val input = "let envVal: number = readEnv(\"PATH\");"
//        val expected = listOf(
//            "let -> LET", "envVal -> IDENTIFIER", ": -> COLON", "number -> NUMBER_TYPE", "= -> EQUALS",
//            "readEnv -> READ_ENV", "( -> LPAREN", "\"PATH\" -> STRING", ") -> RPAREN", "; -> SEMICOLON"
//        )
//        assertEquals(expected, tokenize(input))
//    }
//}
