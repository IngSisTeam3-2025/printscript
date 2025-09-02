package intepreter

import parser.IParser
import symboltable.ISymbolTable

@SuppressWarnings("all")
class Interpreter(parser: IParser, symbolTable: ISymbolTable) : IInterpreter {

    override fun interpret(): InterpretResult {
        TODO("Not yet implemented")
    }
}
