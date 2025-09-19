package reporter

interface DiagnosticReporter {
    fun report(diagnostic: Diagnostic)
}
