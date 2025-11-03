package parser.internal.model.grammar.expression

import model.node.LeftParenthesisNode
import model.node.Node
import model.node.NodeType
import model.node.ReadEnvExpressionNode
import model.node.ReadEnvKeywordNode
import model.node.RightParenthesisNode
import model.span.Span
import model.token.LeftParenthesisToken
import model.token.ReadEnvToken
import model.token.RightParenthesisToken
import model.token.Token
import model.value.StringValue
import parser.internal.model.category.MissingClosingParenthesis
import parser.internal.model.category.MissingExpression
import parser.internal.model.category.MissingOpeningParenthesis
import parser.internal.model.grammar.GrammarFail
import parser.internal.model.grammar.GrammarMatch
import parser.internal.model.grammar.primary.Primary
import parser.internal.table.GrammarTable
import type.outcome.Outcome
import type.outcome.getOrElse

internal class ReadEnvPrimary : Primary {
    override val type: NodeType = ReadEnvExpressionNode

    override fun match(
        tokens: List<Token>,
        table: GrammarTable,
    ): Outcome<GrammarMatch, GrammarFail> {
        var consumed = 0

        val readEnv = tokens.getOrElse(consumed) {
            return Outcome.Error(
                GrammarFail(
                    "Expected 'readEnv'",
                    MissingExpression,
                    consumed,
                ),
            )
        }
        if (readEnv.type != ReadEnvToken) {
            return Outcome.Error(
                GrammarFail(
                    "Expected 'readEnv', got '${readEnv.lexeme}'",
                    MissingExpression,
                    consumed,
                ),
            )
        }
        consumed += 1

        val lparen = tokens.getOrElse(consumed) {
            return Outcome.Error(
                GrammarFail(
                    "Expected '(' after 'readEnv'",
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

        val innerResult = table.dispatchExpression(tokens.drop(consumed))
        val inner = innerResult.getOrElse {
            return Outcome.Error(
                GrammarFail(
                    it.message,
                    it.category,
                    consumed + it.consumed,
                ),
            )
        }
        consumed += inner.consumed

        val rparen = tokens.getOrElse(consumed) {
            return Outcome.Error(
                GrammarFail(
                    "Expected ')' after expression",
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

        val node = buildNode(readEnv, lparen, inner.node, rparen, tokens)
        return Outcome.Ok(GrammarMatch(node, consumed))
    }

    private fun buildNode(
        readEnv: Token,
        lparen: Token,
        inner: Node,
        rparen: Token,
        tokens: List<Token>,
    ): Node {
        val children = listOf(
            Node.Leaf(
                ReadEnvKeywordNode,
                StringValue(readEnv.lexeme),
                readEnv.span,
                readEnv.leading,
                readEnv.trailing,
            ),
            Node.Leaf(
                LeftParenthesisNode,
                StringValue(lparen.lexeme),
                lparen.span,
                lparen.leading,
                lparen.trailing,
            ),
            inner,
            Node.Leaf(
                RightParenthesisNode,
                StringValue(rparen.lexeme),
                rparen.span,
                rparen.leading,
                rparen.trailing,
            ),
        )

        return Node.Composite(
            children = children,
            type = type,
            span = Span(tokens.first().span.start, rparen.span.end),
        )
    }
}
