package formatter.internal.visitor.factory

import formatter.internal.rule.SpacingAroundEqualsRule
import formatter.internal.visitor.SpacingAroundEqualsVisitor
import model.rule.BooleanRuleValue
import model.rule.Rule
import model.rule.RuleType
import model.visitor.context.ContextVisitor

internal class SpacingAroundEqualsVisitorFactory : ContextVisitorFactory {
    override val ruleType: RuleType = SpacingAroundEqualsRule

    override fun create(rule: Rule): ContextVisitor {
        val enforce = (rule.value as BooleanRuleValue).value
        return SpacingAroundEqualsVisitor(enforce)
    }
}
