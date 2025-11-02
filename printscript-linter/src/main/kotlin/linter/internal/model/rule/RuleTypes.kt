package linter.internal.model.rule

import model.rule.RuleType

data object IdentifierFormatRule : RuleType {
    override val signature = "identifier_format"
    override val name = "IdentifierFormat"
}

data object MandatoryIdentifierOrLiteralInPrintlnRule : RuleType {
    override val signature = "mandatory-variable-or-literal-in-print"
    override val name = "MandatoryIdentifierOrLiteralInPrintln"
}

data object MandatoryIdentifierOrLiteralInReadInputRule : RuleType {
    override val signature = "mandatory-variable-or-literal-in-readInput"
    override val name = "MandatoryIdentifierOrLiteralInReadInput"
}
