package cli.internal.command

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context

class ClearCommand : CliktCommand(name = "clear") {

    override fun run() {
        print("\u001B[H\u001B[2J")
        System.out.flush()
    }

    override fun help(context: Context): String = "Clear the terminal screen."
}
