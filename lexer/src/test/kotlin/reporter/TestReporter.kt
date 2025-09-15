package reporter

import location.SourceLocation

class TestReporter : Reporter {
    val errors = mutableListOf<String>()
    override fun reportError(message: String, start: SourceLocation, end: SourceLocation) {
        errors.add("Error at $start -> $end ~ $message")
    }
}