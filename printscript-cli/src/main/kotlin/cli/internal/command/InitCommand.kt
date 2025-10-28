package cli.internal.command

import com.github.ajalt.clikt.core.CliktCommand

class InitCommand : CliktCommand(name = "platypus") {
    private val green = "\u001B[32m"
    private val blue = "\u001B[34m"
    private val yellow = "\u001B[33m"
    private val grey = "\u001B[37m"
    private val bold = "\u001B[1m"
    private val reset = "\u001B[0m"

    override fun run() {
        echo("\n${grey}Platypus CLI (v1.0).")
        echo(
            """$green
       _       _
      | |     | |
 _ __ | | __ _| |_ _   _ _ __  _   _ ___
| '_ \| |/ _` | __| | | | '_ \| | | / __|
| |_) | | (_| | |_| |_| | |_) | |_| \__ \
| .__/|_|\__,_|\__|\__, | .__/ \__,_|___/
| |                 __/ | |
|_|                |___/|_|      ⠀⠀⠀⠀⠀
                    ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣠⣤⣤⣤⣤⣤⣤⠶⠛⠁⠀⠀⠀⠀⠀⠀⠀
                    ⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⣶⠛⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                    ⠀⠀⠀⠀⠀⠀⣀⣠⣾⡋⢻⡄⠀⣤⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                    ⠀⣠⣴⠖⠛⠋⠉⠁⠀⠹⣬⣻⠆⠈⠁⠀⠀⠀⠀⠀⠀⠀⢠⡄⠀⠀
                    ⢸⡟⠉⣴⠀⠀⠀⠀⢀⣤⢟⣿⠀⠀⠀⠀⠀⠀⣆⠀⠀⠀⢸⡇⠀⠀⠀⠀⠀
                    ⠈⠳⣆⡀⠀⣀⣤⣶⠿⠟⠋⠉⠳⢦⣤⣀⣠⡾⠋⠀⠀⢠⣾⠀
                    ⠀⠀⠈⠉⠉⠉⠁⠀⠀⠀⠀⠀⠀⢀⣴⣿⢀⠀⣠⡴⠚⠉⠀⠀⠀⠀⠀⠀⠀⠀
                    ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⢻⣯⡾⢸⡏⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                    ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠉⠛⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
            """.trimIndent(),
        )
        echo()
        echo(
            "\n${grey}LICENSE: $bold[UA-ING_SIS-2025] ~ By: " +
                "${bold}${blue}Juan Decoud - " +
                "${bold}${blue}Felipe Rawson - " +
                "${bold}${blue}Julian Ritondale.",
        )
        echo("\n${yellow}Type 'exit' to quit.$reset")
        echo("${yellow}Type 'info' to explore cli.$reset")
        echo()
    }
}
