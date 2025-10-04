import model.diagnostic.Diagnostic
import model.node.Node
import model.rule.Rule

interface Linter {
    fun lint(nodes: Sequence<Node>, rules: Collection<Rule>): Sequence<Diagnostic>
}
