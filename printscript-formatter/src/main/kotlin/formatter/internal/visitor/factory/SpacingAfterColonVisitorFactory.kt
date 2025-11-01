package formatter.internal.visitor.factory

import formatter.internal.rule.SpacingAfterColonRule
import formatter.internal.visitor.SpacingAfterColonVisitor
import model.rule.BooleanRuleValue
import model.rule.Rule
import model.rule.RuleType
import model.visitor.Visitor

internal class SpacingAfterColonVisitorFactory : VisitorFactory {
    override val ruleType: RuleType = SpacingAfterColonRule

    override fun create(rule: Rule): Visitor {
        val enforce = (rule.value as BooleanRuleValue).value
        return SpacingAfterColonVisitor(enforce)
    }
}
