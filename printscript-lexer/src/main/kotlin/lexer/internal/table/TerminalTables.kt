package lexer.internal.table

import lexer.internal.model.terminal.AssignTerminal
import lexer.internal.model.terminal.BooleanLiteralTerminal
import lexer.internal.model.terminal.BooleanTypeTerminal
import lexer.internal.model.terminal.ColonTerminal
import lexer.internal.model.terminal.CommentTerminal
import lexer.internal.model.terminal.ConstTypeTerminal
import lexer.internal.model.terminal.DivideTerminal
import lexer.internal.model.terminal.ElseTerminal
import lexer.internal.model.terminal.IdentifierTerminal
import lexer.internal.model.terminal.IfTerminal
import lexer.internal.model.terminal.LeftBraceTerminal
import lexer.internal.model.terminal.LeftParenthesisTerminal
import lexer.internal.model.terminal.LetTerminal
import lexer.internal.model.terminal.LineCommentTerminal
import lexer.internal.model.terminal.MinusTerminal
import lexer.internal.model.terminal.MultiplyTerminal
import lexer.internal.model.terminal.NewlineTerminal
import lexer.internal.model.terminal.NumberLiteralTerminal
import lexer.internal.model.terminal.NumberTypeTerminal
import lexer.internal.model.terminal.PlusTerminal
import lexer.internal.model.terminal.PrintlnTerminal
import lexer.internal.model.terminal.ReadEnvTerminal
import lexer.internal.model.terminal.ReadInputTerminal
import lexer.internal.model.terminal.RightBraceTerminal
import lexer.internal.model.terminal.RightParenthesisTerminal
import lexer.internal.model.terminal.SemicolonTerminal
import lexer.internal.model.terminal.SpaceTerminal
import lexer.internal.model.terminal.StringLitTerminal
import lexer.internal.model.terminal.StringTypeTerminal
import lexer.internal.model.terminal.TabTerminal

internal object PrintScriptV10 : TerminalTable {
    override val tokenTerminals = listOf(
        AssignTerminal(),
        ColonTerminal(),
        DivideTerminal(),
        IdentifierTerminal(),
        LeftParenthesisTerminal(),
        LetTerminal(),
        MinusTerminal(),
        MultiplyTerminal(),
        NumberLiteralTerminal(),
        NumberTypeTerminal(),
        PlusTerminal(),
        PrintlnTerminal(),
        RightParenthesisTerminal(),
        SemicolonTerminal(),
        StringLitTerminal(),
        StringTypeTerminal(),
    )

    override val triviaTerminals = listOf(
        CommentTerminal(),
        LineCommentTerminal(),
        NewlineTerminal(),
        SpaceTerminal(),
        TabTerminal(),
    )
}

internal object PrintScriptV11 : TerminalTable {
    override val tokenTerminals = PrintScriptV10.tokenTerminals + listOf(
        BooleanLiteralTerminal(),
        BooleanTypeTerminal(),
        ConstTypeTerminal(),
        ElseTerminal(),
        IfTerminal(),
        LeftBraceTerminal(),
        RightBraceTerminal(),
        ReadEnvTerminal(),
        ReadInputTerminal(),
    )

    override val triviaTerminals = PrintScriptV10.triviaTerminals
}
