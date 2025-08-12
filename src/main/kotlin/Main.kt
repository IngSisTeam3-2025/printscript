import common.Interpreter

fun main() {

    while (true) {
        println("ps> ")
        val source : String = readln()
        try {
            val interpreter = Interpreter(source)
            val res = interpreter.expr()
            println(res)
        }
        catch (e: Exception) {
            println(e.message)
        }
    }

}
