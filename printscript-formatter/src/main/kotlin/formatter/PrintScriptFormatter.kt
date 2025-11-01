package formatter

import Formatter
import formatter.internal.model.value.DocValue
import formatter.internal.table.VisitorTableRegistry
import formatter.internal.type.toDoc
import model.diagnostic.Diagnostic
import model.doc.Doc
import model.node.Node
import model.rule.Rule
import model.visitor.VisitorTable
import type.outcome.Outcome

class PrintScriptFormatter : Formatter {

    override fun format(
        version: String,
        nodes: Sequence<Node>,
        rules: Collection<Rule>,
    ): Sequence<Outcome<Doc, Diagnostic>> {
        return sequence {
            when (val table = getVisitorTable(version, rules)) {
                is Outcome.Ok -> {
                    yieldAll(formatNodes(nodes, table.value))
                }
                is Outcome.Error -> {
                    yield(Outcome.Error(table.error))
                }
            }
        }
    }

    private fun getVisitorTable(
        version: String,
        rules: Collection<Rule>,
    ): Outcome<VisitorTable, Diagnostic> = VisitorTableRegistry.get(version, rules)

    private fun formatNodes(
        nodes: Sequence<Node>,
        table: VisitorTable,
    ): Sequence<Outcome<Doc, Diagnostic>> {
        return sequence {
            for (node in nodes) {
                var yielded = false
                for (visitor in table.visitors) {
                    val visit = node.accept(visitor, table)
                    when (visit) {
                        is Outcome.Ok -> {
                            val result = visit.value
                            if (result is DocValue) {
                                yield(Outcome.Ok(result.value))
                                yielded = true
                            }
                        }
                        is Outcome.Error -> yield(visit)
                    }
                }
                if (!yielded) {
                    val fallback = node.toDoc()
                    yield(Outcome.Ok(fallback))
                }
            }
        }
    }
}
