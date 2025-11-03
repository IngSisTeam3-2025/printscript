package parser.internal.model.grammar.primary

import model.node.Node
import model.node.NodeType
import model.node.UnaryOperationNode
import model.span.Span
import model.token.Token
import model.token.TokenType
import model.value.StringValue
import parser.internal.model.category.InvalidOperator
import parser.internal.model.category.MissingOperator
import parser.internal.model.grammar.GrammarFail
import parser.internal.model.grammar.GrammarMatch
import parser.internal.table.GrammarTable
import type.outcome.Outcome
import type.outcome.getOrElse

internal class UnaryOperationPrimary(
    private val operators: Map<TokenType, NodeType>,
) : Primary {
    override val type = UnaryOperationNode

    override fun match(
        tokens: List<Token>,
        table: GrammarTable,
    ): Outcome<GrammarMatch, GrammarFail> {
        var consumed = 0

        val first = tokens.getOrElse(consumed) {
            return Outcome.Error(
                GrammarFail(
                    "Expected unary operator",
                    MissingOperator,
                    consumed,
                ),
            )
        }

        if (first.type !in operators) {
            return Outcome.Error(
                GrammarFail(
                    "Invalid unary operator",
                    InvalidOperator,
                    consumed,
                ),
            )
        }
        consumed += 1

        val expression = tokens.subList(consumed, tokens.size)
        val operand = table.dispatchPrimary(expression).getOrElse {
            return Outcome.Error(
                GrammarFail(
                    it.message,
                    it.category,
                    consumed + it.consumed,
                ),
            )
        }
        consumed += operand.consumed

        val operatorNodeType = operators.getValue(first.type)

        val node = buildNode(first, operatorNodeType, operand)
        return Outcome.Ok(GrammarMatch(node, consumed))
    }

    private fun buildNode(
        operator: Token,
        operatorNodeType: NodeType,
        operand: GrammarMatch,
    ): Node {
        return Node.Composite(
            children = listOf(
                Node.Leaf(
                    operatorNodeType,
                    StringValue(operator.lexeme),
                    operator.span,
                    operator.leading,
                    operator.trailing,
                ),
                operand.node,
            ),
            type = type,
            span = Span(operator.span.start, operand.node.span.end),
        )
    }
}
