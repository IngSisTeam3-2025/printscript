package internal.model.error

import model.diagnostic.Diagnostic
import model.origin.LexerOrigin
import model.origin.Origin
import model.severity.ErrorSeverity
import model.severity.Severity

internal class ConfigError(
    override val message: String,
    override val severity: Severity = ErrorSeverity,
    override val origin: Origin = LexerOrigin
) : Diagnostic