package parser.internal.model.grammar.primary

import model.node.IdentifierNode
import model.node.Node
import model.token.IdentifierToken
import model.token.Token
import model.value.StringValue
import parser.internal.model.category.MissingIdentifier
import parser.internal.model.grammar.GrammarFail
import parser.internal.model.grammar.GrammarMatch
import parser.internal.table.GrammarTable
import type.outcome.Outcome

internal class IdentifierPrimary : Primary {
    override val type = IdentifierNode

    override fun match(
        tokens: List<Token>,
        table: GrammarTable,
    ): Outcome<GrammarMatch, GrammarFail> {
        val first = tokens.getOrElse(0) {
            return Outcome.Error(
                GrammarFail(
                    "Expected identifier",
                    MissingIdentifier,
                    0,
                ),
            )
        }
        if (first.type != IdentifierToken) {
            return Outcome.Error(
                GrammarFail(
                    "Expected identifier, got '${first.lexeme}'",
                    MissingIdentifier,
                    0,
                ),
            )
        }

        val node = buildNode(first)
        return Outcome.Ok(GrammarMatch(node, 1))
    }

    private fun buildNode(first: Token): Node {
        return Node.Leaf(
            value = StringValue(first.lexeme),
            leading = first.leading,
            trailing = first.trailing,
            type = type,
            span = first.span,
        )
    }
}
