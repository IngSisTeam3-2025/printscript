package io.reader.config

import model.diagnostic.Diagnostic
import model.rule.Rule
import type.outcome.Outcome

interface ConfigReader {
    fun read(): Outcome<Collection<Rule>, Diagnostic>
}
