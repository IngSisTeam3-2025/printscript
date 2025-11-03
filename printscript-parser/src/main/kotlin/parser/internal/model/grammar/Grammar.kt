package parser.internal.model.grammar

import model.node.NodeType
import model.token.Token
import parser.internal.table.GrammarTable
import type.outcome.Outcome

internal interface Grammar {
    val type: NodeType

    fun match(
        tokens: List<Token>,
        table: GrammarTable,
    ): Outcome<GrammarMatch, GrammarFail>
}
