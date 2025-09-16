package token

enum class TokenType(val priority: Int = 0) {

    // Keywords
    LET(priority = 100),
    PRINT_LN(priority = 100),
    IF(priority = 100),
    ELSE(priority = 100),
    READ_INPUT(priority = 100),
    READ_ENV(priority = 100),

    // Types
    NUMBER(priority = 100),
    STRING(priority = 100),
    CONST(priority = 100),

    // Literals
    NUMBER_LITERAL,
    STRING_LITERAL,
    BOOLEAN_LITERAL(priority = 100),

    // Operators
    PLUS,
    MINUS,
    MULTIPLY,
    DIVIDE,
    ASSIGN,
    COLON,

    // Punctuation
    SEMICOLON,
    LPAREN,
    RPAREN,
    COMMA,
    LBRACE,
    RBRACE,

    // Identifiers
    IDENTIFIER,
}
