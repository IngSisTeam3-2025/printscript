package formatter

import Formatter
import formatter.internal.model.value.DocValue
import formatter.internal.table.VisitorTableRegistry
import formatter.internal.type.toDoc
import model.diagnostic.Diagnostic
import model.doc.Doc
import model.node.Node
import model.rule.Rule
import model.visitor.context.ContextVisitorTable
import model.visitor.context.VisitorContext
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
    ): Outcome<ContextVisitorTable, Diagnostic> = VisitorTableRegistry.get(version, rules)

    private fun formatNodes(
        nodes: Sequence<Node>,
        table: ContextVisitorTable,
    ): Sequence<Outcome<Doc, Diagnostic>> {
        return sequence {
            var currentContext = VisitorContext()

            for (node in nodes) {
                val visitResult = table.dispatch(node, currentContext)
                currentContext = visitResult.context

                when (val outcome = visitResult.outcome) {
                    is Outcome.Ok -> {
                        when (val result = outcome.value) {
                            is DocValue -> {
                                yield(Outcome.Ok(result.value))
                            }
                            else -> {
                                yield(Outcome.Ok(node.toDoc()))
                            }
                        }
                    }
                    is Outcome.Error -> {
                        yield(outcome)
                    }
                }
            }
        }
    }
}
