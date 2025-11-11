package interpreter

import Interpreter
import Lexer
import Parser
import Validator
import error.ErrorFlag
import error.onError
import io.reader.env.EnvReader
import io.reader.input.InputReader
import io.reporter.DiagnosticReporter
import io.writer.OutputWriter

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
        val flag = ErrorFlag()

        val chars = source.read()
        val tokens = lexer.lex(version, chars)
            .onError(reporter, flag)
        val nodes = parser.parse(version, tokens)
            .takeWhile { !flag.hasError }
            .onError(reporter, flag)
        val validations = validator.validate(version, nodes)
            .takeWhile { !flag.hasError }
            .onError(reporter, flag)

        if (!flag.hasError) {
            interpreter.interpret(version, validations, input, output, env)
                .forEach { reporter.report(it) }
        }
    }
}
