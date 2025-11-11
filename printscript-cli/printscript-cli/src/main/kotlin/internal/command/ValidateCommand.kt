package internal.command

import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.parameters.arguments.argument
import io.reader.input.FileInputReader
import io.reporter.ConsoleDiagnosticReporter
import lexer.PrintScriptLexer
import parser.PrintScriptParser
import validator.PrintScriptValidator
import validator.ValidatorRunner

class ValidateCommand : BaseCommand(name = "validate") {
    private val source by argument(help = "The source file")
    private val version by argument(help = "The PrintScript language version")

    override fun run() {
        showProgress("Processing file $source...\n")
        val source = FileInputReader(source)
        val reporter = ConsoleDiagnosticReporter()

        val lexer = PrintScriptLexer()
        val parser = PrintScriptParser()
        val validator = PrintScriptValidator()

        val runner = ValidatorRunner(lexer, parser, validator)
        runner.run(version, source, reporter)
        showProgress("Process finished.")
    }

    override fun help(context: Context) = "Runs semantic analysis on file <source>"
}
