package lexer.internal.model.error

import model.diagnostic.Diagnostic
import model.diagnostic.category.Category
import model.diagnostic.category.Configuration
import model.diagnostic.severity.Error
import model.diagnostic.severity.Severity

internal class ConfigurationError(
    override val message: String,
    override val severity: Severity = Error,
    override val category: Category = Configuration,
) : Diagnostic
