package formatter.internal.table

import formatter.internal.model.error.ConfigurationError
import formatter.internal.visitor.factory.ContextVisitorFactory
import model.diagnostic.Diagnostic
import model.rule.Rule
import model.visitor.context.ContextVisitor
import type.outcome.Outcome

internal interface ContextVisitorTableBuilder {
    val factories: Map<String, ContextVisitorFactory>

    @Suppress("TooGenericExceptionCaught", "SwallowedException")
    fun build(rules: Collection<Rule>): Outcome<FormatterContextVisitorTable, Diagnostic> {
        val visitors = mutableListOf<ContextVisitor>()

        for (rule in rules) {
            val factory = factories[rule.signature] ?: continue
            try {
                visitors.add(factory.create(rule))
            } catch (e: Exception) {
                val type = rule.value.type()
                val message = "Rule '${rule.signature}' value has invalid type '$type'"
                return Outcome.Error(ConfigurationError(message))
            }
        }

        return Outcome.Ok(FormatterContextVisitorTable(visitors))
    }
}
