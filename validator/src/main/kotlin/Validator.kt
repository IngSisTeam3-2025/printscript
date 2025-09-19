import error.ParseError
import node.AstNode
import reporter.DiagnosticReporter
import result.Result

@Suppress("UNUSED_PARAMETER")
class Validator {

    fun validate(source: Sequence<Result<AstNode, ParseError>>, reporter: DiagnosticReporter) {
        TODO("not implemented")
    }
}
