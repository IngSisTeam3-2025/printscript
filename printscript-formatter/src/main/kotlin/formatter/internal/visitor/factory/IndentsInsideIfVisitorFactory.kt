package formatter.internal.visitor.factory

import formatter.internal.rule.LineBreakAfterStatementRule
import formatter.internal.visitor.IndentsInsideIfVisitor
import model.rule.IntegerRuleValue
import model.rule.Rule
import model.rule.RuleType
import model.visitor.context.ContextVisitor

internal class IndentsInsideIfVisitorFactory : ContextVisitorFactory {
    override val ruleType: RuleType = LineBreakAfterStatementRule

    override fun create(rule: Rule): ContextVisitor {
        val indents = (rule.value as IntegerRuleValue).value
        return IndentsInsideIfVisitor(indents)
    }
}
