import com.github.ajalt.clikt.core.BadParameterValue
import com.github.ajalt.clikt.core.MissingArgument
import com.github.ajalt.clikt.core.MissingOption
import com.github.ajalt.clikt.core.NoSuchSubcommand
import com.github.ajalt.clikt.core.UsageError
import com.github.ajalt.clikt.core.main
import command.InitCommand
import command.Platypus
import error.ErrorHandlerRegistry
import repl.PlatypusRepl

fun main() {
    InitCommand().main(emptyArray())

    val red = "\u001B[31m"
    val yellow = "\u001B[33m"
    val grey = "\u001B[37m"

    val errorHandlerRegistry = ErrorHandlerRegistry(
        handlers = mapOf(
            NoSuchSubcommand::class to { e ->
                val cmd = (e as? NoSuchSubcommand)?.paramName ?: "<unknown>"
                println(
                    """
                $red(X) - Unknown command: '$cmd'
                ${yellow}Hint:$grey Type 'info' to see a list of available commands.
                    """.trimIndent(),
                )
            },

            MissingArgument::class to { e ->
                println(
                    """
                $red(X) - Missing argument
                ${yellow}Details:$grey ${e.message}
                ${yellow}Hint:$grey Make sure you provide all required arguments for this command.
                    """.trimIndent(),
                )
            },

            MissingOption::class to { e ->
                val optName = (e as? MissingOption)?.printError
                println(
                    """
                $red(X) - Required option missing: $optName
                ${yellow}Details:$grey ${e.message}
                ${yellow}Hint:$grey Use '--help' after the command to view required options.
                    """.trimIndent(),
                )
            },

            BadParameterValue::class to { e ->
                println(
                    """
                $red(X) - Invalid value provided
                ${yellow}Details:$grey ${e.message}
                ${yellow}Hint:$grey Double-check the expected format or allowed values.
                    """.trimIndent(),
                )
            },

            UsageError::class to { e ->
                println(
                    """
                $red(X) - Incorrect usage
                ${yellow}Details:$grey ${e.message}
                ${yellow}Hint:$grey Use '--help' with the command for usage examples.
                    """.trimIndent(),
                )
            },
        ),
    )

    val platypus = Platypus()
    val repl = PlatypusRepl(platypus, errorHandlerRegistry)
    repl.repl()
}
