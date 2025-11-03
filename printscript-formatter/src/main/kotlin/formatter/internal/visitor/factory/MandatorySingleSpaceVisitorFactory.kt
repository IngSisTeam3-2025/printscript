package formatter.internal.visitor.factory

import formatter.internal.rule.MandatorySingleSpaceRule
import formatter.internal.visitor.MandatorySingleSpaceVisitor
import model.rule.BooleanRuleValue
import model.rule.Rule
import model.rule.RuleType
import model.visitor.context.ContextVisitor

internal class MandatorySingleSpaceVisitorFactory : ContextVisitorFactory {
    override val ruleType: RuleType = MandatorySingleSpaceRule

    override fun create(rule: Rule): ContextVisitor {
        val enforce = (rule.value as BooleanRuleValue).value
        return MandatorySingleSpaceVisitor(enforce)
    }
}
