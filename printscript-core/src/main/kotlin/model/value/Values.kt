package model.value

import model.value.type.BooleanValueType
import model.value.type.NoneValueType
import model.value.type.NumberValueType
import model.value.type.StringValueType

data class BooleanValue(val value: Boolean) : Value {
    override val type = BooleanValueType
    override fun format(): String = value.toString()
}

data class FloatValue(val value: Float) : Value {
    override val type = NumberValueType
    override fun format(): String = value.toString()
}

data class IntegerValue(val value: Int) : Value {
    override val type = NumberValueType
    override fun format(): String = value.toString()
}

data object NoneValue : Value {
    override val type = NoneValueType
    override fun format(): String = "none"
}

data class StringValue(val value: String) : Value {
    override val type = StringValueType
    override fun format(): String = value
}
