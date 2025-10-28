package model.visitor.context

import model.diagnostic.Diagnostic
import model.value.Value
import type.outcome.Outcome

data class VisitResult(
    val outcome: Outcome<Value, Diagnostic>,
    val context: VisitorContext,
)
