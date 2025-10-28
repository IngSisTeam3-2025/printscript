package validator.internal.util

import type.option.Option
import validator.internal.model.symbol.StaticSymbol

internal interface StaticSymbolTable {
    fun contains(key: String): Boolean
    fun get(key: String): Option<StaticSymbol>
    fun set(key: String, info: StaticSymbol): StaticSymbolTable
}
