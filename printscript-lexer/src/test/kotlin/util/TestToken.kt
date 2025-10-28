package util

import model.token.Token

data class TestToken(val token: Token) {

    fun format(): List<String> {
        val lines = mutableListOf<String>()

        token.leading.forEach {
            lines.add("${it.span.format()} ${it.type} '${escapeLexeme(it.lexeme)}'")
        }

        val typeName = token.type
        lines.add("${token.span.format()} $typeName '${token.lexeme}'")

        token.trailing.forEach {
            lines.add("${it.span.format()} ${it.type} '${escapeLexeme(it.lexeme)}'")
        }

        return lines
    }

    private fun escapeLexeme(lexeme: String): String {
        return lexeme
            .replace("\n", "\\n")
            .replace("\r", "\\r")
            .replace("\t", "\\t")
    }
}
