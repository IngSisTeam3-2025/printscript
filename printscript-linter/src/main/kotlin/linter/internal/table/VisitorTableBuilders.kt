package linter.internal.table

import linter.internal.model.rule.IdentifierFormatRule
import linter.internal.model.rule.MandatoryIdentifierOrLiteralInPrintlnRule
import linter.internal.model.rule.MandatoryIdentifierOrLiteralInReadInputRule
import linter.internal.visitor.factory.IdentifierFormatVisitorFactory
import linter.internal.visitor.factory.MandatoryIdentifierOrLiteralInPrintlnRuleFactory
import linter.internal.visitor.factory.MandatoryIdentifierOrLiteralInReadInputVisitorFactory

internal object PrintScriptV10 : VisitorTableBuilder {
    override val factories: Map<String, VisitorFactory> = mapOf(
        IdentifierFormatRule.signature to IdentifierFormatVisitorFactory(),
        MandatoryIdentifierOrLiteralInPrintlnRule.signature to
            MandatoryIdentifierOrLiteralInPrintlnRuleFactory(),
    )
}

internal object PrintScriptV11 : VisitorTableBuilder {
    override val factories: Map<String, VisitorFactory> = PrintScriptV10.factories + mapOf(
        MandatoryIdentifierOrLiteralInReadInputRule.signature to
            MandatoryIdentifierOrLiteralInReadInputVisitorFactory(),
    )
}
