import source.SourcePosition
import source.SourceReadResult
import source.SourceReader
import token.Token

class Tokenizer(
    private val reader: SourceReader,
    private val symbolMatcher: SymbolMatcher,
) : Lexer {

    override fun lex(): LexerResult {
        val startPosition = reader.position()
        val buffer = StringBuilder()
        var longestMatch: Token? = null
        var lastSuccessLength = 0
        var lookahead = 0

        while (!reader.isEOF()) {
            when (val peekResult = readNextChar(lookahead)) {
                is SourceReadResult.EOF -> break
                is SourceReadResult.Failure -> return ioError(peekResult)
                is SourceReadResult.Success -> {
                    buffer.append(peekResult.char)
                    val lexeme = buffer.toString()

                    when (val matchResult = matchLexeme(lexeme)) {
                        is MatchResult.None -> return handleNoMatch(
                            buffer,
                            startPosition,
                            longestMatch,
                            lastSuccessLength,
                        )
                        is MatchResult.Skip -> return handleSkip(buffer)
                        is MatchResult.Partial -> {}
                        is MatchResult.Full -> {
                            longestMatch = createToken(matchResult, buffer, startPosition)
                            lastSuccessLength = buffer.length
                        }
                    }
                }
            }
            lookahead++
        }

        if (longestMatch != null) {
            reader.advance(lastSuccessLength)
            return LexerResult.Success(longestMatch)
        }

        return LexerResult.EOF
    }

    private fun matchLexeme(lexeme: String) = symbolMatcher.match(lexeme)

    private fun readNextChar(lookahead: Int): SourceReadResult {
        return reader.peek(lookahead)
    }

    private fun ioError(peekResult: SourceReadResult.Failure): LexerResult.IOError {
        return LexerResult.IOError("Error reading source: ${peekResult.message}")
    }

    private fun createToken(
        matchResult: MatchResult.Full,
        buffer: StringBuilder,
        startPosition: SourcePosition,
    ): Token {
        return Token(matchResult.symbol.tokenType, buffer.toString(), startPosition)
    }

    private fun handleSkip(buffer: StringBuilder): LexerResult {
        reader.advance(buffer.length)
        return lex()
    }

    private fun handleNoMatch(
        buffer: StringBuilder,
        startPosition: SourcePosition,
        longestMatch: Token?,
        lastSuccessLength: Int,
    ): LexerResult {
        if (longestMatch != null) {
            reader.advance(lastSuccessLength)
            return LexerResult.Success(longestMatch)
        }

        reader.advance(1)
        return LexerResult.Error(
            "Lexical Error: " +
                "Unexpected character '${buffer.firstOrNull()}'",
            startPosition,
        )
    }
}
