package validator.internal.model.value

import model.value.Value
import model.value.type.ValueType

internal class RuntimeValue(
    override val type: ValueType,
) : Value {
    override fun format(): String = ""
}
