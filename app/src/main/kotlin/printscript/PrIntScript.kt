package printscript

import lexer.ILexer
import parser.IParser
import intepreter.IInterpreter
import target.ITargetWriter
import value.Value

class PrIntScript(private val
                  target: ITargetWriter,
                  lexer: ILexer,
                  parser: IParser,
                  interpreter: IInterpreter) {

    fun run() : Value {
        TODO()
    }

}
