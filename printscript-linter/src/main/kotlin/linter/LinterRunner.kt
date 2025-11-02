package linter

import Lexer
import Linter
import Parser
import Validator
import error.ErrorFlag
import error.onError
import error.onErrorCollectAll
import io.reader.config.ConfigReader
import io.reader.input.InputReader
import io.reporter.DiagnosticReporter
import model.diagnostic.Diagnostic
import model.rule.Rule
import type.outcome.Outcome

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
        val validations = validator.validate(version, nodes)
            .takeWhile { !flag.hasError }
            .onErrorCollectAll(reporter, flag)
            .toList()

        if (!flag.hasError) {
            val lints = linter.lint(version, validations.asSequence(), rules)
            for (lint in lints) {
                reporter.report(lint)
            }
        }
    }
}
