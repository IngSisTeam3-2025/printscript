import symbol.Symbol
import token.TokenType
import java.util.regex.Pattern

enum class JsonTokenType : TokenType {
    LBRACE,
    RBRACE,
    LBRACKET,
    RBRACKET,
    COLON,
    COMMA,
    STRING,
    NUMBER,
    TRUE,
    FALSE,
    NULL,
    WHITESPACE,
}

object JsonAlphabet {
    val alphabet = listOf(
        Symbol(JsonTokenType.WHITESPACE, Pattern.compile("\\s+"), 1, ignore = true),
        Symbol(JsonTokenType.LBRACE, Pattern.compile("\\{"), 2),
        Symbol(JsonTokenType.RBRACE, Pattern.compile("}"), 2),
        Symbol(JsonTokenType.LBRACKET, Pattern.compile("\\["), 2),
        Symbol(JsonTokenType.RBRACKET, Pattern.compile("]"), 2),
        Symbol(JsonTokenType.COLON, Pattern.compile(":"), 2),
        Symbol(JsonTokenType.COMMA, Pattern.compile(","), 2),
        Symbol(JsonTokenType.TRUE, Pattern.compile("true"), 4),
        Symbol(JsonTokenType.FALSE, Pattern.compile("false"), 4),
        Symbol(JsonTokenType.NULL, Pattern.compile("null"), 4),
        Symbol(JsonTokenType.STRING, Pattern.compile("\"(\\\\.|[^\"\\\\])*\""), 3),
        Symbol(JsonTokenType.NUMBER, Pattern.compile("-?\\d+(\\.\\d+)?([eE][+-]?\\d+)?"), 3),
    )
}

enum class PythonTokenType : TokenType {
    DEF,
    RETURN,
    IDENTIFIER,
    NUMBER,
    STRING,
    OPERATOR,
    COLON,
    LPAREN,
    RPAREN,
    WHITESPACE,
}

object PythonAlphabet {
    val alphabet = listOf(
        Symbol(PythonTokenType.WHITESPACE, Pattern.compile("\\s+"), 1, ignore = true),
        Symbol(PythonTokenType.DEF, Pattern.compile("def"), 4),
        Symbol(PythonTokenType.RETURN, Pattern.compile("return"), 4),
        Symbol(PythonTokenType.STRING, Pattern.compile("\"\"\".*?\"\"\"|'''.*?'''|\".*?\"|'.*?'"), 4),
        Symbol(PythonTokenType.NUMBER, Pattern.compile("\\d+(\\.\\d+)?"), 3),
        Symbol(PythonTokenType.OPERATOR, Pattern.compile("[+\\-*/=<>,]"), 2),
        Symbol(PythonTokenType.COLON, Pattern.compile(":"), 2),
        Symbol(PythonTokenType.LPAREN, Pattern.compile("\\("), 2),
        Symbol(PythonTokenType.RPAREN, Pattern.compile("\\)"), 2),
        Symbol(PythonTokenType.IDENTIFIER, Pattern.compile("[a-zA-Z_][a-zA-Z0-9_]*"), 3),
    )
}

enum class JsTokenType : TokenType {
    VAR,
    LET,
    CONST,
    IDENTIFIER,
    NUMBER,
    STRING,
    OPERATOR,
    ASSIGN,
    SEMICOLON,
    WHITESPACE,
}

object JavaScriptAlphabet {
    val alphabet = listOf(
        Symbol(JsTokenType.WHITESPACE, Pattern.compile("\\s+"), 1, ignore = true),
        Symbol(JsTokenType.VAR, Pattern.compile("var"), 5),
        Symbol(JsTokenType.LET, Pattern.compile("let"), 5),
        Symbol(JsTokenType.CONST, Pattern.compile("const"), 5),
        Symbol(JsTokenType.STRING, Pattern.compile("\".*?\"|'.*?'"), 4),
        Symbol(JsTokenType.NUMBER, Pattern.compile("\\d+(\\.\\d+)?"), 4),
        Symbol(JsTokenType.IDENTIFIER, Pattern.compile("[a-zA-Z_$][a-zA-Z0-9_$]*"), 3),
        Symbol(JsTokenType.OPERATOR, Pattern.compile("[+\\-*/]"), 2),
        Symbol(JsTokenType.ASSIGN, Pattern.compile("="), 2),
        Symbol(JsTokenType.SEMICOLON, Pattern.compile(";"), 2),
    )
}

enum class ArithmeticTokenType : TokenType {
    NUMBER,
    OPERATOR,
    LPAREN,
    RPAREN,
    WHITESPACE,
}

object ArithmeticAlphabet {
    val alphabet = listOf(
        Symbol(ArithmeticTokenType.WHITESPACE, Pattern.compile("\\s+"), 1, ignore = true),
        Symbol(ArithmeticTokenType.NUMBER, Pattern.compile("\\d+(\\.\\d+)?"), 3),
        Symbol(ArithmeticTokenType.OPERATOR, Pattern.compile("[+\\-*/]"), 2),
        Symbol(ArithmeticTokenType.LPAREN, Pattern.compile("\\("), 2),
        Symbol(ArithmeticTokenType.RPAREN, Pattern.compile("\\)"), 2),
    )
}
