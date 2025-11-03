package interpreter.internal.table

import interpreter.internal.visitor.AssignmentVisitor
import interpreter.internal.visitor.BinaryOperationVisitor
import interpreter.internal.visitor.BlockVisitor
import interpreter.internal.visitor.BooleanLiteralVisitor
import interpreter.internal.visitor.ConstDeclarationVisitor
import interpreter.internal.visitor.IdentifierVisitor
import interpreter.internal.visitor.IfStatementVisitor
import interpreter.internal.visitor.LetDeclarationVisitor
import interpreter.internal.visitor.NumberLiteralVisitor
import interpreter.internal.visitor.ParenthesizedExpressionVisitor
import interpreter.internal.visitor.PrintlnVisitor
import interpreter.internal.visitor.ReadEnvVisitor
import interpreter.internal.visitor.ReadInputVisitor
import interpreter.internal.visitor.StringLiteralVisitor
import interpreter.internal.visitor.UnaryOperationVisitor
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
import model.value.transformer.BooleanValueTransformer
import model.value.transformer.FloatValueTransformer
import model.value.transformer.IntegerValueTransformer
import model.value.transformer.StringValueTransformer
import model.value.type.BooleanValueType
import model.value.type.NumberValueType
import model.value.type.StringValueType
import model.visitor.context.ContextVisitor
import model.visitor.context.ContextVisitorTable

internal object PrintScriptV10 : ContextVisitorTable {

    override val visitors: Collection<ContextVisitor> = listOf(
        AssignmentVisitor(),
        BinaryOperationVisitor(
            mapOf(
                PlusNode to listOf(
                    AddIntegerOperation,
                    AddFloatOperation,
                    AddMixedNumericOperation,
                    AddStringOperation,
                    AddStringCoercionOperation,
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
                DivideNode to listOf(
                    DivideIntegerOperation,
                    DivideFloatOperation,
                    DivideMixedNumericOperation,
                ),
            ),
        ),
        IdentifierVisitor(),
        LetDeclarationVisitor(
            mapOf(
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
                PlusNode to listOf(PositiveIntegerOperation, PositiveFloatOperation),
                MinusNode to listOf(NegateIntegerOperation, NegateFloatOperation),
            ),
        ),
    )
}

internal object PrintScriptV11 : ContextVisitorTable {

    override val visitors: Collection<ContextVisitor> = listOf(
        AssignmentVisitor(),
        BinaryOperationVisitor(
            mapOf(
                PlusNode to listOf(
                    AddIntegerOperation,
                    AddFloatOperation,
                    AddMixedNumericOperation,
                    AddStringOperation,
                    AddStringCoercionOperation,
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
                DivideNode to listOf(
                    DivideIntegerOperation,
                    DivideFloatOperation,
                    DivideMixedNumericOperation,
                ),
            ),
        ),
        BlockVisitor(),
        BooleanLiteralVisitor(),
        ConstDeclarationVisitor(
            mapOf(
                BooleanTypeNode to BooleanValueType,
                NumberTypeNode to NumberValueType,
                StringTypeNode to StringValueType,
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
        ReadEnvVisitor(),
        ReadInputVisitor(
            listOf(
                IntegerValueTransformer,
                FloatValueTransformer,
                BooleanValueTransformer,
                StringValueTransformer,
            ),
        ),
        StringLiteralVisitor(),
        UnaryOperationVisitor(
            mapOf(
                PlusNode to listOf(PositiveIntegerOperation, PositiveFloatOperation),
                MinusNode to listOf(NegateIntegerOperation, NegateFloatOperation),
            ),
        ),
    )
}
