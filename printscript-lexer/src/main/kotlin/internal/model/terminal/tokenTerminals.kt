package internal.model.terminal

import model.token.*

internal class LetTerminal : TokenTerminal {
    override val type = Let
    override val pattern = Regex("^let")
    override val priority = 10
}

internal class IdentifierTerminal : TokenTerminal {
    override val type = Identifier
    override val pattern = Regex("^[a-zA-Z_][a-zA-Z0-9_]*")
    override val priority = 0
}

internal class NumberLitTerminal : TokenTerminal {
    override val type = NumberLit
    override val pattern = Regex("^[0-9]+(\\.[0-9]+)?")
    override val priority = 0
}

internal class StringLitTerminal : TokenTerminal {
    override val type = StringLit
    override val pattern = Regex("^\"(?:\\\\.|[^\"])*\"|^'(?:\\\\.|[^'])*'")

    override val priority = 0
}

internal class BooleanLitTerminal : TokenTerminal {
    override val type = BooleanLit
    override val pattern = Regex("^true|false")
    override val priority = 0
}

internal class StringTypeTerminal : TokenTerminal {
    override val type = StringType
    override val pattern = Regex("^string")
    override val priority = 10
}

internal class NumberTypeTerminal : TokenTerminal {
    override val type = NumberType
    override val pattern = Regex("^number")
    override val priority = 10
}

internal class ConstTypeTerminal : TokenTerminal {
    override val type = ConstType
    override val pattern = Regex("^const")
    override val priority = 10
}

internal class BooleanTypeTerminal : TokenTerminal {
    override val type = BooleanType
    override val pattern = Regex("^boolean")
    override val priority = 10
}

internal class AssignTerminal : TokenTerminal {
    override val type = Assign
    override val pattern = Regex("^=")
    override val priority = 0
}

internal class PlusTerminal : TokenTerminal {
    override val type = Plus
    override val pattern = Regex("^\\+")
    override val priority = 0
}

internal class MinusTerminal : TokenTerminal {
    override val type = Minus
    override val pattern = Regex("^-")
    override val priority = 0
}

internal class MultiplyTerminal : TokenTerminal {
    override val type = Multiply
    override val pattern = Regex("^\\*")
    override val priority = 0
}

internal class DivideTerminal : TokenTerminal {
    override val type = Divide
    override val pattern = Regex("^/")
    override val priority = 0
}

internal class ColonTerminal : TokenTerminal {
    override val type = Colon
    override val pattern = Regex("^:")
    override val priority = 0
}

internal class SemicolonTerminal : TokenTerminal {
    override val type = Semicolon
    override val pattern = Regex("^;")
    override val priority = 0
}

internal class IfTerminal : TokenTerminal {
    override val type = If
    override val pattern = Regex("^if")
    override val priority = 10
}

internal class ElseTerminal : TokenTerminal {
    override val type = Else
    override val pattern = Regex("^else")
    override val priority = 10
}

internal class PrintLnTerminal : TokenTerminal {
    override val type = PrintLn
    override val pattern = Regex("^printLn")
    override val priority = 10
}

internal class ReadEnvTerminal : TokenTerminal {
    override val type = ReadEnv
    override val pattern = Regex("^readEnv")
    override val priority = 10
}

internal class ReadInputTerminal : TokenTerminal {
    override val type = ReadInput
    override val pattern = Regex("^readInput")
    override val priority = 10
}

internal class LParenTerminal : TokenTerminal {
    override val type = LParen
    override val pattern = Regex("^\\(")
    override val priority = 0
}

internal class RParenTerminal : TokenTerminal {
    override val type = RParen
    override val pattern = Regex("^\\)")
    override val priority = 0
}

internal class LBracketTerminal : TokenTerminal {
    override val type = LBracket
    override val pattern = Regex("^\\{")
    override val priority = 10
}

internal class RBracketTerminal : TokenTerminal {
    override val type = RBracket
    override val pattern = Regex("^}")
    override val priority = 10
}
