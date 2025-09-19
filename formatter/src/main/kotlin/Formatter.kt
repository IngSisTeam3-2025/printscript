import error.ParseError
import node.CstNode
import reporter.DiagnosticReporter
import result.Result
import java.io.Writer

@Suppress("UNUSED_PARAMETER")
class Formatter {

    fun format(
        source: Sequence<Result<CstNode, ParseError>>,
        output: Writer,
        reporter: DiagnosticReporter,
    ) {
        TODO("not implemented")
    }
}
