import java.io.File
import java.io.IOException

fun main() {
    val interpreter = Interpreter()

    /*
    if (tryRunFromFile("program.prints", interpreter)) {
        return
    }
    */

    //Si no encuentra ese file pasa directamente al input del usuario
    runInteractiveMode(interpreter)
}

fun tryRunFromFile(fileName: String, interpreter: Interpreter): Boolean {
    val file = File(fileName)

    if (!file.exists() || !file.canRead()) {
        return false
    }

    return try {
        println("Ejecutando archivo: $fileName")
        val source = FileSource(fileName)
        val tokenizer = Tokenizer(source)
        val ts = ParserTokenStream(tokenizer)
        val parser = Parser(ts)

        val program = parser.parseProgram()
        val result = interpreter.visit(program)

        println("Programa ejecutado exitosamente.")
        when (result) {
            is RuntimeValue.Num  -> println("Resultado: ${result.v}")
            is RuntimeValue.Str  -> println("Resultado: ${result.v}")
            is RuntimeValue.Void -> {}
        }

        ts.close()
        true
    } catch (e: IOException) {
        println("Error al ejecutar archivo '$fileName': ${e.message}")
        false
    }
}

fun runInteractiveMode(interpreter: Interpreter) {
    while (true) {
        print("ps> ")
        val line = readLine() ?: break
        if (line.isBlank()) continue

        try {
            evalAndPrint(line, interpreter)
        } catch (e: IOException) {
            println(e.message ?: e.toString())
        }
    }
}

fun evalAndPrint(src: String, interpreter: Interpreter) {
    val source = StringSource(src)
    val tokenizer = Tokenizer(source)
    val ts = ParserTokenStream(tokenizer)
    val parser = Parser(ts)

    try {
        val program = parser.parseProgram()
        when (val result = interpreter.visit(program)) {
            is RuntimeValue.Num  -> println(result.v)
            is RuntimeValue.Str  -> println(result.v)
            is RuntimeValue.Void -> {}
        }
    } finally {
        ts.close()
    }
}
