package formatter.internal.visitor.factory

import formatter.internal.rule.LineBreakAfterStatementRule
import formatter.internal.visitor.LineBreakAfterStatementVisitor
import model.rule.BooleanRuleValue
import model.rule.Rule
import model.rule.RuleType
import model.visitor.context.ContextVisitor

internal class LineBreakAfterStatementVisitorFactory : ContextVisitorFactory {
    override val ruleType: RuleType = LineBreakAfterStatementRule

    override fun create(rule: Rule): ContextVisitor {
        val enforce = (rule.value as BooleanRuleValue).value
        return LineBreakAfterStatementVisitor(enforce)
    }
}
