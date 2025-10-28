package parser.internal.model.grammar.primary

import model.node.LeftParenthesisNode
import model.node.Node
import model.node.ParenthesizedExpressionNode
import model.node.RightParenthesisNode
import model.span.Span
import model.token.LeftParenthesisToken
import model.token.RightParenthesisToken
import model.token.Token
import model.value.StringValue
import parser.internal.model.category.MissingClosingParenthesis
import parser.internal.model.category.MissingOpeningParenthesis
import parser.internal.model.grammar.GrammarFail
import parser.internal.model.grammar.GrammarMatch
import parser.internal.table.GrammarTable
import type.outcome.Outcome
import type.outcome.getOrElse

internal class ParenthesizedPrimary : Primary {
    override val type = ParenthesizedExpressionNode

    override fun match(
        tokens: List<Token>,
        table: GrammarTable,
    ): Outcome<GrammarMatch, GrammarFail> {
        var consumed = 0

        val lparen = tokens.getOrElse(consumed) {
            return Outcome.Error(
                GrammarFail(
                    "Expected '('",
                    MissingOpeningParenthesis,
                    consumed,
                ),
            )
        }
        if (lparen.type != LeftParenthesisToken) {
            return Outcome.Error(
                GrammarFail(
                    "Expected '(', got '${lparen.lexeme}'",
                    MissingOpeningParenthesis,
                    consumed,
                ),
            )
        }
        consumed += 1

        val inner = table.dispatchExpression(tokens.drop(consumed)).getOrElse {
            return Outcome.Error(it)
        }
        consumed += inner.consumed

        val rparen = tokens.getOrElse(consumed) {
            return Outcome.Error(
                GrammarFail(
                    "Expected ')'",
                    MissingClosingParenthesis,
                    consumed,
                ),
            )
        }
        if (rparen.type != RightParenthesisToken) {
            return Outcome.Error(
                GrammarFail(
                    "Expected ')', got '${rparen.lexeme}'",
                    MissingClosingParenthesis,
                    consumed,
                ),
            )
        }
        consumed += 1

        val node = buildNode(lparen, inner, rparen)
        return Outcome.Ok(GrammarMatch(node, consumed))
    }

    private fun buildNode(
        lparen: Token,
        inner: GrammarMatch,
        rparen: Token,
    ): Node {
        return Node.Composite(
            children = listOf(
                Node.Leaf(
                    LeftParenthesisNode,
                    StringValue(lparen.lexeme),
                    lparen.span,
                    lparen.leading,
                    lparen.trailing,
                ),
                inner.node,
                Node.Leaf(
                    RightParenthesisNode,
                    StringValue(rparen.lexeme),
                    rparen.span,
                    rparen.leading,
                    rparen.trailing,
                ),
            ),
            type = type,
            span = Span(lparen.span.start, rparen.span.end),
        )
    }
}
