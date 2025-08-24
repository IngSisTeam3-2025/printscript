import interpreter.Interpreter
import interpreter.runtime.RuntimeValue

fun main() {
    val interpreter = Interpreter()
    while (true) {
        print("ps> ")
        val line = readLine() ?: break
        if (line.isBlank()) continue
        try { evalAndPrint(line, interpreter) }
        catch (e: Throwable) {
            print(e.message ?: e.toString())
            print("\n")
        }
    }
}


fun evalAndPrint(src: String, interpreter: Interpreter) {
    val tokenizer = lexer.Tokenizer(src)
    val ts = ParserTokenStream(tokenizer)
    val parser = Parser(ts)
    val program = parser.parseProgram()
    when (val result = interpreter.visit(program)) {
        is RuntimeValue.Num  -> println(result.v)
        is RuntimeValue.Str  -> println(result.v)
        is RuntimeValue.Void -> {}
    }
}