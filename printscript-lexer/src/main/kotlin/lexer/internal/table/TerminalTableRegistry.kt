package lexer.internal.table

import type.option.Option

internal object TerminalTableRegistry {
    private val tables: Map<String, Lazy<TerminalTable>> = mapOf(
        "1.0" to lazy { PrintScriptV10 },
        "1.1" to lazy { PrintScriptV11 },
    )

    fun get(version: String): Option<TerminalTable> {
        val lazy = tables[version.lowercase()]
        return if (lazy != null) {
            Option.Some(lazy.value)
        } else {
            Option.None
        }
    }
}
