package util.registry

import model.rule.RuleValue
import util.transformer.RuleValueTransformer

object RuleValueRegistry {
    fun transform(
        value: Any,
        transformers: Collection<RuleValueTransformer>,
    ): RuleValue {
        val transformer = transformers.first { it.supports(value) }
        return transformer.transform(value)
    }
}
