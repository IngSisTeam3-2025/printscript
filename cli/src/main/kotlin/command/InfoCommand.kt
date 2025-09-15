package command

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context

class InfoCommand : CliktCommand(name = "info") {

    override fun run() {
        println()
        println(
            """
            ╔════════════════════════════════════════════════════╗
            ║            Platypus CLI (v1.0) — INFO              ║
            ╚════════════════════════════════════════════════════╝

            Welcome to Platypus — a command-line interface for working with PrintScript source files.

            Available Commands:

              validate   → Runs lexical, syntactical, and semantic checks on source [file].
              execute    → Executes the given source [file].
              format     → Formats the source [file] to match style conventions.
              analyze    → Runs static code analysis on the source [file].
              clear      → Clears the terminal screen.
              help       → Shows usage help for a command.
              info       → Shows this CLI info screen.
              exit       → Exits the REPL.

            Type `command --help` for more details on any command.

            """.trimIndent(),
        )
    }

    override fun help(context: Context) = "\"Show CLI info and available commands.\""
}
