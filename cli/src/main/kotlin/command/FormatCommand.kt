package command

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.parameters.arguments.argument

class FormatCommand : CliktCommand(name = "format") {
    val source by argument(help = "The file to format.")
    val version by argument(help = "The Ps language version.")
    val config by argument(help = "The format rule configuration file.")

    override fun run() {
        TODO("Not yet implemented")
    }

    override fun help(context: Context) = "ps> Formats code from source [file]."
}
