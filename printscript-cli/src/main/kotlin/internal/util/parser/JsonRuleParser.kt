package internal.util.parser

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import internal.model.ConfigurationError
import internal.util.registry.RuleValueRegistry
import internal.util.transformer.RuleValueTransformer
import model.diagnostic.Diagnostic
import model.rule.Rule
import type.outcome.Outcome
import java.io.InputStream

internal class JsonRuleParser(
    private val transformers: Collection<RuleValueTransformer>,
) : RuleParser {

    @Suppress("TooGenericExceptionCaught")
    override fun parse(source: InputStream): Outcome<Collection<Rule>, Diagnostic> {
        return try {
            val mapper = jacksonObjectMapper()
            val rawRules: Map<String, Any> = mapper.readValue(source)

            val rules = rawRules.map { (signature, value) ->
                Rule(signature, RuleValueRegistry.transform(value, transformers))
            }

            Outcome.Ok(rules)
        } catch (e: Exception) {
            val message = "${e.message}"
            Outcome.Error(ConfigurationError(message))
        }
    }
}
