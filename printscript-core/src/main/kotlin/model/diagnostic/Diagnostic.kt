package model.diagnostic

import model.diagnostic.category.Category
import model.diagnostic.severity.Severity

interface Diagnostic {
    val message: String
    val severity: Severity
    val category: Category
    fun format(): String = "${severity.name} -> (${category.name}): $message"
}
