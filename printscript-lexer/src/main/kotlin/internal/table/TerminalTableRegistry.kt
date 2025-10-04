package internal.table

import util.option.Option

internal object TerminalTableRegistry {
    private val tables: MutableMap<String, TerminalTable> = mutableMapOf()

    fun get(version: String): Option<TerminalTable> {
        val table = tables[version.lowercase()]
        return if (table != null) {
            Option.Some(table)
        } else {
            Option.None
        }
    }

    fun register(version: String, table: TerminalTable) {
        tables[version.lowercase()] = table
    }
}


