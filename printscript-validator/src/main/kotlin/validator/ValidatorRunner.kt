package validator

import Lexer
import Parser
import Validator
import io.reader.input.InputReader
import io.reporter.DiagnosticReporter
import model.node.Node
import model.token.Token
import type.outcome.Outcome

class ValidatorRunner(
    private val lexer: Lexer,
    private val parser: Parser,
    private val validator: Validator,
) {
    fun run(version: String, source: InputReader, reporter: DiagnosticReporter) {
        var stop = false

        val chars = source.read()
        val tokens = lexer.lex(version, chars)
            .takeWhile { !stop }
            .onEach {
                if (it is Outcome.Error) {
                    reporter.report(it.error)
                    stop = true
                }
            }
            .filterIsInstance<Outcome.Ok<Token>>()
            .map { it.value }

        val nodes = parser.parse(version, tokens)
            .takeWhile { !stop }
            .onEach {
                if (it is Outcome.Error) {
                    reporter.report(it.error)
                    stop = true
                }
            }
            .filterIsInstance<Outcome.Ok<Node>>()
            .map { it.value }

        for (outcome in validator.validate(version, nodes)) {
            if (outcome is Outcome.Error) {
                reporter.report(outcome.error)
            }
        }
    }
}
