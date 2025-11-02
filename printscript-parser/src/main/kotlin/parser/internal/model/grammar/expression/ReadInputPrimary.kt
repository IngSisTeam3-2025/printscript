package parser.internal.model.grammar.expression

import model.node.LeftParenthesisNode
import model.node.Node
import model.node.NodeType
import model.node.ReadInputExpressionNode
import model.node.ReadInputKeywordNode
import model.node.RightParenthesisNode
import model.span.Span
import model.token.LeftParenthesisToken
import model.token.ReadInputToken
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

internal class ReadInputPrimary : Primary {
    override val type: NodeType = ReadInputExpressionNode

    override fun match(
        tokens: List<Token>,
        table: GrammarTable,
    ): Outcome<GrammarMatch, GrammarFail> {
        var consumed = 0

        val readInput = tokens.getOrElse(consumed) {
            return Outcome.Error(
                GrammarFail(
                    "Expected 'readInput'",
                    MissingExpression,
                    consumed,
                ),
            )
        }
        if (readInput.type != ReadInputToken) {
            return Outcome.Error(
                GrammarFail(
                    "Expected 'readInput', got '${readInput.lexeme}'",
                    MissingExpression,
                    consumed,
                ),
            )
        }
        consumed += 1

        val lparen = tokens.getOrElse(consumed) {
            return Outcome.Error(
                GrammarFail(
                    "Expected '(' after 'readInput'",
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

        val node = buildNode(readInput, lparen, inner.node, rparen, tokens)
        return Outcome.Ok(GrammarMatch(node, consumed))
    }

    private fun buildNode(
        readInput: Token,
        lparen: Token,
        inner: Node,
        rparen: Token,
        tokens: List<Token>,
    ): Node {
        val children = listOf(
            Node.Leaf(
                ReadInputKeywordNode,
                StringValue(readInput.lexeme),
                readInput.span,
                readInput.leading,
                readInput.trailing,
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
