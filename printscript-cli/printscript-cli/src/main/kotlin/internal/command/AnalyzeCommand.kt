package internal.command

import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.parameters.arguments.argument
import internal.util.JsonFileConfigReader
import internal.util.parser.JsonRuleParser
import internal.util.transformer.BooleanTransformer
import internal.util.transformer.IntegerTransformer
import internal.util.transformer.StringTransformer
import io.reader.input.FileInputReader
import io.reporter.ConsoleDiagnosticReporter
import lexer.PrintScriptLexer
import linter.LinterRunner
import linter.PrintScriptLinter
import parser.PrintScriptParser
import validator.PrintScriptValidator

class AnalyzeCommand : BaseCommand(name = "analyze") {
    private val source by argument(help = "The source file")
    private val version by argument(help = "The PrintScript language version")
    private val config by argument(help = "The configuration file")

    override fun run() {
        showProgress("Processing file $source...\n")
        val reader = FileInputReader(source)
        val reporter = ConsoleDiagnosticReporter()
        val rules = JsonRuleParser(
            listOf(
                IntegerTransformer(),
                StringTransformer(),
                BooleanTransformer(),
            ),
        )
        val config = JsonFileConfigReader(config, rules)

        val lexer = PrintScriptLexer()
        val parser = PrintScriptParser()
        val validator = PrintScriptValidator()
        val linter = PrintScriptLinter()

        val runner = LinterRunner(lexer, parser, validator, linter)
        runner.run(version, reader, config, reporter)
        showProgress("Process finished.")
    }

    override fun help(context: Context) = "Runs static code analysis on file <source>"
}
