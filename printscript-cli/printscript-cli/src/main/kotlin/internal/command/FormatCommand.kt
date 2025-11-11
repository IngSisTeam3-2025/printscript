package internal.command

import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.parameters.arguments.argument
import formatter.FormatterRunner
import formatter.PrintScriptFormatter
import internal.util.JsonFileConfigReader
import internal.util.parser.JsonRuleParser
import internal.util.transformer.BooleanTransformer
import internal.util.transformer.IntegerTransformer
import internal.util.transformer.StringTransformer
import io.reader.input.FileInputReader
import io.reporter.ConsoleDiagnosticReporter
import io.writer.FileOutputWriter
import lexer.PrintScriptLexer
import parser.PrintScriptParser

class FormatCommand : BaseCommand(name = "format") {
    private val source by argument(help = "The source file")
    private val version by argument(help = "The PrintScript language version")
    private val target by argument(help = "The output file")
    private val config by argument(help = "The configuration file")

    override fun run() {
        showProgress("Processing file $source...\n")
        val source = FileInputReader(source)
        val target = FileOutputWriter(target)
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
        val formatter = PrintScriptFormatter()

        val runner = FormatterRunner(lexer, parser, formatter)
        runner.run(version, source, target, config, reporter)
        showProgress("Process finished.")
    }

    override fun help(context: Context) = "Runs formatting on file <source> to file <target>"
}
