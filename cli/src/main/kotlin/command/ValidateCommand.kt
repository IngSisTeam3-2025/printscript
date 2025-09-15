package command

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.parameters.arguments.argument

class ValidateCommand : CliktCommand(name = "validate") {
    val source by argument(help = "The file to validate.")
    val version by argument(help = "The Ps language version.")

    override fun run() {
        TODO("Not yet implemented")
    }

    override fun help(context: Context) = "Runs Lexical, " +
        "Syntactical and Semantic Analysis on source [file]."
}
