
class Interpreter (private val source: String) {
    private var column : Int = 0
    private var currentToken : Token = Token(TokenType.EOF, "")

    private fun error() {
        throw Error("Error parsing source")
    }

    private fun getNextToken() : Token {

        if (this.column > source.length - 1) {
            return Token(TokenType.EOF, "")
        }

        val currentChar = source[column]

        if (currentChar.isDigit()) {
            column++
            return Token(TokenType.INTEGER, currentChar.toString())
        }

        if (currentChar == '+') {
            column++
            return Token(TokenType.PLUS, "+")
        }

        return currentToken
    }

    private fun parse(tokenType: TokenType) {
        if (tokenType == currentToken.type) {
            currentToken = getNextToken()
        }
        else error()
    }

    fun expr() : Int {
        currentToken = getNextToken()

        val left = currentToken
        parse(TokenType.INTEGER)

        val op = currentToken
        parse(TokenType.PLUS)

        val right = currentToken
        parse(TokenType.INTEGER)

        return left.literal.toInt() + right.literal.toInt()

    }


}
