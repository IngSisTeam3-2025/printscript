import error.LexError
import error.ParseError
import node.AstNode
import node.CstNode
import result.Result
import token.Token

@Suppress("UNUSED_PARAMETER")
class Parser {

    fun parseAST(tokens: Sequence<Result<Token, LexError>>): Sequence<Result<AstNode, ParseError>> = sequence {
        TODO("not implemented")
    }

    fun parseCST(tokens: Sequence<Result<Token, LexError>>): Sequence<Result<CstNode, ParseError>> = sequence {
        TODO("not implemented")
    }
}
