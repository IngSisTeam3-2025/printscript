package formatter.internal.visitor.factory

import formatter.internal.rule.LineBreaksAfterPrintlnRule
import formatter.internal.visitor.LineBreaksAfterPrintlnVisitor
import model.rule.IntegerRuleValue
import model.rule.Rule
import model.rule.RuleType
import model.visitor.context.ContextVisitor

internal class LineBreaksAfterPrintlnVisitorFactory : ContextVisitorFactory {
    override val ruleType: RuleType = LineBreaksAfterPrintlnRule

    override fun create(rule: Rule): ContextVisitor {
        val lineBreaks = (rule.value as IntegerRuleValue).value
        return LineBreaksAfterPrintlnVisitor(lineBreaks)
    }
}
