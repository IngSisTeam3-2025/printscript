package internal.util.transformer

import model.rule.RuleValue

interface RuleValueTransformer {
    fun supports(value: Any): Boolean
    fun transform(value: Any): RuleValue
}
