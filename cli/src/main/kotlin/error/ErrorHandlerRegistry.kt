package error

import com.github.ajalt.clikt.core.CliktError
import kotlin.reflect.KClass

class ErrorHandlerRegistry(
    private val handlers: Map<KClass<out CliktError>, (CliktError) -> Unit>,
) {

    fun handle(error: CliktError) {
        val handler = handlers.entries
            .firstOrNull { it.key.isInstance(error) }
            ?.value ?: defaultHandler

        handler(error)
    }

    private val red = "\u001B[31m"

    private val defaultHandler: (CliktError) -> Unit = { e ->
        println("${red}X - ${e::class.simpleName}: ${e.message ?: "Unknown error"}")
    }
}


