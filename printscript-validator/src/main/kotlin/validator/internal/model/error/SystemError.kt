package validator.internal.model.error

import model.diagnostic.Diagnostic
import model.diagnostic.category.Category
import model.diagnostic.severity.Error
import model.diagnostic.severity.Severity
import validator.internal.model.category.System

internal class SystemError(
    override val message: String,
    override val severity: Severity = Error,
    override val category: Category = System,
) : Diagnostic
