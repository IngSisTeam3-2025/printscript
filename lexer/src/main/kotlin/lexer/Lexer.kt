package lexer

import lexer.processor.SourceProcessor
import lexer.processor.TokenProcessor
import lexer.processor.TriviaProcessor
import token.Token

class Lexer(
    charIterator: Iterator<Char>,
    recognizers: List<TokenRecognizer>,
) : Iterator<Result<Token>> {

    private val source = SourceProcessor(charIterator)
    private val triviaProcessor = TriviaProcessor(source)
    private val tokenProcessor = TokenProcessor(source, recognizers)

    override fun hasNext(): Boolean = !source.eof()

    override fun next(): Result<Token> {
        if (!hasNext()) throw NoSuchElementException()
        return lex()
    }

    private fun lex(): Result<Token> {
        val prefix = triviaProcessor.capturePrefix()

        if (source.eof()) {
            return Result.failure(Exception("Unexpected end of input"))
        }

        val match = tokenProcessor.matchToken() ?: run {
            val invalidChar = source.peek()
            source.advance(1)
            return Result.failure(Exception("Unknown token starting with '$invalidChar'"))
        }

        val tokenStart = source.position
        source.advance(match.length)
        val tokenEnd = source.position

        val suffix = triviaProcessor.captureSuffix()

        val token = Token(
            type = match.type,
            value = match.value,
            prefixTrivia = prefix,
            suffixTrivia = suffix,
            start = tokenStart,
            end = tokenEnd,
        )

        return Result.success(token)
    }
}
