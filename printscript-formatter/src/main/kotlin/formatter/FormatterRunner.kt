package formatter

import Formatter
import Lexer
import Parser
import io.reader.config.ConfigReader
import io.reader.input.InputReader
import io.reporter.DiagnosticReporter
import io.writer.OutputWriter
import type.outcome.onError

class FormatterRunner(
    private val lexer: Lexer,
    private val parser: Parser,
    private val formatter: Formatter,
) {
    fun run(
        version: String,
        source: InputReader,
        target: OutputWriter,
        config: ConfigReader,
        reporter: DiagnosticReporter,
    ) {
        val chars = source.read()
        val rules = config.read()
        val tokens = lexer.lex(version, chars).onError(reporter::report)
        val nodes = parser.parse(version, tokens).onError(reporter::report)
        val docs = formatter.format(version, nodes, rules).onError(reporter::report)
        val formatted = docs.map { it.format() }
        target.write(formatted)
    }
}
