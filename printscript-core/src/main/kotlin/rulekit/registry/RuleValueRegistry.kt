package rulekit.registry

import model.rule.RuleValue
import rulekit.transformer.RuleValueTransformer

object RuleValueRegistry {
    fun transform(value: Any, transformers: Collection<RuleValueTransformer>): RuleValue {
        val transformer = transformers.first { it.canTransform(value) }
        return transformer.transform(value)
    }
}
