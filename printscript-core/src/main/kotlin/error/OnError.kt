package error

import io.reporter.DiagnosticReporter
import model.diagnostic.Diagnostic
import type.outcome.Outcome

fun <T> Sequence<Outcome<T, Diagnostic>>.onError(
    reporter: DiagnosticReporter,
    errorFlag: ErrorFlag,
): Sequence<T> = sequence {
    for (outcome in this@onError) {
        when (outcome) {
            is Outcome.Ok -> yield(outcome.value)
            is Outcome.Error -> {
                reporter.report(outcome.error)
                errorFlag.hasError = true
            }
        }
        if (errorFlag.hasError) {
            return@sequence
        }
    }
}

fun <T> Sequence<Outcome<T, Diagnostic>>.onError(
    reporter: DiagnosticReporter,
): Sequence<T> = sequence {
    for (outcome in this@onError) {
        when (outcome) {
            is Outcome.Ok -> yield(outcome.value)
            is Outcome.Error -> {
                reporter.report(outcome.error)
            }
        }
    }
}
