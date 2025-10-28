package util.parser

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import model.rule.Rule
import util.registry.RuleValueRegistry
import util.transformer.RuleValueTransformer
import java.io.InputStream

internal class JsonRuleParser(
    private val transformers: Collection<RuleValueTransformer>,
) : RuleParser {

    override fun parse(source: InputStream): Collection<Rule> {
        val mapper = jacksonObjectMapper()
        val rawRules: Map<String, Any> = mapper.readValue(source)

        return rawRules.map { (signature, value) ->
            Rule(signature, RuleValueRegistry.transform(value, transformers))
        }
    }
}
