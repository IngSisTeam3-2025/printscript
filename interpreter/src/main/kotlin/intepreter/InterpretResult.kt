package intepreter

import source.SourcePosition

sealed interface InterpretResult {
    data class Success(val result: Any) : InterpretResult
    data class Error(val message: String, val position: SourcePosition) : InterpretResult
    data object EOF : InterpretResult
}
