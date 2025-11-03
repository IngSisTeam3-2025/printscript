package formatter.internal.visitor.factory

import formatter.internal.rule.NoSpacingAroundEqualsRule
import formatter.internal.visitor.NoSpacingAroundEqualsVisitor
import model.rule.BooleanRuleValue
import model.rule.Rule
import model.rule.RuleType
import model.visitor.context.ContextVisitor

internal class NoSpacingAroundEqualsVisitorFactory : ContextVisitorFactory {
    override val ruleType: RuleType = NoSpacingAroundEqualsRule

    override fun create(rule: Rule): ContextVisitor {
        val enforce = (rule.value as BooleanRuleValue).value
        return NoSpacingAroundEqualsVisitor(enforce)
    }
}
