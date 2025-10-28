package rulekit.transformer

import model.rule.RuleValue

interface RuleValueTransformer {
    fun canTransform(value: Any): Boolean
    fun transform(value: Any): RuleValue
}
