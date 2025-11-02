package formatter.internal.visitor.factory

import formatter.internal.rule.LineBreaksAfterPrintlnRule
import formatter.internal.visitor.LineBreaksAfterPrintlnVisitor
import model.rule.IntRuleValue
import model.rule.Rule
import model.rule.RuleType
import model.visitor.Visitor

internal class LineBreaksAfterPrintlnVisitorFactory : VisitorFactory {
    override val ruleType: RuleType = LineBreaksAfterPrintlnRule

    override fun create(rule: Rule): Visitor {
        val lineBreaks = (rule.value as IntRuleValue).value
        return LineBreaksAfterPrintlnVisitor(lineBreaks)
    }
}
