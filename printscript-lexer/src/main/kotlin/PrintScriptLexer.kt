import internal.buffer.LexemeBuffer
import internal.collector.TriviaCollector
import internal.model.error.ConfigError
import internal.model.error.LexError
import internal.model.scan.Scan
import internal.spanner.Spanner
import internal.scanner.TokenScanner
import internal.table.TerminalTable
import internal.table.TerminalTableRegistry
import model.diagnostic.Diagnostic
import model.token.Token
import model.trivia.Trivia
import util.option.Option
import util.outcome.Outcome

class PrintScriptLexer(private val ver: String) : Lexer {

    override fun lex(chars: Sequence<Char>): Sequence<Outcome<Token, Diagnostic>> = sequence {
        val result = getTerminalTable(ver)
        when (result) {
            is Option.Some -> {
                val table = result.value
                yieldAll(lexCharacters(chars, table))
            }
            is Option.None -> yield(Outcome.Err(buildConfigError()))
        }
    }

    private fun getTerminalTable(ver: String): Option<TerminalTable> {
        return TerminalTableRegistry.get(ver)
    }

    private fun buildConfigError(): Diagnostic {
        return ConfigError("No such version '$ver'")
    }

    private fun lexCharacters(chars: Sequence<Char>, table: TerminalTable): Sequence<Outcome<Token, Diagnostic>> = sequence {
        val spanner = Spanner()
        val buffer = LexemeBuffer(chars)
        val scanner = TokenScanner(table.tokenTerminals)
        val collector = TriviaCollector(table.triviaTerminals)

        while (buffer.hasNext()) {
            val leading = collector.collect(buffer, spanner)

            when (val scan = scanner.scan(buffer, spanner)) {
                is Scan.Empty -> break
                is Scan.Error -> {
                    val error = buildLexError(buffer, spanner)
                    buffer.consume(1)
                    yield(Outcome.Err(error))
                }
                is Scan.Ok -> {
                    val trailing = collector.collect(buffer, spanner)
                    yield(Outcome.Ok(buildToken(scan, leading, trailing)))
                }
            }
        }
    }

    private fun buildToken(scan: Scan.Ok, leading: Collection<Trivia>, trailing: Collection<Trivia>): Token {
        return Token(scan.type, scan.lexeme, scan.span, leading, trailing)
    }

    private fun buildLexError(buffer: LexemeBuffer, spanner: Spanner): Diagnostic {
        val char = buffer.peek(1)[0]
        val span = spanner.span(char.toString())
        val message = "Unexpected char: '$char'"
        return LexError(message, span)
    }
}

