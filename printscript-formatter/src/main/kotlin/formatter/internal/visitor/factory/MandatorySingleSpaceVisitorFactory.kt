package formatter.internal.visitor.factory

import formatter.internal.rule.MandatorySingleSpaceRule
import formatter.internal.visitor.MandatorySingleSpaceVisitor
import model.rule.BooleanRuleValue
import model.rule.Rule
import model.rule.RuleType
import model.visitor.Visitor

internal class MandatorySingleSpaceVisitorFactory : VisitorFactory {
    override val ruleType: RuleType = MandatorySingleSpaceRule

    override fun create(rule: Rule): Visitor {
        val enforce = (rule.value as BooleanRuleValue).value
        return MandatorySingleSpaceVisitor(enforce)
    }
}
