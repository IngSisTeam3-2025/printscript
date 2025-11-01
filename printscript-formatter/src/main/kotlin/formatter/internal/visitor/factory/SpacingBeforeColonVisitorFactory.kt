package formatter.internal.visitor.factory

import formatter.internal.rule.SpacingBeforeColonRule
import formatter.internal.visitor.SpacingBeforeColonVisitor
import model.rule.BooleanRuleValue
import model.rule.Rule
import model.rule.RuleType
import model.visitor.Visitor

internal class SpacingBeforeColonVisitorFactory : VisitorFactory {
    override val ruleType: RuleType = SpacingBeforeColonRule

    override fun create(rule: Rule): Visitor {
        val enforce = (rule.value as BooleanRuleValue).value
        return SpacingBeforeColonVisitor(enforce)
    }
}
