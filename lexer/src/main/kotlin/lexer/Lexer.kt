package lexer

import lexer.matcher.Matcher
import lexer.matcher.token.TokenMatch
import lexer.matcher.trivia.TriviaMatch
import lexer.processor.SourceProcessor
import lexer.processor.TokenProcessor
import lexer.processor.TriviaProcessor
import token.Token

class Lexer(
    charIterator: Iterator<Char>,
    tokenMatchers: List<Matcher<TokenMatch>>,
    triviaMatchers: List<Matcher<TriviaMatch>>,
    private val maxLookahead: Int = 64,
) : Iterator<Result<Token>> {

    private val source = SourceProcessor(charIterator)
    private val triviaProcessor = TriviaProcessor(source, triviaMatchers, maxLookahead)
    private val tokenProcessor = TokenProcessor(source, tokenMatchers, maxLookahead)

    override fun hasNext(): Boolean = !source.eof()

    override fun next(): Result<Token> {
        if (!hasNext()) throw NoSuchElementException()

        val prefix = triviaProcessor.captureTrivia()
        val start = source.location()

        val token = tokenProcessor.matchToken()
            ?: run {
                val badChar = source.peekSlice(1).first()
                source.advance(1)
                return Result.failure(
                    Exception(
                        "Unexpected character " +
                            "'$badChar' at " +
                            "${source.location()}",
                    ),
                )
            }

        val end = source.location()
        val suffix = triviaProcessor.captureTrivia()

        return Result.success(Token(token.type, token.value, prefix, suffix, start, end))
    }
}
