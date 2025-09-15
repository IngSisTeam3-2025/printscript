package command

import com.github.ajalt.clikt.core.CliktCommand

class ExitCommand : CliktCommand(name = "close") {
    override fun run() {
        echo("\n[SESSION TERMINATED]")
    }
}