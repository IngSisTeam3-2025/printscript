import common.Interpreter
import scanning.Tokenizer

fun main() {

    while (true) {
        println("ps> ")
        val source : String = readln()
        try {
            val tokenizer = Tokenizer(source)
            val interpreter = Interpreter(tokenizer)
            val res = interpreter.eval()
            println(res)
        }
        catch (e: Exception) {
            println(e.message)
        }
    }

}
