package formatter.internal.table

import formatter.internal.rule.NoSpacingAroundEqualsRule
import formatter.internal.rule.SpacingAroundEqualsRule
import formatter.internal.visitor.factory.NoSpacingAroundEqualsVisitorFactory
import formatter.internal.visitor.factory.SpacingAroundEqualsVisitorFactory
import formatter.internal.visitor.factory.VisitorFactory

internal object PrintScriptV10 : VisitorTableBuilder {
    override val factories: Map<String, VisitorFactory> = mapOf(
        NoSpacingAroundEqualsRule.signature to NoSpacingAroundEqualsVisitorFactory(),
        SpacingAroundEqualsRule.signature to SpacingAroundEqualsVisitorFactory(),
    )
}

internal object PrintScriptV11 : VisitorTableBuilder {
    override val factories: Map<String, VisitorFactory> = PrintScriptV10.factories + mapOf()
}
