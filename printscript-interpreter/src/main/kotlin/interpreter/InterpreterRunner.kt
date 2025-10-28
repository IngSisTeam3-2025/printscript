package interpreter

import Interpreter
import Lexer
import Parser
import Validator
import io.reader.env.EnvReader
import io.reader.input.InputReader
import io.reporter.DiagnosticReporter
import io.writer.OutputWriter
import model.node.Node
import model.token.Token
import type.option.onSome
import type.outcome.Outcome

class InterpreterRunner(
    private val lexer: Lexer,
    private val parser: Parser,
    private val validator: Validator,
    private val interpreter: Interpreter,
) {
    fun run(
        version: String,
        source: InputReader,
        input: InputReader,
        output: OutputWriter,
        env: EnvReader,
        reporter: DiagnosticReporter,
    ) {
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

        val validated = validator.validate(version, nodes)
            .takeWhile { !stop }
            .onEach {
                if (it is Outcome.Error) {
                    reporter.report(it.error)
                    stop = true
                }
            }
            .filterIsInstance<Outcome.Ok<Node>>()
            .map { it.value }

        interpreter.interpret(version, validated, input, output, env).onSome(reporter::report)
    }
}
