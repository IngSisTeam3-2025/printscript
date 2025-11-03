package cli.internal.command

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands

class Platypus : CliktCommand(name = "platypus") {

    init {
        subcommands(
            AnalyzeCommand(),
            ExecuteCommand(),
            FormatCommand(),
            ValidateCommand(),
            InfoCommand(),
            ClearCommand(),
            ExitCommand(),
        )
    }

    override fun run() {
        return
    }
}
