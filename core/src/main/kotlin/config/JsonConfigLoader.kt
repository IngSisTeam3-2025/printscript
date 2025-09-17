package config

import java.io.Reader

class JsonConfigLoader : ConfigLoader {

    override fun load(path: String, reader: Reader): Config {
        val content = readAll(reader).trim()
        if (content.isEmpty()) {
            return Config.withDefaults()
        }
        if (!content.startsWith("{") || !content.endsWith("}")) {
            throw ConfigLoadException(path, "Expected JSON object")
        }
        val body = content.substring(1, content.length - 1)
        val defaults = Config.withDefaults()
        var spaceBeforeColon = defaults.spaceBeforeColon
        var spaceAfterColon = defaults.spaceAfterColon
        var spaceAroundEquals = defaults.spaceAroundEquals
        var newlinesBeforePrintln = defaults.newlinesBeforePrintln
        var newlineAfterSemicolon = defaults.newlineAfterSemicolon
        var singleSpaceBetweenTokens = defaults.singleSpaceBetweenTokens
        var spaceAroundOperators = defaults.spaceAroundOperators

        val entries = splitEntries(body)
        for (entry in entries) {
            if (entry.isBlank()) {
                continue
            }
            val colonIndex = findColon(entry)
            if (colonIndex == -1) {
                throw ConfigLoadException(path, "Malformed JSON entry: $entry")
            }
            val keyPart = entry.substring(0, colonIndex).trim()
            val valuePart = entry.substring(colonIndex + 1).trim()
            val key = parseJsonString(keyPart, path)
            val value = parseJsonValue(valuePart)
            when (key) {
                SPACE_BEFORE_COLON -> spaceBeforeColon = parseBoolean(path, key, value)
                SPACE_AFTER_COLON -> spaceAfterColon = parseBoolean(path, key, value)
                SPACE_AROUND_EQUALS -> spaceAroundEquals = parseBoolean(path, key, value)
                NEWLINES_BEFORE_PRINTLN ->
                    newlinesBeforePrintln =
                        parseIntInRange(path, key, value, MIN_NEWLINES, MAX_NEWLINES)

                NEWLINE_AFTER_SEMICOLON -> newlineAfterSemicolon = parseBoolean(path, key, value)
                SINGLE_SPACE_BETWEEN_TOKENS ->
                    singleSpaceBetweenTokens =
                        parseBoolean(path, key, value)

                SPACE_AROUND_OPERATORS -> spaceAroundOperators = parseBoolean(path, key, value)
                else -> warnUnknownKey(path, key)
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

    private fun splitEntries(body: String): List<String> {
        val entries = mutableListOf<String>()
        val builder = StringBuilder()
        var inString = false
        for (character in body) {
            if (character == '\"') {
                inString = !inString
            }
            if (character == ',' && !inString) {
                entries.add(builder.toString())
                builder.setLength(0)
            } else {
                builder.append(character)
            }
        }
        if (builder.isNotEmpty()) {
            entries.add(builder.toString())
        }
        return entries
    }

    private fun findColon(entry: String): Int {
        var inString = false
        for (index in entry.indices) {
            val character = entry[index]
            if (character == '\"') {
                inString = !inString
            }
            if (character == ':' && !inString) {
                return index
            }
        }
        return -1
    }

    private fun parseJsonString(fragment: String, path: String): String {
        val trimmed = fragment.trim()
        if (trimmed.length < MIN_STRING_LENGTH || !trimmed.startsWith('"') || !trimmed.endsWith('"')) {
            throw ConfigLoadException(path, "Expected JSON string but was $fragment")
        }
        return trimmed.substring(1, trimmed.length - 1)
    }

    private fun parseJsonValue(fragment: String): String {
        val trimmed = fragment.trim()
        if (trimmed.startsWith('"') && trimmed.endsWith('"') && trimmed.length >= MIN_STRING_LENGTH) {
            return trimmed.substring(1, trimmed.length - 1)
        }
        return trimmed
    }

    private fun parseBoolean(path: String, key: String, value: String): Boolean {
        return when (value.lowercase()) {
            "true" -> true
            "false" -> false
            else -> throw ConfigLoadException(path, "Invalid boolean value '$value' for key '$key'")
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
        private const val MIN_STRING_LENGTH = 2
    }
}
