package formatter

import Formatter
import Lexer
import Parser
import error.ErrorFlag
import error.onError
import io.reader.config.ConfigReader
import io.reader.input.InputReader
import io.reporter.DiagnosticReporter
import io.writer.OutputWriter
import type.outcome.Outcome

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
        val rulesOutcome = config.read()
        val rules = when (rulesOutcome) {
            is Outcome.Ok -> rulesOutcome.value
            is Outcome.Error -> {
                reporter.report(rulesOutcome.error)
                return
            }
        }

        val flag = ErrorFlag()

        val chars = source.read()
        val tokens = lexer.lex(version, chars)
            .onError(reporter, flag)
        val nodes = parser.parse(version, tokens)
            .takeWhile { !flag.hasError }
            .onError(reporter, flag)

        val docs = formatter.format(version, nodes, rules)

        for (doc in docs) {
            when (doc) {
                is Outcome.Ok -> {
                    val formatted = doc.value.format()
                    target.write(sequenceOf(formatted))
                }

                is Outcome.Error -> {
                    reporter.report(doc.error)
                    return
                }
            }
        }
    }
}
