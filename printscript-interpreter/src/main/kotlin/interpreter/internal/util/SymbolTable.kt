package interpreter.internal.util

import model.value.Value
import type.option.Option

internal interface SymbolTable {
    fun contains(key: String): Boolean
    fun get(key: String): Option<Value>
    fun set(key: String, value: Value): SymbolTable
}
