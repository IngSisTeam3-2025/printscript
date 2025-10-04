package util.outcome

fun <T, E> Sequence<Outcome<T, E>>.onErr(
    report: (E) -> Unit
): Sequence<T> = sequence {
    for (outcome in this@onErr) {
        when (outcome) {
            is Outcome.Ok -> yield(outcome.value)
            is Outcome.Err -> report(outcome.error)
        }
    }
}
