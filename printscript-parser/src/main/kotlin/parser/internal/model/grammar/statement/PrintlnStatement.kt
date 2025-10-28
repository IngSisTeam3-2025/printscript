package parser.internal.model.grammar.statement

import model.node.LeftParenthesisNode
import model.node.Node
import model.node.PrintlnKeywordNode
import model.node.PrintlnStatementNode
import model.node.RightParenthesisNode
import model.node.SemicolonNode
import model.span.Span
import model.token.LeftParenthesisToken
import model.token.PrintlnToken
import model.token.RightParenthesisToken
import model.token.SemicolonToken
import model.token.Token
import model.value.StringValue
import parser.internal.model.category.MissingClosingParenthesis
import parser.internal.model.category.MissingEndOfLine
import parser.internal.model.category.MissingExpression
import parser.internal.model.category.MissingOpeningParenthesis
import parser.internal.model.grammar.GrammarFail
import parser.internal.model.grammar.GrammarMatch
import parser.internal.table.GrammarTable
import type.option.Option
import type.outcome.Outcome

internal class PrintlnStatement : Statement {
    override val type = PrintlnStatementNode

    override fun match(
        tokens: List<Token>,
        table: GrammarTable,
    ): Outcome<GrammarMatch, GrammarFail> {
        var consumed = 0

        val println = tokens.getOrElse(consumed) {
            return Outcome.Error(
                GrammarFail(
                    "Expected 'println'",
                    MissingExpression,
                    consumed,
                ),
            )
        }
        if (println.type != PrintlnToken) {
            val fail = GrammarFail(
                "Expected 'println', got '${println.lexeme}'",
                MissingExpression,
                consumed,
            )
            return Outcome.Error(fail)
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
            val fail = GrammarFail(
                "Expected '(', got '${lparen.lexeme}'",
                MissingOpeningParenthesis,
                consumed,
            )
            return Outcome.Error(fail)
        }
        consumed += 1

        val innerResult = parseInner(tokens.drop(consumed), table)
        if (innerResult is Outcome.Error) {
            val error = innerResult.error
            val fail = GrammarFail(
                error.message,
                error.category,
                consumed + error.consumed,
            )
            return Outcome.Error(fail)
        }
        val inner = (innerResult as Outcome.Ok).value
        if (inner is Option.Some) {
            consumed += inner.value.consumed
        }

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
            val fail = GrammarFail(
                "Expected ')', got '${rparen.lexeme}'",
                MissingClosingParenthesis,
                consumed,
            )
            return Outcome.Error(fail)
        }
        consumed += 1

        val semicolon = tokens.getOrElse(consumed) {
            return Outcome.Error(
                GrammarFail(
                    "Expected ';'",
                    MissingEndOfLine,
                    consumed,
                ),
            )
        }
        if (semicolon.type != SemicolonToken) {
            val fail = GrammarFail(
                "Expected ';', got '${semicolon.lexeme}'",
                MissingEndOfLine,
                consumed,
            )
            return Outcome.Error(fail)
        }
        consumed += 1

        val node = buildNode(println, lparen, inner, rparen, semicolon, tokens)
        return Outcome.Ok(GrammarMatch(node, consumed))
    }

    private fun parseInner(
        tokens: List<Token>,
        table: GrammarTable,
    ): Outcome<Option<GrammarMatch>, GrammarFail> {
        val noArguments = tokens.isEmpty() || tokens[0].type == RightParenthesisToken
        if (noArguments) {
            return Outcome.Ok(Option.None)
        }

        return when (val result = table.dispatchExpression(tokens)) {
            is Outcome.Ok -> Outcome.Ok(Option.Some(result.value))
            is Outcome.Error -> Outcome.Error(result.error)
        }
    }

    private fun buildNode(
        println: Token,
        lparen: Token,
        inner: Option<GrammarMatch>,
        rparen: Token,
        semicolon: Token,
        tokens: List<Token>,
    ): Node {
        val children = buildList {
            add(
                Node.Leaf(
                    PrintlnKeywordNode,
                    StringValue(println.lexeme),
                    println.span,
                    println.leading,
                    println.trailing,
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

            when (inner) {
                is Option.Some -> add(inner.value.node)
                is Option.None -> {}
            }

            add(
                Node.Leaf(
                    RightParenthesisNode,
                    StringValue(rparen.lexeme),
                    rparen.span,
                    rparen.leading,
                    rparen.trailing,
                ),
            )
            add(
                Node.Leaf(
                    SemicolonNode,
                    StringValue(semicolon.lexeme),
                    semicolon.span,
                    semicolon.leading,
                    semicolon.trailing,
                ),
            )
        }

        return Node.Composite(
            children = children,
            type = type,
            span = Span(tokens.first().span.start, semicolon.span.end),
        )
    }
}
