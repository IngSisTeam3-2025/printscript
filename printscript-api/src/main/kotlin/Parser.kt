import model.diagnostic.Diagnostic
import model.node.Node
import model.token.Token
import type.outcome.Outcome

interface Parser {
    fun parse(tokens: Sequence<Token>): Sequence<Outcome<Node, Diagnostic>>
}
