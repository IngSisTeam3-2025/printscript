package model.value

import model.value.type.ValueType

interface Value {
    val type: ValueType
    fun format(): String
}
