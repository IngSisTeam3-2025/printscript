package formatter.internal.visitor.factory

import formatter.internal.rule.SpacingAroundEqualsRule
import formatter.internal.visitor.SpacingAroundEqualsVisitor
import model.rule.BoolRuleValue
import model.rule.Rule
import model.rule.RuleType
import model.visitor.Visitor

internal class SpacingAroundEqualsVisitorFactory : VisitorFactory {
    override val ruleType: RuleType = SpacingAroundEqualsRule

    override fun create(rule: Rule): Visitor {
        val enforce = (rule.value as BoolRuleValue).value
        return SpacingAroundEqualsVisitor(enforce)
    }
}
