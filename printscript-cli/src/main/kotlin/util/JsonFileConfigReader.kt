package util

import io.reader.config.ConfigReader
import model.rule.Rule
import util.parser.RuleParser
import java.io.File

class JsonFileConfigReader(
    private val filePath: String,
    private val parser: RuleParser,
) : ConfigReader {

    override fun read(): Collection<Rule> {
        val file = File(filePath)

        if (!file.exists()) {
            return emptyList()
        }

        val content = file.readText(Charsets.UTF_8)
        return parser.parse(content.byteInputStream())
    }
}
