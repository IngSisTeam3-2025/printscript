package reporter

import location.SourceLocation

interface DiagnosticReporter {
    fun reportError(severity: Severity, message: String, start: SourceLocation, end: SourceLocation)
}
