package interpreter.internal.util

import model.value.Value
import type.option.Option
import type.option.Option.None
import type.option.Option.Some

internal class DefaultSymbolTable private constructor(
    private val table: Map<String, Value>,
) : SymbolTable {

    constructor() : this(emptyMap())

    override fun contains(key: String): Boolean = table.containsKey(key)

    override fun get(key: String): Option<Value> = table[key]?.let { Some(it) } ?: None

    override fun set(key: String, value: Value): SymbolTable {
        return DefaultSymbolTable(table + (key to value))
    }
}
