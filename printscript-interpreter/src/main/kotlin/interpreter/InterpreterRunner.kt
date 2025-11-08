package interpreter

import Interpreter
import Lexer
import Parser
import Validator
import error.ErrorFlag
import error.onError
import error.onErrorCollectAll
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
        val hasError = validate(version, source, flag, reporter)
        if (hasError) return
        execute(version, source, input, output, env, flag, reporter)
    }

    private fun validate(
        version: String,
        source: InputReader,
        flag: ErrorFlag,
        reporter: DiagnosticReporter,
    ): Boolean {
        val chars = source.read()

        val tokens = lexer.lex(version, chars)
            .onError(reporter, flag)

        val nodes = parser.parse(version, tokens)
            .takeWhile { !flag.hasError }
            .onError(reporter, flag)

        validator.validate(version, nodes)
            .takeWhile { !flag.hasError }
            .onErrorCollectAll(reporter, flag)
            .forEach { _ -> }

        return flag.hasError
    }

    private fun execute(
        version: String,
        source: InputReader,
        input: InputReader,
        output: OutputWriter,
        env: EnvReader,
        flag: ErrorFlag,
        reporter: DiagnosticReporter,
    ) {
        val chars = source.read()

        val tokens = lexer.lex(version, chars)
            .onError(reporter, flag)
            .takeWhile { !flag.hasError }

        val nodes = parser.parse(version, tokens)
            .onError(reporter, flag)
            .takeWhile { !flag.hasError }

        interpreter.interpret(version, nodes, input, output, env)
            .forEach { reporter.report(it) }
    }
}
