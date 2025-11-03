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

fun main() {
    val source = FileInputReader("C:\\Users\\julir\\faculty\\ingsis\\printscript\\printscript-cli\\main.ps")
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
    val validator = PrintScriptValidator()
    val linter = PrintScriptLinter()
    val runner = LinterRunner(lexer, parser, validator, linter)
    runner.run(version, source, config, reporter)
}
