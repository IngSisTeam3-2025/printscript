import model.doc.Doc
import model.node.Node
import model.rule.Rule

interface Formatter {
    fun format(nodes: Sequence<Node>, rules: Collection<Rule>): Sequence<Doc>
}
