package lexer

import Lexer
import lexer.internal.buffer.LexemeBuffer
import lexer.internal.collector.TriviaCollector
import lexer.internal.model.category.Lexical
import lexer.internal.model.error.ConfigurationError
import lexer.internal.model.error.LexError
import lexer.internal.model.scan.TokenScan
import lexer.internal.scanner.TokenScanner
import lexer.internal.spanner.Spanner
import lexer.internal.table.TerminalTable
import lexer.internal.table.TerminalTableRegistry
import model.diagnostic.Diagnostic
import model.token.Token
import model.trivia.Trivia
import type.option.Option
import type.outcome.Outcome

class PrintScriptLexer : Lexer {

    override fun lex(
        version: String,
        chars: Sequence<Char>,
    ): Sequence<Outcome<Token, Diagnostic>> {
        return sequence {
            when (val table = getTerminalTable(version)) {
                is Option.Some -> {
                    for (token in lexCharacters(chars, table.value)) {
                        yield(token)
                    }
                }
                is Option.None -> {
                    yield(Outcome.Error(buildConfigurationError(version)))
                    return@sequence
                }
            }
        }
    }

    private fun getTerminalTable(version: String): Option<TerminalTable> {
        return TerminalTableRegistry.get(version)
    }

    private fun buildConfigurationError(version: String): Diagnostic {
        return ConfigurationError("Unsupported language version '$version'")
    }

    private fun lexCharacters(
        chars: Sequence<Char>,
        table: TerminalTable,
    ): Sequence<Outcome<Token, Diagnostic>> {
        return sequence {
            val spanner = Spanner()
            val buffer = LexemeBuffer(chars)
            val scanner = TokenScanner(table.tokenTerminals)
            val collector = TriviaCollector(table.triviaTerminals)

            while (buffer.hasNext()) {
                val leading = collector.collect(buffer, spanner)

                when (val scan = scanner.scan(buffer, spanner)) {
                    is TokenScan.Empty -> {
                        return@sequence
                    }
                    is TokenScan.Error -> {
                        yield(Outcome.Error(buildLexError(buffer, spanner)))
                        return@sequence
                    }
                    is TokenScan.Ok -> {
                        val trailing = collector.collect(buffer, spanner)
                        yield(Outcome.Ok(buildToken(scan, leading, trailing)))
                        buffer.consume(scan.lexeme.length)
                    }
                }
            }
        }
    }

    private fun buildToken(
        scan: TokenScan.Ok,
        leading: Collection<Trivia>,
        trailing: Collection<Trivia>,
    ): Token {
        return Token(scan.type, scan.lexeme, scan.span, leading, trailing)
    }

    private fun buildLexError(
        buffer: LexemeBuffer,
        spanner: Spanner,
    ): Diagnostic {
        val char = buffer.peek().first()
        val span = spanner.span(char.toString())
        val message = "Invalid character '$char'"
        return LexError(message, Lexical, span)
    }
}
