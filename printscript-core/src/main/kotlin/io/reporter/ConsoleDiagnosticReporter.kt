package io.reporter

import model.diagnostic.Diagnostic

class ConsoleDiagnosticReporter : DiagnosticReporter {

    override fun report(diagnostic: Diagnostic) {
        println(diagnostic.format())
    }
}
