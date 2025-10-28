package linter

import Lexer
import Linter
import Parser
import Validator
import io.reader.config.ConfigReader
import io.reader.input.InputReader
import io.reporter.DiagnosticReporter
import type.outcome.onError

class LinterRunner(
    private val lexer: Lexer,
    private val parser: Parser,
    private val validator: Validator,
    private val linter: Linter,
) {

    fun run(
        version: String,
        source: InputReader,
        config: ConfigReader,
        reporter: DiagnosticReporter,
    ) {
        val chars = source.read()
        val rules = config.read()
        val tokens = lexer.lex(version, chars).onError(reporter::report)
        val nodes = parser.parse(version, tokens).onError(reporter::report)
        val validated = validator.validate(version, nodes).onError(reporter::report)
        linter.lint(version, validated, rules).forEach { reporter.report(it) }
    }
}
