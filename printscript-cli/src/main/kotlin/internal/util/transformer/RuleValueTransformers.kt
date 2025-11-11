package internal.util.transformer

import model.rule.BooleanRuleValue
import model.rule.IntegerRuleValue
import model.rule.StringRuleValue

class IntegerTransformer : RuleValueTransformer {
    override fun supports(value: Any) = value is Int
    override fun transform(value: Any) = IntegerRuleValue(value as Int)
}

class BooleanTransformer : RuleValueTransformer {
    override fun supports(value: Any) = value is Boolean
    override fun transform(value: Any) = BooleanRuleValue(value as Boolean)
}

class StringTransformer : RuleValueTransformer {
    override fun supports(value: Any) = value is String
    override fun transform(value: Any) = StringRuleValue(value as String)
}
