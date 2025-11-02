package linter.internal.visitor.factory

import linter.internal.model.rule.MandatoryIdentifierOrLiteralInReadInputRule
import linter.internal.table.VisitorFactory
import linter.internal.visitor.MandatoryVariableOrLiteralInReadInputVisitor
import model.rule.BooleanRuleValue
import model.rule.Rule
import model.rule.RuleType
import model.visitor.Visitor

internal class MandatoryIdentifierOrLiteralInReadInputVisitorFactory : VisitorFactory {
    override val ruleType: RuleType = MandatoryIdentifierOrLiteralInReadInputRule

    override fun create(rule: Rule): Visitor {
        val enforce = (rule.value as BooleanRuleValue).value
        return MandatoryVariableOrLiteralInReadInputVisitor(enforce)
    }
}
