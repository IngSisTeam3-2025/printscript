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
import model.diagnostic.Diagnostic
import model.rule.Rule
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
        val flag = ErrorFlag()

        val rulesOutcome: Outcome<Collection<Rule>, Diagnostic> = config.read()
        val rules = when (rulesOutcome) {
            is Outcome.Ok -> rulesOutcome.value
            is Outcome.Error -> {
                reporter.report(rulesOutcome.error)
                return
            }
        }

        val chars = source.read()

        val tokens = lexer.lex(version, chars)
            .onError(reporter, flag)
        val nodes = parser.parse(version, tokens)
            .takeWhile { !flag.hasError }
            .onError(reporter, flag)
            .toList()

        if (!flag.hasError) {
            val docs = formatter.format(version, nodes.asSequence(), rules)

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
}
