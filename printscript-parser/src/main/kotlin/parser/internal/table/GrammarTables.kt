package parser.internal.table

import model.node.BooleanTypeNode
import model.node.DivideNode
import model.node.MinusNode
import model.node.MultiplyNode
import model.node.NumberTypeNode
import model.node.PlusNode
import model.node.StringTypeNode
import model.token.BooleanTypeToken
import model.token.DivideToken
import model.token.MinusToken
import model.token.MultiplyToken
import model.token.NumberTypeToken
import model.token.PlusToken
import model.token.StringTypeToken
import parser.internal.model.grammar.expression.BinaryOperationExpression
import parser.internal.model.grammar.expression.BlockExpression
import parser.internal.model.grammar.expression.Expression
import parser.internal.model.grammar.expression.ReadEnvPrimary
import parser.internal.model.grammar.expression.ReadInputPrimary
import parser.internal.model.grammar.primary.BooleanLiteralPrimary
import parser.internal.model.grammar.primary.IdentifierPrimary
import parser.internal.model.grammar.primary.NumberLiteralPrimary
import parser.internal.model.grammar.primary.ParenthesizedPrimary
import parser.internal.model.grammar.primary.Primary
import parser.internal.model.grammar.primary.StringLiteralPrimary
import parser.internal.model.grammar.primary.UnaryOperationPrimary
import parser.internal.model.grammar.statement.AssignStatement
import parser.internal.model.grammar.statement.ConstDeclarationStatement
import parser.internal.model.grammar.statement.IfStatement
import parser.internal.model.grammar.statement.LetDeclarationStatement
import parser.internal.model.grammar.statement.PrintlnStatement
import parser.internal.model.grammar.statement.Statement
import parser.internal.model.operator.BinaryOperator

internal object PrintScriptV10 : GrammarTable {
    override val statements: Collection<Statement> = listOf(
        AssignStatement(),
        LetDeclarationStatement(
            mapOf(
                NumberTypeToken to NumberTypeNode,
                StringTypeToken to StringTypeNode,
            ),
        ),
        PrintlnStatement(),
    )

    override val primaries: Collection<Primary> = listOf(
        IdentifierPrimary(),
        NumberLiteralPrimary(),
        ParenthesizedPrimary(),
        StringLiteralPrimary(),
        UnaryOperationPrimary(
            mapOf(
                MinusToken to MinusNode,
                PlusToken to PlusNode,
            ),
        ),
    )

    override val expressions: Collection<Expression> = listOf(
        BinaryOperationExpression(
            mapOf(
                DivideToken to BinaryOperator(DivideNode, 2),
                MinusToken to BinaryOperator(MinusNode, 1),
                MultiplyToken to BinaryOperator(MultiplyNode, 2),
                PlusToken to BinaryOperator(PlusNode, 1),
            ),
        ),
    )
}

internal object PrintScriptV11 : GrammarTable {
    override val statements: Collection<Statement> = listOf(
        AssignStatement(),
        ConstDeclarationStatement(
            mapOf(
                BooleanTypeToken to BooleanTypeNode,
                NumberTypeToken to NumberTypeNode,
                StringTypeToken to StringTypeNode,
            ),
        ),
        IfStatement(),
        LetDeclarationStatement(
            mapOf(
                BooleanTypeToken to BooleanTypeNode,
                NumberTypeToken to NumberTypeNode,
                StringTypeToken to StringTypeNode,
            ),
        ),
        PrintlnStatement(),
    )

    override val primaries: Collection<Primary> = PrintScriptV10.primaries + listOf(
        BooleanLiteralPrimary(),
        ReadEnvPrimary(),
        ReadInputPrimary(),
    )

    override val expressions: Collection<Expression> = PrintScriptV10.expressions + listOf(
        BlockExpression(),
    )
}
