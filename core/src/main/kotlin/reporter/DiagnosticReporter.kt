package reporter

import location.SourceLocation

interface DiagnosticReporter {
    fun reportError(message: String, start: SourceLocation, end: SourceLocation)
}
