package model.severity

data object InfoSeverity: Severity {
    override val name: String = "INFO"
}

data object WarningSeverity: Severity {
    override val name: String = "WARNING"
}

data object ErrorSeverity: Severity {
    override val name: String = "ERROR"
}