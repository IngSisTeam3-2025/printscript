package linter.internal.visitor.factory

import linter.internal.model.rule.MandatoryIdentifierOrLiteralInPrintlnRule
import linter.internal.table.VisitorFactory
import linter.internal.visitor.MandatoryVariableOrLiteralInPrintlnVisitor
import model.rule.BooleanRuleValue
import model.rule.Rule
import model.rule.RuleType
import model.visitor.Visitor

internal class MandatoryIdentifierOrLiteralInPrintlnRuleFactory : VisitorFactory {
    override val ruleType: RuleType = MandatoryIdentifierOrLiteralInPrintlnRule

    override fun create(rule: Rule): Visitor {
        val enforce = (rule.value as BooleanRuleValue).value
        return MandatoryVariableOrLiteralInPrintlnVisitor(enforce)
    }
}
