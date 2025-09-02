package parser

import grammar.Grammar
import ast.AstNode
import lexer.ILexer

class Parser(private val
             lexer: ILexer,
             grammar: List<Grammar>) : IParser {

    private var currentTree: AstNode? = null

    override fun parse(): ParseResult {




        return ParseResult.EOF
    }

}
