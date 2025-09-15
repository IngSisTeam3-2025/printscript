package linter

import ast.AstNode
import java.io.Reader

interface ILinter {
    fun analyze(source: Reader,
                config: Reader,
                parser: Iterator<Result<AstNode>>,
                reporter: IDiagnosticReporter)
}
