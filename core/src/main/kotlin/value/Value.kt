package value

sealed interface Value {
    override fun toString(): String
}

data class IntegerValue(val value: Int) : Value {
    override fun toString(): String = value.toString()
}

data class DecimalValue(val value: Float) : Value {
    override fun toString(): String = value.toString()
}

data class BooleanValue(val value: Boolean) : Value {
    override fun toString(): String = value.toString()
}

data class StringValue(val value: String) : Value {
    override fun toString(): String = "\"$value\""
}
