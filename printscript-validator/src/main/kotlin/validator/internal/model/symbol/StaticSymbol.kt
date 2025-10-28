package validator.internal.model.symbol

import model.span.Span
import model.value.Value
import model.value.type.ValueType

internal data class StaticSymbol(
    val name: String,
    val value: Value,
    val declaredType: ValueType,
    val declaredAt: Span,
    val isMutable: Boolean,
)
