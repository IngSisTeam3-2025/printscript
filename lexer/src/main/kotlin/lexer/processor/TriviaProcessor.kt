package lexer.processor

import lexer.matcher.Matcher
import lexer.matcher.trivia.TriviaMatch

class TriviaProcessor(
    private val source: SourceProcessor,
    private val matchers: List<Matcher<TriviaMatch>>,
    private val maxLookahead: Int = 64,
) {
    fun captureTrivia(): String {
        var trivia = ""
        while (!source.eof()) {
            val slice = source.peekSlice(maxLookahead)
            val matches = matchers.mapNotNull { it.match(slice) }
            val best = matches.maxByOrNull { it.length } ?: break
            trivia += best.text
            source.advance(best.length)
        }
        return trivia
    }
}
