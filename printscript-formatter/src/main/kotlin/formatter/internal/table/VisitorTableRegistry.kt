package formatter.internal.table

import formatter.internal.model.error.ConfigurationError
import model.diagnostic.Diagnostic
import model.rule.Rule
import model.visitor.VisitorTable
import type.outcome.Outcome

internal object VisitorTableRegistry {

    private val builders: Map<String, Lazy<VisitorTableBuilder>> = mapOf(
        "1.0" to lazy { PrintScriptV10 },
        "1.1" to lazy { PrintScriptV11 },
    )

    fun get(
        version: String,
        rules: Collection<Rule>,
    ): Outcome<VisitorTable, Diagnostic> {
        val error = ConfigurationError("Unsupported language version '$version'")
        val lazyBuilder = builders[version.lowercase()] ?: return Outcome.Error(error)

        val builder = lazyBuilder.value
        return when (val visitorsOutcome = builder.build(rules)) {
            is Outcome.Ok -> {
                Outcome.Ok(visitorsOutcome.value)
            }
            is Outcome.Error -> Outcome.Error(visitorsOutcome.error)
        }
    }
}
