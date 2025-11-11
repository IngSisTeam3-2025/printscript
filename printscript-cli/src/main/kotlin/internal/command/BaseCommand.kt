package internal.command

import com.github.ajalt.clikt.core.CliktCommand

abstract class BaseCommand(name: String) : CliktCommand(name = name) {
    protected fun showProgress(message: String) = echo(message, err = true)
}
