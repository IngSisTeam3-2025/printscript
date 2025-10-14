package rulekit.parser

import java.io.InputStream

import model.rule.Rule
import rulekit.registry.RuleValueRegistry

interface RuleParser {
    fun parse(stream: InputStream, registry: RuleValueRegistry): Collection<Rule>
}
