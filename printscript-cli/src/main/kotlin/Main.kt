import interpreter.InterpreterRunner
import interpreter.PrintScriptInterpreter
import io.reader.env.MapEnvReader
import io.reader.input.ConsoleInputReader
import io.reader.input.FileInputReader
import io.reporter.ConsoleDiagnosticReporter
import io.writer.ConsoleOutputWriter
import lexer.PrintScriptLexer
import model.value.BooleanValue
import parser.PrintScriptParser
import validator.PrintScriptValidator

fun main() {
    val source = FileInputReader("C:\\Users\\julir\\faculty\\ingsis\\printscript\\printscript-cli\\main.ps")
    val input = ConsoleInputReader()
    val output = ConsoleOutputWriter()
    val env = MapEnvReader(
        mapOf(
            "BOOLEAN" to BooleanValue(true),
        ),
    )
    val reporter = ConsoleDiagnosticReporter()

    val version = "1.1"
    val lexer = PrintScriptLexer()
    val parser = PrintScriptParser()
    val validator = PrintScriptValidator()
    val interpreter = PrintScriptInterpreter()
    val runner = InterpreterRunner(lexer, parser, validator, interpreter)
    runner.run(version, source, input, output, env, reporter)
    return
}
