package lexer.matcher.trivia

import lexer.matcher.Matcher
import trivia.TriviaType

class WhitespaceMatcher : Matcher<TriviaMatch> {
    override fun match(input: CharSequence): TriviaMatch? {
        if (input.isEmpty() || !input[0].isWhitespace()) return null
        var len = 1
        while (len < input.length && input[len].isWhitespace()) len++
        val text = input.subSequence(0, len).toString()
        return object : TriviaMatch {
            override val kind = TriviaType.WHITESPACE
            override val text = text
            override val length = len
        }
    }
}
