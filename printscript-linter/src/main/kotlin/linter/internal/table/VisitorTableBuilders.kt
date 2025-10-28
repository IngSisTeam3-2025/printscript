package linter.internal.table

internal object PrintScriptV10 : VisitorTableBuilder {
    override val factories: Map<String, VisitorFactory> = mapOf()
}

internal object PrintScriptV11 : VisitorTableBuilder {
    override val factories: Map<String, VisitorFactory> = PrintScriptV10.factories + mapOf()
}
