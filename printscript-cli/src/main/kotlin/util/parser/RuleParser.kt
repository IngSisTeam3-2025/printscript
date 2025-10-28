package util.parser

import model.rule.Rule
import java.io.InputStream

interface RuleParser {
    fun parse(source: InputStream): Collection<Rule>
}
