import io.reader.input.FileInputReader
import io.reporter.ConsoleDiagnosticReporter
import lexer.PrintScriptLexer
import parser.PrintScriptParser
import validator.PrintScriptValidator
import validator.ValidatorRunner

fun main() {
    val source = FileInputReader("C:\\Users\\julir\\faculty\\ingsis\\printscript\\printscript-cli\\main.ps")
    val reporter = ConsoleDiagnosticReporter()

    val version = "1.1"
    val lexer = PrintScriptLexer()
    val parser = PrintScriptParser()
    val validator = PrintScriptValidator()
    val runner = ValidatorRunner(lexer, parser, validator)
    runner.run(version, source, reporter)
    return
}
