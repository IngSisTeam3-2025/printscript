package linter.internal.table

import model.rule.Rule
import model.visitor.VisitorTable
import type.option.Option

internal object VisitorTableRegistry {

    private val builders: Map<String, Lazy<VisitorTableBuilder>> = mapOf(
        "1.0" to lazy { PrintScriptV10 },
        "1.1" to lazy { PrintScriptV11 },
    )

    fun get(version: String, rules: Collection<Rule>): Option<VisitorTable> {
        val lazy = builders[version.lowercase()]
        return if (lazy != null) {
            val builder = lazy.value
            Option.Some(builder.build(rules))
        } else {
            Option.None
        }
    }
}
