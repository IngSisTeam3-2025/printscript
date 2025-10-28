package formatter

import Formatter
import formatter.internal.model.error.ConfigurationError
import formatter.internal.model.value.DocValue
import formatter.internal.table.VisitorTableRegistry
import model.diagnostic.Diagnostic
import model.doc.Doc
import model.node.Node
import model.rule.Rule
import model.visitor.VisitorTable
import type.option.Option
import type.outcome.Outcome

class PrintScriptFormatter : Formatter {

    override fun format(
        version: String,
        nodes: Sequence<Node>,
        rules: Collection<Rule>,
    ): Sequence<Outcome<Doc, Diagnostic>> {
        return sequence {
            when (val table = getVisitorTable(version, rules)) {
                is Option.Some -> yieldAll(formatNodes(nodes, table.value))
                is Option.None -> yield(Outcome.Error(buildConfigurationError(version)))
            }
        }
    }

    private fun getVisitorTable(
        version: String,
        rules: Collection<Rule>,
    ): Option<VisitorTable> {
        return VisitorTableRegistry.get(version, rules)
    }

    private fun buildConfigurationError(version: String): Diagnostic {
        return ConfigurationError("Unsupported language version '$version'")
    }

    private fun formatNodes(
        nodes: Sequence<Node>,
        table: VisitorTable,
    ): Sequence<Outcome<Doc, Diagnostic>> {
        return sequence {
            for (node in nodes) {
                for (visitor in table.visitors) {
                    val visit = node.accept(visitor, table)
                    when (visit) {
                        is Outcome.Ok -> {
                            val result = visit.value
                            if (result is DocValue) {
                                yield(Outcome.Ok(result.value))
                            }
                        }
                        is Outcome.Error -> yield(visit)
                    }
                }
            }
        }
    }
}
