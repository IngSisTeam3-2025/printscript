package config

data class Config(
    val spaceBeforeColon: Boolean,
    val spaceAfterColon: Boolean,
    val spaceAroundEquals: Boolean,
    val newlinesBeforePrintln: Int,
    val newlineAfterSemicolon: Boolean,
    val singleSpaceBetweenTokens: Boolean,
    val spaceAroundOperators: Boolean,
) {
    companion object {
        fun withDefaults(): Config {
            return Config(
                spaceBeforeColon = false,
                spaceAfterColon = true,
                spaceAroundEquals = true,
                newlinesBeforePrintln = 0,
                newlineAfterSemicolon = true,
                singleSpaceBetweenTokens = true,
                spaceAroundOperators = true,
            )
        }
    }
}
