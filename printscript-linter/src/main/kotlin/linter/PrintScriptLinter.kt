package linter

import Linter
import linter.internal.model.error.ConfigurationError
import linter.internal.table.VisitorTableRegistry
import model.diagnostic.Diagnostic
import model.node.Node
import model.rule.Rule
import model.visitor.VisitorTable
import type.option.Option
import type.outcome.Outcome

class PrintScriptLinter : Linter {

    override fun lint(
        version: String,
        nodes: Sequence<Node>,
        rules: Collection<Rule>,
    ): Sequence<Diagnostic> {
        return sequence {
            when (val table = getVisitorTable(version, rules)) {
                is Option.Some -> yieldAll(lintNodes(nodes, table.value))
                is Option.None -> yield(buildConfigurationError(version))
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

    private fun lintNodes(
        nodes: Sequence<Node>,
        table: VisitorTable,
    ): Sequence<Diagnostic> {
        return sequence {
            for (node in nodes) {
                for (visitor in table.visitors) {
                    val outcome = node.accept(visitor, table)
                    if (outcome is Outcome.Error) {
                        yield(outcome.error)
                    }
                }
            }
        }
    }
}
