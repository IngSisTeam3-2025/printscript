package model.visitor.context

import model.node.Node
import model.value.NoneValue
import type.option.Option
import type.option.getOrElse
import type.outcome.Outcome

interface ContextVisitorTable {
    val visitors: Collection<ContextVisitor>

    fun dispatch(node: Node, context: VisitorContext): VisitResult {
        var currentContext = context
        var result: Option<VisitResult> = Option.None

        for (visitor in visitors) {
            val visit = node.accept(visitor, this, currentContext)
            if (visit.outcome is Outcome.Error) return visit

            if (result is Option.None && visit.outcome is Outcome.Ok) {
                val value = visit.outcome.value
                if (value !is NoneValue) {
                    result = Option.Some(visit)
                }
            }
            currentContext = visit.context
        }
        return result.getOrElse { VisitResult(Outcome.Ok(NoneValue), currentContext) }
    }
}
