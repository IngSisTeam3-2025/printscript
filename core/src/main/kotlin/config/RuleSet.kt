package config

class RuleSet private constructor(private val config: Config) {

    val spaceBeforeColon: Boolean
        get() = config.spaceBeforeColon

    val spaceAfterColon: Boolean
        get() = config.spaceAfterColon

    val spaceAroundEquals: Boolean
        get() = config.spaceAroundEquals

    val newlinesBeforePrintln: Int
        get() = config.newlinesBeforePrintln

    val newlineAfterSemicolon: Boolean
        get() = config.newlineAfterSemicolon

    val singleSpaceBetweenTokens: Boolean
        get() = config.singleSpaceBetweenTokens

    val spaceAroundOperators: Boolean
        get() = config.spaceAroundOperators

    val enforceTrailingNewline: Boolean
        get() = true

    val enforceSingleStatementPerLine: Boolean
        get() = true

    companion object {
        fun from(config: Config): RuleSet {
            return RuleSet(config)
        }
    }
}
