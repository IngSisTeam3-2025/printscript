package model.value.operation

import model.value.Value
import model.value.type.ValueType

interface UnaryValueOperation {
    val symbol: String
    val resultType: ValueType
    fun supports(type: ValueType): Boolean
    fun apply(operand: Value): OperationResult
}
