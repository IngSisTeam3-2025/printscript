package lexer

import Token
import token.TokenType
import java.io.Reader

class Tokenizer(source: Source) {
    private val reader: Reader = source.getReader()
    private var column: Int = 0
    private var currentChar: Int = reader.read()

    private val RESERVED_KEYWORDS = mapOf(
        "let" to TokenType.LET,
        "number" to TokenType.INT,
        "string" to TokenType.STRING,
        "println" to TokenType.PRINTLN
    )

    private fun isIdStart(c: Char) = c.isLetter() || c == '_'
    private fun isIdPart(c: Char) = c.isLetterOrDigit() || c == '_'

    fun column(): Int = column

    private fun advance() {
        column++
        currentChar = reader.read()
    }

    fun peek(): Char? {
        reader.mark(1)
        val nextChar = reader.read()
        reader.reset()
        return if (nextChar == -1) null else nextChar.toChar()
    }

    private fun _id(): Token {
        var res = ""
        while (currentChar != -1 && isIdPart(currentChar.toChar())) {
            res += currentChar.toChar()
            advance()
        }
        val lexeme = res
        val type = RESERVED_KEYWORDS[lexeme] ?: TokenType.ID
        return Token(type, lexeme)
    }

    private fun string(): Token {
        val quote = currentChar.toChar()
        advance()
        var res = ""
        while (currentChar != -1 && currentChar.toChar() != quote) {
            res += currentChar.toChar()
            advance()
        }
        if (currentChar != -1 && currentChar.toChar() == quote) {
            advance()
        } else {
            throw Error("String sin cerrar en columna $column")
        }
        return Token(TokenType.STRING, res)
    }

    private fun number(): String {
        var res = ""
        while (currentChar != -1 && currentChar.toChar().isDigit()) {
            res += currentChar.toChar()
            advance()
        }
        if (currentChar != -1 && currentChar.toChar() == '.') {
            res += currentChar.toChar()
            advance()
            while (currentChar != -1 && currentChar.toChar().isDigit()) {
                res += currentChar.toChar()
                advance()
            }
        }
        return res
    }

    fun getNextToken(): Token {
        while (currentChar != -1) {
            val ch = currentChar.toChar()

            if (ch.isWhitespace()) {
                advance()
                return Token(TokenType.WHITESPACE, ch.toString())
            }

            if (ch.isDigit()) {
                return Token(TokenType.INT, number())
            }

            when (ch) {
                '=' -> {
                    advance()
                    return Token(TokenType.ASSIGN, "=")
                }
                ';' -> {
                    advance()
                    return Token(TokenType.SEMI, ";")
                }
                ':' -> {
                    advance()
                    return Token(TokenType.COLON, ":")
                }
                '.' -> {
                    advance()
                    return Token(TokenType.DOT, ".")
                }
                '+' -> {
                    advance()
                    return Token(TokenType.ADD, "+")
                }
                '-' -> {
                    advance()
                    return Token(TokenType.SUB, "-")
                }
                '/' -> {
                    advance()
                    return Token(TokenType.DIV, "/")
                }
                '*' -> {
                    advance()
                    return Token(TokenType.MUL, "*")
                }
                '(' -> {
                    advance()
                    return Token(TokenType.LPAREN, "(")
                }
                ')' -> {
                    advance()
                    return Token(TokenType.RPAREN, ")")
                }
                '"', '\'' -> {
                    return string()
                }
                else -> {
                    if (isIdStart(ch)) {
                        return _id()
                    }
                    error()
                }
            }
        }
        return Token(TokenType.EOF, "")
    }

    private fun error(): Nothing {
        throw Error("Col: $column -> Lexical error:: Invalid character: '${currentChar.toChar()}'")
    }

    fun close() {
        reader.close()
    }
}