package interpreter.internal.model.error

import interpreter.internal.model.category.IO
import model.diagnostic.Diagnostic
import model.diagnostic.category.Category
import model.diagnostic.severity.Error
import model.diagnostic.severity.Severity

internal class IOError(
    override val message: String,
    override val severity: Severity = Error,
    override val category: Category = IO,
) : Diagnostic
