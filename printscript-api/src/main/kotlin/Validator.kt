import model.diagnostic.Diagnostic
import model.node.Node

interface Validator {
    fun validate(nodes: Sequence<Node>): Sequence<Diagnostic>
}
