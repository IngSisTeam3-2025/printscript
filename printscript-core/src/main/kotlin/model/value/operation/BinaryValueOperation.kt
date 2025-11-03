package model.value.operation

import model.value.Value
import model.value.type.ValueType

interface BinaryValueOperation {
    val symbol: String
    val resultType: ValueType
    fun supports(lhs: ValueType, rhs: ValueType): Boolean
    fun apply(lhs: Value, rhs: Value): OperationResult
}
