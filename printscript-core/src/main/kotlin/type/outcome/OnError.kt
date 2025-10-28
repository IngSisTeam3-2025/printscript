package type.outcome

inline fun <T, E> Outcome<T, E>.onError(action: (E) -> Unit): Outcome<T, E> {
    if (this is Outcome.Error) {
        action(error)
        return this
    }
    return this
}

fun <T, E> Sequence<Outcome<T, E>>.onError(report: (E) -> Unit): Sequence<T> = sequence {
    for (item in this@onError) {
        when (item) {
            is Outcome.Ok -> yield(item.value)
            is Outcome.Error -> {
                report(item.error)
                return@sequence
            }
        }
    }
}
