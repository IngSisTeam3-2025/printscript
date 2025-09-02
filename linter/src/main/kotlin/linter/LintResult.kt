package linter

sealed interface LintResult {
    data class Success(val warnings: List<String>) : LintResult
    data class Error(val message: String) : LintResult
}
