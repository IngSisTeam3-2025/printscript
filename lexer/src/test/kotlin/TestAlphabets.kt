import rule.TokenRule
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
        TokenRule(JsonTokenType.WHITESPACE, Pattern.compile("\\s+"), 1, ignore = true),
        TokenRule(JsonTokenType.LBRACE, Pattern.compile("\\{"), 2),
        TokenRule(JsonTokenType.RBRACE, Pattern.compile("}"), 2),
        TokenRule(JsonTokenType.LBRACKET, Pattern.compile("\\["), 2),
        TokenRule(JsonTokenType.RBRACKET, Pattern.compile("]"), 2),
        TokenRule(JsonTokenType.COLON, Pattern.compile(":"), 2),
        TokenRule(JsonTokenType.COMMA, Pattern.compile(","), 2),
        TokenRule(JsonTokenType.TRUE, Pattern.compile("true"), 4),
        TokenRule(JsonTokenType.FALSE, Pattern.compile("false"), 4),
        TokenRule(JsonTokenType.NULL, Pattern.compile("null"), 4),
        TokenRule(JsonTokenType.STRING, Pattern.compile("\"(\\\\.|[^\"\\\\])*\""), 3),
        TokenRule(JsonTokenType.NUMBER, Pattern.compile("-?\\d+(\\.\\d+)?([eE][+-]?\\d+)?"), 3),
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
        TokenRule(PythonTokenType.WHITESPACE, Pattern.compile("\\s+"), 1, ignore = true),
        TokenRule(PythonTokenType.DEF, Pattern.compile("def"), 4),
        TokenRule(PythonTokenType.RETURN, Pattern.compile("return"), 4),
        TokenRule(PythonTokenType.STRING, Pattern.compile("\"\"\".*?\"\"\"|'''.*?'''|\".*?\"|'.*?'"), 4),
        TokenRule(PythonTokenType.NUMBER, Pattern.compile("\\d+(\\.\\d+)?"), 3),
        TokenRule(PythonTokenType.OPERATOR, Pattern.compile("[+\\-*/=<>,]"), 2),
        TokenRule(PythonTokenType.COLON, Pattern.compile(":"), 2),
        TokenRule(PythonTokenType.LPAREN, Pattern.compile("\\("), 2),
        TokenRule(PythonTokenType.RPAREN, Pattern.compile("\\)"), 2),
        TokenRule(PythonTokenType.IDENTIFIER, Pattern.compile("[a-zA-Z_][a-zA-Z0-9_]*"), 3),
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
        TokenRule(JsTokenType.WHITESPACE, Pattern.compile("\\s+"), 1, ignore = true),
        TokenRule(JsTokenType.VAR, Pattern.compile("var"), 5),
        TokenRule(JsTokenType.LET, Pattern.compile("let"), 5),
        TokenRule(JsTokenType.CONST, Pattern.compile("const"), 5),
        TokenRule(JsTokenType.STRING, Pattern.compile("\".*?\"|'.*?'"), 4),
        TokenRule(JsTokenType.NUMBER, Pattern.compile("\\d+(\\.\\d+)?"), 4),
        TokenRule(JsTokenType.IDENTIFIER, Pattern.compile("[a-zA-Z_$][a-zA-Z0-9_$]*"), 3),
        TokenRule(JsTokenType.OPERATOR, Pattern.compile("[+\\-*/]"), 2),
        TokenRule(JsTokenType.ASSIGN, Pattern.compile("="), 2),
        TokenRule(JsTokenType.SEMICOLON, Pattern.compile(";"), 2),
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
        TokenRule(ArithmeticTokenType.WHITESPACE, Pattern.compile("\\s+"), 1, ignore = true),
        TokenRule(ArithmeticTokenType.NUMBER, Pattern.compile("\\d+(\\.\\d+)?"), 3),
        TokenRule(ArithmeticTokenType.OPERATOR, Pattern.compile("[+\\-*/]"), 2),
        TokenRule(ArithmeticTokenType.LPAREN, Pattern.compile("\\("), 2),
        TokenRule(ArithmeticTokenType.RPAREN, Pattern.compile("\\)"), 2),
    )
}
