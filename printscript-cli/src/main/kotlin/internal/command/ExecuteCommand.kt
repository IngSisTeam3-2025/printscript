package internal.command

import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.parameters.arguments.argument
import internal.SystemEnvReader
import interpreter.InterpreterRunner
import interpreter.PrintScriptInterpreter
import io.reader.input.ConsoleInputReader
import io.reader.input.FileInputReader
import io.reporter.ConsoleDiagnosticReporter
import io.writer.ConsoleOutputWriter
import lexer.PrintScriptLexer
import model.value.transformer.BooleanValueTransformer
import model.value.transformer.IntegerValueTransformer
import model.value.transformer.StringValueTransformer
import parser.PrintScriptParser
import validator.PrintScriptValidator

class ExecuteCommand : BaseCommand(name = "execute") {
    private val source by argument(help = "The source file")
    private val version by argument(help = "The PrintScript language version")

    override fun run() {
        showProgress("Processing file $source...\n")
        val source = FileInputReader(source)
        val input = ConsoleInputReader()
        val output = ConsoleOutputWriter()
        val env = SystemEnvReader(
            listOf(
                BooleanValueTransformer,
                IntegerValueTransformer,
                StringValueTransformer,
            ),
        )
        val reporter = ConsoleDiagnosticReporter()

        val lexer = PrintScriptLexer()
        val parser = PrintScriptParser()
        val validator = PrintScriptValidator()
        val interpreter = PrintScriptInterpreter()

        val runner = InterpreterRunner(lexer, parser, validator, interpreter)
        runner.run(version, source, input, output, env, reporter)
        showProgress("Process finished.")
    }

    override fun help(context: Context) = "Executes code from file <source>"
}
