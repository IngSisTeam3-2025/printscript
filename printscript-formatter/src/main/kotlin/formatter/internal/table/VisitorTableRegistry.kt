package formatter.internal.table

import model.rule.Rule
import model.visitor.VisitorTable
import type.option.Option

internal object VisitorTableRegistry {

    private val builders: Map<String, VisitorTableBuilder> = mapOf(
        "1.0" to PrintScriptV10,
        "1.1" to PrintScriptV11,
    )

    fun get(version: String, rules: Collection<Rule>): Option<VisitorTable> {
        val builder = builders[version.lowercase()]
        return if (builder != null) {
            Option.Some(builder.build(rules))
        } else {
            Option.None
        }
    }
}
