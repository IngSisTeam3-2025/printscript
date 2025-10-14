package rulekit.transformer

import model.rule.BoolRuleValue
import model.rule.IntRuleValue
import model.rule.StringRuleValue

class IntTransformer : RuleValueTransformer {
    override fun canTransform(value: Any) = value is Int
    override fun transform(value: Any) = IntRuleValue(value as Int)
}

class BoolTransformer : RuleValueTransformer {
    override fun canTransform(value: Any) = value is Boolean
    override fun transform(value: Any) = BoolRuleValue(value as Boolean)
}

class StringTransformer : RuleValueTransformer {
    override fun canTransform(value: Any) = value is String
    override fun transform(value: Any) = StringRuleValue(value as String)
}
