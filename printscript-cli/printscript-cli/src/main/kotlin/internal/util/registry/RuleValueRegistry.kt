package internal.util.registry

import internal.util.transformer.RuleValueTransformer
import model.rule.RuleValue

object RuleValueRegistry {
    fun transform(
        value: Any,
        transformers: Collection<RuleValueTransformer>,
    ): RuleValue {
        val transformer = transformers.first { it.supports(value) }
        return transformer.transform(value)
    }
}
