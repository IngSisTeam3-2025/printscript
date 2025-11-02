package internal.util.parser

import model.diagnostic.Diagnostic
import model.rule.Rule
import type.outcome.Outcome
import java.io.InputStream

interface RuleParser {
    fun parse(source: InputStream): Outcome<Collection<Rule>, Diagnostic>
}
