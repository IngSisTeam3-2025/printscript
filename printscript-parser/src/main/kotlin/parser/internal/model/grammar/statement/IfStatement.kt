package parser.internal.model.grammar.statement

import model.node.ElseBlockNode
import model.node.ElseNode
import model.node.IfNode
import model.node.IfStatementNode
import model.node.LeftParenthesisNode
import model.node.Node
import model.node.NodeType
import model.node.RightParenthesisNode
import model.span.Span
import model.token.ElseToken
import model.token.IfToken
import model.token.LeftParenthesisToken
import model.token.RightParenthesisToken
import model.token.Token
import model.value.StringValue
import parser.internal.model.category.MissingClosingParenthesis
import parser.internal.model.category.MissingIfDeclaration
import parser.internal.model.category.MissingOpeningParenthesis
import parser.internal.model.grammar.GrammarFail
import parser.internal.model.grammar.GrammarMatch
import parser.internal.table.GrammarTable
import type.option.Option
import type.outcome.Outcome
import type.outcome.getOrElse

internal class IfStatement : Statement {
    override val type: NodeType = IfStatementNode

    override fun match(
        tokens: List<Token>,
        table: GrammarTable,
    ): Outcome<GrammarMatch, GrammarFail> {
        var consumed = 0

        val ifToken = tokens.getOrElse(consumed) {
            return Outcome.Error(
                GrammarFail(
                    "Expected 'if'",
                    MissingIfDeclaration,
                    consumed,
                ),
            )
        }
        if (ifToken.type != IfToken) {
            return Outcome.Error(
                GrammarFail(
                    "Expected 'if', got '${ifToken.lexeme}'",
                    MissingIfDeclaration,
                    consumed,
                ),
            )
        }
        consumed += 1

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

        val condition = table.dispatchExpression(tokens.drop(consumed)).getOrElse {
            return Outcome.Error(
                GrammarFail(
                    it.message,
                    it.category,
                    consumed + it.consumed,
                ),
            )
        }
        consumed += condition.consumed

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

        val thenBlock = table.dispatchExpression(tokens.drop(consumed)).getOrElse {
            return Outcome.Error(
                GrammarFail(
                    it.message,
                    it.category,
                    consumed + it.consumed,
                ),
            )
        }
        consumed += thenBlock.consumed

        val elseResult = parseOptionalElse(tokens.drop(consumed), table)
        if (elseResult is Outcome.Error) {
            return Outcome.Error(
                GrammarFail(
                    elseResult.error.message,
                    elseResult.error.category,
                    consumed + elseResult.error.consumed,
                ),
            )
        }
        val elseBlock = (elseResult as Outcome.Ok).value
        if (elseBlock is Option.Some) {
            consumed += elseBlock.value.consumed
        }

        val node = buildNode(
            ifToken,
            lparen,
            condition,
            rparen,
            thenBlock,
            elseBlock,
            tokens,
        )
        return Outcome.Ok(GrammarMatch(node, consumed))
    }

    private fun parseOptionalElse(
        tokens: List<Token>,
        table: GrammarTable,
    ): Outcome<Option<GrammarMatch>, GrammarFail> {
        if (tokens.isEmpty() || tokens[0].type != ElseToken) return Outcome.Ok(Option.None)

        var consumed = 0
        val elseToken = tokens[0]
        consumed += 1

        val elseBlockExpr = table.dispatchExpression(tokens.drop(consumed)).getOrElse {
            return Outcome.Error(
                GrammarFail(
                    it.message,
                    it.category,
                    consumed + it.consumed,
                ),
            )
        }
        consumed += elseBlockExpr.consumed

        val elseNode = Node.Composite(
            children = listOf(
                Node.Leaf(
                    ElseNode,
                    StringValue(elseToken.lexeme),
                    elseToken.span,
                    elseToken.leading,
                    elseToken.trailing,
                ),
                elseBlockExpr.node,
            ),
            type = ElseBlockNode,
            span = Span(elseToken.span.start, elseBlockExpr.node.span.end),
        )

        return Outcome.Ok(Option.Some(GrammarMatch(elseNode, consumed)))
    }

    private fun buildNode(
        ifToken: Token,
        lparen: Token,
        condition: GrammarMatch,
        rparen: Token,
        thenBlock: GrammarMatch,
        elseBlock: Option<GrammarMatch>,
        tokens: List<Token>,
    ): Node {
        val children = buildList {
            add(
                Node.Leaf(
                    IfNode,
                    StringValue(ifToken.lexeme),
                    ifToken.span,
                    ifToken.leading,
                    ifToken.trailing,
                ),
            )
            add(
                Node.Leaf(
                    LeftParenthesisNode,
                    StringValue(lparen.lexeme),
                    lparen.span,
                    lparen.leading,
                    lparen.trailing,
                ),
            )
            add(condition.node)
            add(
                Node.Leaf(
                    RightParenthesisNode,
                    StringValue(rparen.lexeme),
                    rparen.span,
                    rparen.leading,
                    rparen.trailing,
                ),
            )
            add(thenBlock.node)

            if (elseBlock is Option.Some) add(elseBlock.value.node)
        }

        val endSpan = if (elseBlock is Option.Some) {
            elseBlock.value.node.span.end
        } else {
            thenBlock.node.span.end
        }

        return Node.Composite(
            children = children,
            type = type,
            span = Span(tokens.first().span.start, endSpan),
        )
    }
}
