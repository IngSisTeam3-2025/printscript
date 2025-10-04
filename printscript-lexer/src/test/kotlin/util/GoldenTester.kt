package util

import java.nio.file.Files
import java.nio.file.Path

import model.diagnostic.Diagnostic
import model.token.Token
import util.outcome.Outcome

internal object GoldenTester {

    fun read(path: Path): String {
        return Files.readString(path).replace("\r\n", "\n").replace("\r", "\n")
    }

    fun format(tokens: Sequence<Outcome<Token, Diagnostic>>): String {
        val builder = StringBuilder()

        for (outcome in tokens) {
            when (outcome) {
                is Outcome.Ok -> {
                    val t = outcome.value
                    builder.append("${t.span.format()}:TOKEN: ${t.type} '${t.lexeme}'\n")

                    if (t.leading.isNotEmpty()) {
                        t.leading.forEach {
                            val lexemeEscaped = it.lexeme.replace("\n", "\\n").replace("\r", "\\r")
                            builder.append("${it.span.format()}:LEADING: ${it.type} '$lexemeEscaped'\n")
                        }
                    }
                    if (t.trailing.isNotEmpty()) {
                        t.trailing.forEach {
                            val lexemeEscaped = it.lexeme.replace("\n", "\\n").replace("\r", "\\r")
                            builder.append("${it.span.format()}:TRAILING: ${it.type} '$lexemeEscaped'\n")
                        }
                    }
                }
                is Outcome.Err -> {
                    builder.appendLine(outcome.error.format())
                }
            }
        }

        return builder.toString().trimEnd()
    }
}

