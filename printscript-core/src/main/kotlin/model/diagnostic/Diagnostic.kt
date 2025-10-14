package model.diagnostic

import model.origin.Origin
import model.severity.Severity

interface Diagnostic {
    val message: String
    val severity: Severity
    val origin: Origin
    fun format(): String = "${severity.name} [${origin.name}] $message"
}
