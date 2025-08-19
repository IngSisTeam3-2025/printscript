
enum class TokenType {

    // Reservadas
    IF, ELSE, WHILE, RETURN, CLASS, INT, FLOAT,

    // Identificadores
    ID, NUMBER, STRING, LET, PRINTLN,

    // Operadores y símbolos
    ADD, SUB, MUL, DIV, COLON,
    ASSIGN, SEMI, LPAREN, RPAREN, DOT,

    // Fin de archivo
    EOF,

    // Especiales
    END, BEGIN
}

