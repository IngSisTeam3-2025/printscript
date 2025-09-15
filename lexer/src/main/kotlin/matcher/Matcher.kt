package lexer.matcher

import lexer.Lexer

class Matcher(private val lexers: List<Lexer>) {

    fun match(input: String): Match {
        val best = Match()
        for (lexer in lexers) {
            var m = lexer.lex(input)
            if (m.length > best.length || (m.length == best.length && m.type.priority > best.type.priority)) {
                best.length = m.length
                best.type = m.type
            }
        }
        return best
    }
}

