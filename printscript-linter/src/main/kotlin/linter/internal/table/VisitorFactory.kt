package linter.internal.table

import model.rule.Rule
import model.rule.RuleType
import model.visitor.Visitor

internal interface VisitorFactory {
    val ruleType: RuleType
    fun create(rule: Rule): Visitor
}
