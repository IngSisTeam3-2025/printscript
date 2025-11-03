package parser.internal.model.grammar.statement

import model.node.AssignNode
import model.node.ColonNode
import model.node.ConstDeclarationStatementNode
import model.node.ConstNode
import model.node.IdentifierNode
import model.node.Node
import model.node.NodeType
import model.node.SemicolonNode
import model.span.Span
import model.token.AssignToken
import model.token.ColonToken
import model.token.ConstToken
import model.token.IdentifierToken
import model.token.SemicolonToken
import model.token.Token
import model.token.TokenType
import model.value.StringValue
import parser.internal.model.category.InvalidTypeDeclaration
import parser.internal.model.category.MissingColon
import parser.internal.model.category.MissingConstDeclaration
import parser.internal.model.category.MissingEndOfLine
import parser.internal.model.category.MissingIdentifier
import parser.internal.model.category.MissingTypeDeclaration
import parser.internal.model.grammar.GrammarFail
import parser.internal.model.grammar.GrammarMatch
import parser.internal.table.GrammarTable
import type.option.Option
import type.outcome.Outcome
import type.outcome.getOrElse

internal class ConstDeclarationStatement(
    private val typeMapping: Map<TokenType, NodeType>,
) : Statement {
    override val type: NodeType = ConstDeclarationStatementNode

    override fun match(
        tokens: List<Token>,
        table: GrammarTable,
    ): Outcome<GrammarMatch, GrammarFail> {
        var consumed = 0

        val const = tokens.getOrElse(consumed) {
            return Outcome.Error(
                GrammarFail(
                    "Expected 'const'",
                    MissingConstDeclaration,
                    consumed,
                ),
            )
        }
        if (const.type != ConstToken) {
            return Outcome.Error(
                GrammarFail(
                    "Expected 'const', got '${const.lexeme}'",
                    MissingConstDeclaration,
                    consumed,
                ),
            )
        }
        consumed += 1

        val identifier = tokens.getOrElse(consumed) {
            return Outcome.Error(
                GrammarFail(
                    "Expected identifier",
                    MissingIdentifier,
                    consumed,
                ),
            )
        }
        if (identifier.type != IdentifierToken) {
            return Outcome.Error(
                GrammarFail(
                    "Expected identifier, got '${identifier.lexeme}'",
                    MissingIdentifier,
                    consumed,
                ),
            )
        }
        consumed += 1

        val colon = tokens.getOrElse(consumed) {
            return Outcome.Error(
                GrammarFail(
                    "Expected ':'",
                    MissingColon,
                    consumed,
                ),
            )
        }
        if (colon.type != ColonToken) {
            return Outcome.Error(
                GrammarFail(
                    "Expected ':', got '${colon.lexeme}'",
                    MissingColon,
                    consumed,
                ),
            )
        }
        consumed += 1

        val typeToken = tokens.getOrElse(consumed) {
            return Outcome.Error(
                GrammarFail(
                    "Expected type declaration",
                    MissingTypeDeclaration,
                    consumed,
                ),
            )
        }
        val typeNodeType = typeMapping[typeToken.type] ?: return Outcome.Error(
            GrammarFail(
                "Invalid type '${typeToken.lexeme}'",
                InvalidTypeDeclaration,
                consumed,
            ),
        )
        consumed += 1

        val assignmentResult = parseOptionalAssignment(tokens.drop(consumed), table)
        if (assignmentResult is Outcome.Error) {
            return Outcome.Error(
                GrammarFail(
                    assignmentResult.error.message,
                    assignmentResult.error.category,
                    consumed + assignmentResult.error.consumed,
                ),
            )
        }
        val assignment = (assignmentResult as Outcome.Ok).value
        if (assignment is Option.Some) {
            consumed += assignment.value.consumed
        }

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
            return Outcome.Error(
                GrammarFail(
                    "Expected ';', got '${semicolon.lexeme}'",
                    MissingEndOfLine,
                    consumed,
                ),
            )
        }
        consumed += 1

        val node = buildNode(
            const,
            identifier,
            colon,
            typeToken,
            typeNodeType,
            assignment,
            semicolon,
            tokens,
        )
        return Outcome.Ok(GrammarMatch(node, consumed))
    }

    private fun parseOptionalAssignment(
        tokens: List<Token>,
        table: GrammarTable,
    ): Outcome<Option<GrammarMatch>, GrammarFail> {
        if (tokens.isEmpty() || tokens[0].type != AssignToken) {
            return Outcome.Ok(Option.None)
        }
        val expr = table.dispatchExpression(tokens.drop(1)).getOrElse {
            return Outcome.Error(
                GrammarFail(
                    it.message,
                    it.category,
                    1 + it.consumed,
                ),
            )
        }
        return Outcome.Ok(Option.Some(GrammarMatch(expr.node, 1 + expr.consumed)))
    }

    private fun buildNode(
        const: Token,
        identifier: Token,
        colon: Token,
        typeToken: Token,
        typeNodeType: NodeType,
        assignment: Option<GrammarMatch>,
        semicolon: Token,
        tokens: List<Token>,
    ): Node {
        val children = buildList {
            add(
                Node.Leaf(
                    ConstNode,
                    StringValue(const.lexeme),
                    const.span,
                    const.leading,
                    const.trailing,
                ),
            )
            add(
                Node.Leaf(
                    IdentifierNode,
                    StringValue(identifier.lexeme),
                    identifier.span,
                    identifier.leading,
                    identifier.trailing,
                ),
            )
            add(
                Node.Leaf(
                    ColonNode,
                    StringValue(colon.lexeme),
                    colon.span,
                    colon.leading,
                    colon.trailing,
                ),
            )
            add(
                Node.Leaf(
                    typeNodeType,
                    StringValue(typeToken.lexeme),
                    typeToken.span,
                    typeToken.leading,
                    typeToken.trailing,
                ),
            )

            when (assignment) {
                is Option.Some -> {
                    val assignToken = tokens.dropWhile { it.type != AssignToken }.first()
                    add(
                        Node.Leaf(
                            AssignNode,
                            StringValue(assignToken.lexeme),
                            assignToken.span,
                            assignToken.leading,
                            assignToken.trailing,
                        ),
                    )
                    add(assignment.value.node)
                }
                is Option.None -> {}
            }

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
