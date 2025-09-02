package formatter

sealed interface FormatResult {
    data object Success : FormatResult
    data class Error(val message: String) : FormatResult
}
