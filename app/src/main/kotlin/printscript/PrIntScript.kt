package printscript

import intepreter.IInterpreter
import lexer.ILexer
import parser.IParser
import target.ITargetWriter
import value.Value

@SuppressWarnings("all")
class PrIntScript(
    private val
    target: ITargetWriter,
    lexer: ILexer,
    parser: IParser,
    interpreter: IInterpreter,
) {

    fun run(): Value {
        TODO()
    }
}
