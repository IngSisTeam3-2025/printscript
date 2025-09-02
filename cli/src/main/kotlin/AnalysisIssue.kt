import source.SourcePosition

data class AnalysisIssue(
    val message: String,
    val position: SourcePosition,
    val severity: IssueSeverity = IssueSeverity.WARNING
)