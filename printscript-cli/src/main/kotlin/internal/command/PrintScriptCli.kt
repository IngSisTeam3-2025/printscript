package internal.command

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context

class PrintScriptCli : CliktCommand(name = "printscript") {
    override fun run() = Unit
    override fun help(context: Context) = "A command-line interface for the PrintScript language"
}
