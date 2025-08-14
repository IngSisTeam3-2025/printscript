import interpreter.Interpreter
import parser.Parser
import scanning.Tokenizer

fun main() {
    while (true) {
        print("ps> ")
        val source: String = readln()
        if (source.isBlank()) continue

        try {
            val tokenizer = Tokenizer(source)
            val parser = Parser(tokenizer)
            val ast = parser.parseProgram()
            val interpreter = Interpreter()
            val res = interpreter.interpret(ast)
            println(res)
        } catch (e: Exception) {
            println(e.message)
        }
    }
}

