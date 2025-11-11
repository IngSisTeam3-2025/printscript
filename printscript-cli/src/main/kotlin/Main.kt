import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.core.subcommands
import internal.command.AnalyzeCommand
import internal.command.ExecuteCommand
import internal.command.FormatCommand
import internal.command.PrintScriptCli
import internal.command.ValidateCommand

fun main(args: Array<String>) {
    PrintScriptCli()
        .subcommands(
            ValidateCommand(),
            ExecuteCommand(),
            AnalyzeCommand(),
            FormatCommand(),
        ).main(args)
    return
}
