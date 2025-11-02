package linter.internal.visitor.factory

import linter.internal.model.rule.IdentifierFormatRule
import linter.internal.table.VisitorFactory
import linter.internal.visitor.IdentifierFormatVisitor
import model.rule.Rule
import model.rule.RuleType
import model.rule.StringRuleValue
import model.visitor.Visitor

internal class IdentifierFormatVisitorFactory : VisitorFactory {
    override val ruleType: RuleType = IdentifierFormatRule

    private val formatPatterns = mapOf(
        "snake case" to Regex("^[a-z]+(?:_[a-z0-9]+)*$"),
        "camel case" to Regex("^[a-z][a-zA-Z0-9]*$"),
    )

    override fun create(rule: Rule): Visitor {
        val format = (rule.value as StringRuleValue).value.lowercase()
        val regex = formatPatterns[format] ?: Regex(".*")
        return IdentifierFormatVisitor(format, regex)
    }
}
