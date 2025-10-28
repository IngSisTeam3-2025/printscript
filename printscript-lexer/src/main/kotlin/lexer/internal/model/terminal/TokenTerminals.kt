package lexer.internal.model.terminal

import model.token.AssignToken
import model.token.BooleanLiteralToken
import model.token.BooleanTypeToken
import model.token.ColonToken
import model.token.ConstToken
import model.token.DivideToken
import model.token.ElseToken
import model.token.IdentifierToken
import model.token.IfToken
import model.token.LeftBraceToken
import model.token.LeftParenthesisToken
import model.token.LetToken
import model.token.MinusToken
import model.token.MultiplyToken
import model.token.NumberLiteralToken
import model.token.NumberTypeToken
import model.token.PlusToken
import model.token.PrintlnToken
import model.token.ReadEnvToken
import model.token.ReadInputToken
import model.token.RightBraceToken
import model.token.RightParenthesisToken
import model.token.SemicolonToken
import model.token.StringLiteralToken
import model.token.StringTypeToken

internal class LetTerminal : TokenTerminal {
    override val type = LetToken
    override val pattern = Regex("^let")
    override val priority = 10
}

internal class IdentifierTerminal : TokenTerminal {
    override val type = IdentifierToken
    override val pattern = Regex("^[a-zA-Z_][a-zA-Z0-9_]*")
    override val priority = 0
}

internal class NumberLiteralTerminal : TokenTerminal {
    override val type = NumberLiteralToken
    override val pattern = Regex("^[0-9]+(\\.[0-9]+)?")
    override val priority = 0
}

internal class StringLitTerminal : TokenTerminal {
    override val type = StringLiteralToken
    override val pattern = Regex("^\"(?:\\\\.|[^\"])*\"|^'(?:\\\\.|[^'])*'")

    override val priority = 0
}

internal class BooleanLiteralTerminal : TokenTerminal {
    override val type = BooleanLiteralToken
    override val pattern = Regex("^true|false")
    override val priority = 10
}

internal class StringTypeTerminal : TokenTerminal {
    override val type = StringTypeToken
    override val pattern = Regex("^string")
    override val priority = 10
}

internal class NumberTypeTerminal : TokenTerminal {
    override val type = NumberTypeToken
    override val pattern = Regex("^number")
    override val priority = 10
}

internal class ConstTypeTerminal : TokenTerminal {
    override val type = ConstToken
    override val pattern = Regex("^const")
    override val priority = 10
}

internal class BooleanTypeTerminal : TokenTerminal {
    override val type = BooleanTypeToken
    override val pattern = Regex("^boolean")
    override val priority = 10
}

internal class AssignTerminal : TokenTerminal {
    override val type = AssignToken
    override val pattern = Regex("^=")
    override val priority = 0
}

internal class PlusTerminal : TokenTerminal {
    override val type = PlusToken
    override val pattern = Regex("^\\+")
    override val priority = 0
}

internal class MinusTerminal : TokenTerminal {
    override val type = MinusToken
    override val pattern = Regex("^-")
    override val priority = 0
}

internal class MultiplyTerminal : TokenTerminal {
    override val type = MultiplyToken
    override val pattern = Regex("^\\*")
    override val priority = 0
}

internal class DivideTerminal : TokenTerminal {
    override val type = DivideToken
    override val pattern = Regex("^/")
    override val priority = 0
}

internal class ColonTerminal : TokenTerminal {
    override val type = ColonToken
    override val pattern = Regex("^:")
    override val priority = 0
}

internal class SemicolonTerminal : TokenTerminal {
    override val type = SemicolonToken
    override val pattern = Regex("^;")
    override val priority = 0
}

internal class IfTerminal : TokenTerminal {
    override val type = IfToken
    override val pattern = Regex("^if")
    override val priority = 10
}

internal class ElseTerminal : TokenTerminal {
    override val type = ElseToken
    override val pattern = Regex("^else")
    override val priority = 10
}

internal class PrintlnTerminal : TokenTerminal {
    override val type = PrintlnToken
    override val pattern = Regex("^println")
    override val priority = 10
}

internal class ReadEnvTerminal : TokenTerminal {
    override val type = ReadEnvToken
    override val pattern = Regex("^readEnv")
    override val priority = 10
}

internal class ReadInputTerminal : TokenTerminal {
    override val type = ReadInputToken
    override val pattern = Regex("^readInput")
    override val priority = 10
}

internal class LeftParenthesisTerminal : TokenTerminal {
    override val type = LeftParenthesisToken
    override val pattern = Regex("^\\(")
    override val priority = 0
}

internal class RightParenthesisTerminal : TokenTerminal {
    override val type = RightParenthesisToken
    override val pattern = Regex("^\\)")
    override val priority = 0
}

internal class LeftBraceTerminal : TokenTerminal {
    override val type = LeftBraceToken
    override val pattern = Regex("^\\{")
    override val priority = 10
}

internal class RightBraceTerminal : TokenTerminal {
    override val type = RightBraceToken
    override val pattern = Regex("^}")
    override val priority = 10
}
