package interpreter.runtime

sealed class RuntimeValue {
    object Void : RuntimeValue()
    data class Num(val v: Int) : RuntimeValue()
    data class Str(val v: String) : RuntimeValue()
}