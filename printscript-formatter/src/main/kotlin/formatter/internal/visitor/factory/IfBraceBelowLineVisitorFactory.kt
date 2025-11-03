package formatter.internal.visitor.factory

import formatter.internal.rule.IfBraceBelowLineRule
import formatter.internal.visitor.IfBraceBelowLineVisitor
import model.rule.BooleanRuleValue
import model.rule.Rule
import model.rule.RuleType
import model.visitor.context.ContextVisitor

internal class IfBraceBelowLineVisitorFactory : ContextVisitorFactory {
    override val ruleType: RuleType = IfBraceBelowLineRule

    override fun create(rule: Rule): ContextVisitor {
        val enforce = (rule.value as BooleanRuleValue).value
        return IfBraceBelowLineVisitor(enforce)
    }
}
