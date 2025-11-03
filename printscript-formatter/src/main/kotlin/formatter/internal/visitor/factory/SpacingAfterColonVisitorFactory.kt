package formatter.internal.visitor.factory

import formatter.internal.rule.SpacingAfterColonRule
import formatter.internal.visitor.SpacingAfterColonVisitor
import model.rule.BooleanRuleValue
import model.rule.Rule
import model.rule.RuleType
import model.visitor.context.ContextVisitor

internal class SpacingAfterColonVisitorFactory : ContextVisitorFactory {
    override val ruleType: RuleType = SpacingAfterColonRule

    override fun create(rule: Rule): ContextVisitor {
        val enforce = (rule.value as BooleanRuleValue).value
        return SpacingAfterColonVisitor(enforce)
    }
}
