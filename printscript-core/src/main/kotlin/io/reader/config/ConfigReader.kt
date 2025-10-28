package io.reader.config

import model.rule.Rule

interface ConfigReader {
    fun read(): Collection<Rule>
}
