package formatter

import ast.AstNode
import cli.CliTool
import java.io.Reader
import java.io.Writer

class Formatter : IFormatter, CliTool {

    override fun analyze(
        source: Reader,
        config: Reader,
        target: Writer,
        parser: Iterator<Result<AstNode>>,
        reporter: IDiagnosticReporter
    ) {
        TODO("Not yet implemented")
    }

}
