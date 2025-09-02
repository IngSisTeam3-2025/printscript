package parser

import ast.AstNode
import grammar.Grammar
import lexer.ILexer

@SuppressWarnings("all")
class Parser(
    private val
    lexer: ILexer,
    grammar: List<Grammar>,
) : IParser {

    private var currentTree: AstNode? = null

    override fun parse(): ParseResult {
        return ParseResult.EOF
    }
}
