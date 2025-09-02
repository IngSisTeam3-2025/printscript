package lexer

import matcher.MatchResult
import matcher.TokenRuleMatcher
import source.SourcePosition
import source.SourceReadResult
import source.ISourceReader
import token.Token

class Lexer(
    private val reader: ISourceReader,
    private val tokenRuleMatcher: TokenRuleMatcher,
) : ILexer {

    override fun lex(): LexResult {
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
            return LexResult.Success(longestMatch)
        }

        return LexResult.EOF
    }

    private fun matchLexeme(lexeme: String) = tokenRuleMatcher.match(lexeme)

    private fun readNextChar(lookahead: Int): SourceReadResult {
        return reader.peek(lookahead)
    }

    private fun ioError(peekResult: SourceReadResult.Failure): LexResult.IOError {
        return LexResult.IOError("Error reading source: ${peekResult.message}")
    }

    private fun createToken(
        matchResult: MatchResult.Full,
        buffer: StringBuilder,
        startPosition: SourcePosition,
    ): Token {
        return Token(matchResult.tokenRule.tokenType, buffer.toString(), startPosition)
    }

    private fun handleSkip(buffer: StringBuilder): LexResult {
        reader.advance(buffer.length)
        return lex()
    }

    private fun handleNoMatch(
        buffer: StringBuilder,
        startPosition: SourcePosition,
        longestMatch: Token?,
        lastSuccessLength: Int,
    ): LexResult {
        if (longestMatch != null) {
            reader.advance(lastSuccessLength)
            return LexResult.Success(longestMatch)
        }

        reader.advance(1)
        return LexResult.Error(
            "Lexical Error: " +
                    "Unexpected character '${buffer.firstOrNull()}'",
            startPosition,
        )
    }
}
