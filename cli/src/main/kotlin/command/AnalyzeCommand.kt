package command

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.parameters.arguments.argument

class AnalyzeCommand : CliktCommand(name = "analyze") {
    val source by argument(help = "The file to analyze.")
    val version by argument(help = "The Ps language version.")

    override fun run() {
        TODO("Not yet implemented")
    }

    override fun help(context: Context) = "ps> Runs Code Analysis on source [file]."

}