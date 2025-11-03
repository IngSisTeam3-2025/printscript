import model.diagnostic.Diagnostic
import model.doc.Doc
import model.node.Node
import model.rule.Rule
import type.outcome.Outcome

interface Formatter {
    fun format(
        version: String,
        nodes: Sequence<Node>,
        rules: Collection<Rule>,
    ): Sequence<Outcome<Doc, Diagnostic>>
}
