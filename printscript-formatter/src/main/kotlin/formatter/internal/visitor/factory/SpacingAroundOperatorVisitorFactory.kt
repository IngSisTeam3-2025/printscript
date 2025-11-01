package formatter.internal.visitor.factory

import formatter.internal.rule.SpacingAroundOperatorRule
import formatter.internal.visitor.SpacingAroundOperatorVisitor
import model.node.NodeType
import model.rule.BooleanRuleValue
import model.rule.Rule
import model.rule.RuleType
import model.visitor.Visitor

internal class SpacingAroundOperatorVisitorFactory(
    private val operators: Collection<NodeType>,
) : VisitorFactory {
    override val ruleType: RuleType = SpacingAroundOperatorRule

    override fun create(rule: Rule): Visitor {
        val enforce = (rule.value as BooleanRuleValue).value
        return SpacingAroundOperatorVisitor(enforce, operators)
    }
}
