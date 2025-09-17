package config

import java.io.BufferedReader
import java.io.Reader

class YamlConfigLoader : ConfigLoader {

    override fun load(path: String, reader: Reader): Config {
        val defaults = Config.withDefaults()
        var spaceBeforeColon = defaults.spaceBeforeColon
        var spaceAfterColon = defaults.spaceAfterColon
        var spaceAroundEquals = defaults.spaceAroundEquals
        var newlinesBeforePrintln = defaults.newlinesBeforePrintln
        var newlineAfterSemicolon = defaults.newlineAfterSemicolon
        var singleSpaceBetweenTokens = defaults.singleSpaceBetweenTokens
        var spaceAroundOperators = defaults.spaceAroundOperators

        val buffered = if (reader is BufferedReader) reader else BufferedReader(reader)
        var lineNumber = 0
        while (true) {
            val rawLine = buffered.readLine() ?: break
            lineNumber += 1
            val trimmed = rawLine.trim()
            if (trimmed.isEmpty() || trimmed.startsWith("#")) {
                continue
            }
            val colonIndex = trimmed.indexOf(':')
            if (colonIndex == -1) {
                throw ConfigLoadException(path, "Invalid YAML entry: $rawLine", lineNumber)
            }
            val key = trimmed.substring(0, colonIndex).trim()
            val valuePart = trimmed.substring(colonIndex + 1).trim()
            val cleanedValue = removeQuotes(valuePart)
            val column = colonIndex + 1
            when (key) {
                SPACE_BEFORE_COLON ->
                    spaceBeforeColon =
                        parseBoolean(path, key, cleanedValue, lineNumber, column)

                SPACE_AFTER_COLON ->
                    spaceAfterColon =
                        parseBoolean(path, key, cleanedValue, lineNumber, column)

                SPACE_AROUND_EQUALS ->
                    spaceAroundEquals =
                        parseBoolean(path, key, cleanedValue, lineNumber, column)

                NEWLINES_BEFORE_PRINTLN ->
                    newlinesBeforePrintln =
                        parseIntInRange(path, key, cleanedValue, MIN_NEWLINES, MAX_NEWLINES)

                NEWLINE_AFTER_SEMICOLON ->
                    newlineAfterSemicolon =
                        parseBoolean(path, key, cleanedValue, lineNumber, column)

                SINGLE_SPACE_BETWEEN_TOKENS ->
                    singleSpaceBetweenTokens =
                        parseBoolean(path, key, cleanedValue, lineNumber, column)

                SPACE_AROUND_OPERATORS ->
                    spaceAroundOperators =
                        parseBoolean(path, key, cleanedValue, lineNumber, column)

                else -> warnUnknownKey(path, key, lineNumber)
            }
        }
        return Config(
            spaceBeforeColon,
            spaceAfterColon,
            spaceAroundEquals,
            newlinesBeforePrintln,
            newlineAfterSemicolon,
            singleSpaceBetweenTokens,
            spaceAroundOperators,
        )
    }

    private fun removeQuotes(value: String): String {
        if (value.length >= MIN_QUOTE_LENGTH) {
            if (value.startsWith("\"") && value.endsWith("\"")) {
                return value.substring(1, value.length - 1)
            }
            if (value.startsWith("'") && value.endsWith("'")) {
                return value.substring(1, value.length - 1)
            }
        }
        return value
    }

    private fun parseBoolean(
        path: String,
        key: String,
        value: String,
        line: Int,
        column: Int,
    ): Boolean {
        return when (value.lowercase()) {
            "true" -> true
            "false" -> false
            else -> throw ConfigLoadException(
                path,
                "Invalid boolean value '$value' for key '$key'",
                line,
                column,
            )
        }
    }

    private fun parseIntInRange(path: String, key: String, value: String, min: Int, max: Int): Int {
        val intValue = try {
            value.toInt()
        } catch (e: NumberFormatException) {
            throw ConfigLoadException(path, "Invalid integer value '$value' for key '$key'")
        }

        if (intValue < min || intValue > max) {
            throw ConfigLoadException(
                path,
                "Value '$intValue' for key '$key' must be between $min and $max",
            )
        }

        return intValue
    }

    companion object {
        private const val SPACE_BEFORE_COLON = "spaceBeforeColon"
        private const val SPACE_AFTER_COLON = "spaceAfterColon"
        private const val SPACE_AROUND_EQUALS = "spaceAroundEquals"
        private const val NEWLINES_BEFORE_PRINTLN = "newlinesBeforePrintln"
        private const val NEWLINE_AFTER_SEMICOLON = "newlineAfterSemicolon"
        private const val SINGLE_SPACE_BETWEEN_TOKENS = "singleSpaceBetweenTokens"
        private const val SPACE_AROUND_OPERATORS = "spaceAroundOperators"

        private const val MIN_NEWLINES = 0
        private const val MAX_NEWLINES = 10
        private const val MIN_QUOTE_LENGTH = 2
    }
}
