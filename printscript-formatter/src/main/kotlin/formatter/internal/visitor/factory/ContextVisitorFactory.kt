package formatter.internal.visitor.factory

import model.rule.Rule
import model.rule.RuleType
import model.visitor.context.ContextVisitor

internal interface ContextVisitorFactory {
    val ruleType: RuleType
    fun create(rule: Rule): ContextVisitor
}
