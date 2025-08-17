import interpreter.Interpreter
import parser.Parser
import lexer.Tokenizer

fun main() {
    while (true) {
        print("ps> ")
        val source: String = readln()
        if (source.isBlank()) continue

        try {
            val tokenizer = Tokenizer(source)
            val parser = Parser(tokenizer)
            val interpreter = Interpreter()
            val res = interpreter.interpret(parser.parseProgram())
            println(res)
        } catch (e: Exception) {
            println(e.message)
        }
    }
}

