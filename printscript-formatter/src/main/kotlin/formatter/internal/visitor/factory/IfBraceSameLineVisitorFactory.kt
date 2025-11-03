package formatter.internal.visitor.factory

import formatter.internal.rule.IfBraceSameLineRule
import formatter.internal.visitor.IfBraceSameLineVisitor
import model.rule.BooleanRuleValue
import model.rule.Rule
import model.rule.RuleType
import model.visitor.context.ContextVisitor

internal class IfBraceSameLineVisitorFactory : ContextVisitorFactory {
    override val ruleType: RuleType = IfBraceSameLineRule

    override fun create(rule: Rule): ContextVisitor {
        val enforce = (rule.value as BooleanRuleValue).value
        return IfBraceSameLineVisitor(enforce)
    }
}
