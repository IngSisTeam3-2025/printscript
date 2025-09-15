package lexer

fun main() {
    val input = "  abc123 + 456 - xyz "
    val lexer = Lexer(
        input.iterator(),
        listOf(IdentifierRecognizer(), NumberRecognizer(), PlusRecognizer(), MinusRecognizer()),
    )

    while (lexer.hasNext()) {
        val result = lexer.next()
        result.fold(
            onSuccess = {
                println(
                    "Token: ${it.type}, value='${it.value}', prefix='${it.prefixTrivia}', " +
                        "suffix='${it.suffixTrivia}', start=${it.start}, end=${it.end}",
                )
            },
            onFailure = { println("Lexer error: ${it.message}") },
        )
    }
}
