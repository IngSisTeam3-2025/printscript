package formatter.internal.model.error

import formatter.internal.model.origin.FormatterCategory
import model.diagnostic.Diagnostic
import model.diagnostic.category.Category
import model.diagnostic.severity.Error
import model.diagnostic.severity.Severity

internal class ConfigurationError(
    override val message: String,
    override val severity: Severity = Error,
    override val category: Category = FormatterCategory,
) : Diagnostic
