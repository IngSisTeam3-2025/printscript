package util

import model.diagnostic.Diagnostic
import model.token.Token
import type.outcome.Outcome
import java.nio.file.Files
import java.nio.file.Path

internal object GoldenTester {

    fun read(path: Path): String {
        return Files.readString(path)
            .replace("\r\n", "\n")
            .replace("\r", "\n")
    }

    fun format(tokens: Sequence<Outcome<Token, Diagnostic>>): String {
        val lines = mutableListOf<String>()

        for (outcome in tokens) {
            when (outcome) {
                is Outcome.Ok -> {
                    val token = outcome.value
                    val tokenLines = TestToken(token).format()
                    lines.addAll(tokenLines)
                }
                is Outcome.Error -> {
                    val diagnostic = outcome.error
                    lines.add(diagnostic.format())
                }
            }
        }
        return lines.joinToString("\n")
    }
}
