import formatter.FormatterRunner
import formatter.PrintScriptFormatter
import internal.util.JsonFileConfigReader
import internal.util.parser.JsonRuleParser
import internal.util.transformer.BooleanTransformer
import internal.util.transformer.IntegerTransformer
import internal.util.transformer.StringTransformer
import io.reader.input.FileInputReader
import io.reporter.ConsoleDiagnosticReporter
import io.writer.ConsoleOutputWriter
import lexer.PrintScriptLexer
import parser.PrintScriptParser

fun main() {
    val source = FileInputReader("C:\\Users\\julir\\faculty\\ingsis\\printscript\\printscript-cli\\main.ps")
    val target = ConsoleOutputWriter()

    val config = JsonFileConfigReader(
        "C:\\Users\\julir\\faculty\\ingsis\\printscript\\printscript-cli\\config.json",
        JsonRuleParser(
            listOf(
                IntegerTransformer(),
                StringTransformer(),
                BooleanTransformer(),
            ),
        ),
    )

    val reporter = ConsoleDiagnosticReporter()
    val version = "1.1"
    val lexer = PrintScriptLexer()
    val parser = PrintScriptParser()
    val formatter = PrintScriptFormatter()
    val runner = FormatterRunner(lexer, parser, formatter)
    runner.run(version, source, target, config, reporter)
}
