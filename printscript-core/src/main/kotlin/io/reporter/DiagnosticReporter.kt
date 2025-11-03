package io.reporter

import model.diagnostic.Diagnostic

interface DiagnosticReporter {
    fun report(diagnostic: Diagnostic)
}
