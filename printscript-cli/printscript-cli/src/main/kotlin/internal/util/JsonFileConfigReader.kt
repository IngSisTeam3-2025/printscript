package internal.util

import internal.model.ConfigurationError
import internal.util.parser.RuleParser
import io.reader.config.ConfigReader
import model.diagnostic.Diagnostic
import model.rule.Rule
import type.outcome.Outcome
import java.io.File

class JsonFileConfigReader(
    private val filePath: String,
    private val parser: RuleParser,
) : ConfigReader {

    @Suppress("TooGenericExceptionCaught")
    private fun readSafe(): Outcome<Collection<Rule>, Diagnostic> {
        val file = File(filePath)

        if (!file.exists()) {
            val message = "Configuration file not found at '$filePath'"
            return Outcome.Error(ConfigurationError(message))
        }

        return try {
            val content = file.readText(Charsets.UTF_8)
            parser.parse(content.byteInputStream())
        } catch (e: Exception) {
            val message = "${e.message}"
            Outcome.Error(ConfigurationError(message))
        }
    }

    override fun read(): Outcome<Collection<Rule>, Diagnostic> {
        return readSafe().let {
            when (it) {
                is Outcome.Ok -> Outcome.Ok(it.value)
                is Outcome.Error -> it
            }
        }
    }
}
