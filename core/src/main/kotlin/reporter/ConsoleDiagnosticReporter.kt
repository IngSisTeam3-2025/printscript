package reporter

import location.SourceLocation

class ConsoleDiagnosticReporter : DiagnosticReporter {
    override fun reportError(message: String, start: SourceLocation, end: SourceLocation) {
        println("Error at ${start.line}:${start.column}-${end.line}:${end.column}: $message")
    }
}