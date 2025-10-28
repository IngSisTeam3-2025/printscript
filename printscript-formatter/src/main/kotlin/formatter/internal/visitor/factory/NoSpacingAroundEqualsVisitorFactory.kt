package formatter.internal.visitor.factory

import formatter.internal.rule.NoSpacingAroundEqualsRule
import formatter.internal.visitor.NoSpacingAroundEqualsVisitor
import model.rule.BoolRuleValue
import model.rule.Rule
import model.rule.RuleType
import model.visitor.Visitor

internal class NoSpacingAroundEqualsVisitorFactory : VisitorFactory {
    override val ruleType: RuleType = NoSpacingAroundEqualsRule

    override fun create(rule: Rule): Visitor {
        val enforce = (rule.value as BoolRuleValue).value
        return NoSpacingAroundEqualsVisitor(enforce)
    }
}
