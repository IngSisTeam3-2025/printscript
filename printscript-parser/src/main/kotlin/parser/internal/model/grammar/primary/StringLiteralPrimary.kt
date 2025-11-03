package parser.internal.model.grammar.primary

import model.node.Node
import model.node.NodeType
import model.node.StringLiteralNode
import model.token.StringLiteralToken
import model.token.Token
import model.value.StringValue
import parser.internal.model.category.MissingStringLiteral
import parser.internal.model.grammar.GrammarFail
import parser.internal.model.grammar.GrammarMatch
import parser.internal.table.GrammarTable
import type.outcome.Outcome

internal class StringLiteralPrimary : Primary {
    override val type: NodeType = StringLiteralNode

    override fun match(
        tokens: List<Token>,
        table: GrammarTable,
    ): Outcome<GrammarMatch, GrammarFail> {
        val first = tokens.getOrElse(0) {
            return Outcome.Error(
                GrammarFail(
                    "Expected string literal",
                    MissingStringLiteral,
                    0,
                ),
            )
        }
        if (first.type != StringLiteralToken) {
            return Outcome.Error(
                GrammarFail(
                    "Expected string literal, got '${first.lexeme}'",
                    MissingStringLiteral,
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
