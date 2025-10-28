package formatter.internal.table

import formatter.internal.visitor.factory.VisitorFactory
import model.rule.Rule
import model.visitor.Visitor
import model.visitor.VisitorTable

internal interface VisitorTableBuilder {

    class DefaultVisitorTable(override val visitors: Collection<Visitor>) : VisitorTable

    val factories: Map<String, VisitorFactory>

    fun build(rules: Collection<Rule>): VisitorTable {
        val visitors = mutableListOf<Visitor>()

        for (rule in rules) {
            val factory = factories[rule.signature]
            if (factory != null) {
                visitors.add(factory.create(rule))
            }
        }
        return DefaultVisitorTable(visitors)
    }
}
