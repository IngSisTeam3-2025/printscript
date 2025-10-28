package validator.internal.table

import model.node.BooleanTypeNode
import model.node.DivideNode
import model.node.MinusNode
import model.node.MultiplyNode
import model.node.NumberTypeNode
import model.node.PlusNode
import model.node.StringTypeNode
import model.value.operation.AddFloatOperation
import model.value.operation.AddIntegerOperation
import model.value.operation.AddMixedNumericOperation
import model.value.operation.AddStringCoercionOperation
import model.value.operation.AddStringOperation
import model.value.operation.DivideFloatOperation
import model.value.operation.DivideIntegerOperation
import model.value.operation.DivideMixedNumericOperation
import model.value.operation.MultiplyFloatOperation
import model.value.operation.MultiplyIntegerOperation
import model.value.operation.MultiplyMixedNumericOperation
import model.value.operation.NegateFloatOperation
import model.value.operation.NegateIntegerOperation
import model.value.operation.PositiveFloatOperation
import model.value.operation.PositiveIntegerOperation
import model.value.operation.SubtractFloatOperation
import model.value.operation.SubtractIntegerOperation
import model.value.operation.SubtractMixedNumericOperation
import model.value.type.BooleanValueType
import model.value.type.NumberValueType
import model.value.type.StringValueType
import model.visitor.context.ContextVisitor
import model.visitor.context.ContextVisitorTable
import validator.internal.visitor.AssignmentVisitor
import validator.internal.visitor.BinaryOperationVisitor
import validator.internal.visitor.BlockVisitor
import validator.internal.visitor.BooleanLiteralVisitor
import validator.internal.visitor.ConstDeclarationVisitor
import validator.internal.visitor.IdentifierVisitor
import validator.internal.visitor.IfStatementVisitor
import validator.internal.visitor.LetDeclarationVisitor
import validator.internal.visitor.NumberLiteralVisitor
import validator.internal.visitor.ParenthesizedExpressionVisitor
import validator.internal.visitor.PrintlnVisitor
import validator.internal.visitor.ReadEnvVisitor
import validator.internal.visitor.ReadInputVisitor
import validator.internal.visitor.StringLiteralVisitor
import validator.internal.visitor.UnaryOperationVisitor

internal object PrintScriptV10 : ContextVisitorTable {
    override val visitors: Collection<ContextVisitor> = listOf(
        AssignmentVisitor(),
        BinaryOperationVisitor(
            mapOf(
                DivideNode to listOf(
                    DivideIntegerOperation,
                    DivideFloatOperation,
                    DivideMixedNumericOperation,
                ),
                MinusNode to listOf(
                    SubtractIntegerOperation,
                    SubtractFloatOperation,
                    SubtractMixedNumericOperation,
                ),
                MultiplyNode to listOf(
                    MultiplyIntegerOperation,
                    MultiplyFloatOperation,
                    MultiplyMixedNumericOperation,
                ),
                PlusNode to listOf(
                    AddIntegerOperation,
                    AddFloatOperation,
                    AddMixedNumericOperation,
                    AddStringOperation,
                    AddStringCoercionOperation,
                ),
            ),
        ),
        IdentifierVisitor(),
        IfStatementVisitor(),
        LetDeclarationVisitor(
            mapOf(
                BooleanTypeNode to BooleanValueType,
                NumberTypeNode to NumberValueType,
                StringTypeNode to StringValueType,
            ),
        ),
        NumberLiteralVisitor(),
        ParenthesizedExpressionVisitor(),
        PrintlnVisitor(),
        StringLiteralVisitor(),
        UnaryOperationVisitor(
            mapOf(
                MinusNode to listOf(
                    NegateIntegerOperation,
                    NegateFloatOperation,
                ),
                PlusNode to listOf(
                    PositiveIntegerOperation,
                    PositiveFloatOperation,
                ),
            ),
        ),
    )
}

internal object PrintScriptV11 : ContextVisitorTable {
    override val visitors: Collection<ContextVisitor> = PrintScriptV10.visitors + listOf(
        BlockVisitor(),
        BooleanLiteralVisitor(),
        ConstDeclarationVisitor(
            mapOf(
                BooleanTypeNode to BooleanValueType,
                NumberTypeNode to NumberValueType,
                StringTypeNode to StringValueType,
            ),
        ),
        ReadEnvVisitor(),
        ReadInputVisitor(),
    )
}
