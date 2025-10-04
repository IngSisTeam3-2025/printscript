package util

import internal.model.terminal.AssignTerminal
import internal.model.terminal.BooleanLitTerminal
import internal.model.terminal.BooleanTypeTerminal
import internal.model.terminal.ColonTerminal
import internal.model.terminal.CommentTerminal
import internal.model.terminal.ConstTypeTerminal
import internal.model.terminal.DivideTerminal
import internal.model.terminal.ElseTerminal
import internal.model.terminal.IdentifierTerminal
import internal.model.terminal.IfTerminal
import internal.model.terminal.LBracketTerminal
import internal.model.terminal.LParenTerminal
import internal.model.terminal.LetTerminal
import internal.model.terminal.MinusTerminal
import internal.model.terminal.MultiplyTerminal
import internal.model.terminal.NewlineTerminal
import internal.model.terminal.NumberLitTerminal
import internal.model.terminal.NumberTypeTerminal
import internal.model.terminal.PlusTerminal
import internal.model.terminal.PrintLnTerminal
import internal.model.terminal.RBracketTerminal
import internal.model.terminal.RParenTerminal
import internal.model.terminal.ReadEnvTerminal
import internal.model.terminal.ReadInputTerminal
import internal.model.terminal.SemicolonTerminal
import internal.model.terminal.SpaceTerminal
import internal.model.terminal.StringLitTerminal
import internal.model.terminal.StringTypeTerminal
import internal.table.TerminalTable

internal object PrintScriptV1_0Terminals : TerminalTable {
    override val tokenTerminals = listOf(
        LetTerminal(),
        PrintLnTerminal(),
        NumberLitTerminal(),
        StringLitTerminal(),
        NumberTypeTerminal(),
        StringTypeTerminal(),
        IdentifierTerminal(),
        AssignTerminal(),
        PlusTerminal(),
        MinusTerminal(),
        MultiplyTerminal(),
        DivideTerminal(),
        ColonTerminal(),
        SemicolonTerminal(),
        LParenTerminal(),
        RParenTerminal()
    )

    override val triviaTerminals = listOf(
        NewlineTerminal(),
        SpaceTerminal(),
        CommentTerminal()
    )
}

internal object PrintScriptV1_1Terminals : TerminalTable {
    override val tokenTerminals = PrintScriptV1_0Terminals.tokenTerminals + listOf(
        ConstTypeTerminal(),
        BooleanLitTerminal(),
        BooleanTypeTerminal(),
        IfTerminal(),
        ElseTerminal(),
        ReadInputTerminal(),
        ReadEnvTerminal(),
        LBracketTerminal(),
        RBracketTerminal(),
    )
    override val triviaTerminals = PrintScriptV1_0Terminals.triviaTerminals
}
