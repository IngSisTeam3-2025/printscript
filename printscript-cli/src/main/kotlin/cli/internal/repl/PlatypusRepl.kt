package cli.internal.repl

import cli.internal.error.ErrorHandlerRegistry
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.CliktError
import com.github.ajalt.clikt.core.parse
import java.io.IOError

class PlatypusRepl(
    private val rootCommand: CliktCommand,
    private val errorHandlerRegistry: ErrorHandlerRegistry,
) {

    private val grey = "\u001B[37m"

    fun repl() {
        while (true) {
            print("${grey}ps> ")
            val input = readlnOrNull() ?: break
            if (input.lowercase() == "exit") {
                println()
                break
            }
            if (input.isBlank()) continue

            val args = input.trim().split("\\s+".toRegex())

            try {
                rootCommand.parse(args)
            } catch (e: CliktError) {
                errorHandlerRegistry.handle(e)
            } catch (e: IOError) {
                println("Unexpected error: ${e.message}")
            }
        }
    }
}
