package linter

import ast.AstNode
import cli.CliTool
import java.io.Reader

class Linter : ILinter, CliTool {

    override fun analyze(
        source: Reader,
        config: Reader,
        parser: Iterator<Result<AstNode>>,
        reporter: IDiagnosticReporter
    ) {
        TODO("Not yet implemented")
    }

}
