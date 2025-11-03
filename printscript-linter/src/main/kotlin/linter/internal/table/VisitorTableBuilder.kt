package linter.internal.table

import linter.internal.model.error.ConfigurationError
import model.diagnostic.Diagnostic
import model.rule.Rule
import model.visitor.Visitor
import model.visitor.VisitorTable
import type.outcome.Outcome

internal interface VisitorTableBuilder {

    class DefaultVisitorTable(override val visitors: Collection<Visitor>) : VisitorTable

    val factories: Map<String, VisitorFactory>

    @Suppress("TooGenericExceptionCaught", "SwallowedException")
    fun build(rules: Collection<Rule>): Outcome<VisitorTable, Diagnostic> {
        val visitors = mutableListOf<Visitor>()

        for (rule in rules) {
            val factory = factories[rule.signature] ?: continue
            try {
                visitors.add(factory.create(rule))
            } catch (e: ClassCastException) {
                val type = rule.value.type()
                val message = "Rule '${rule.signature}' value has invalid type '$type'"
                return Outcome.Error(ConfigurationError(message))
            }
        }

        return Outcome.Ok(DefaultVisitorTable(visitors))
    }
}
