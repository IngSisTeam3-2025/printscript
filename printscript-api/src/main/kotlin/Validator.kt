import model.diagnostic.Diagnostic
import model.node.Node
import type.outcome.Outcome

interface Validator {
    fun validate(
        version: String,
        nodes: Sequence<Node>,
    ): Sequence<Outcome<Node, Diagnostic>>
}
