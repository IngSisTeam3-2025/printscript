package util.outcome

fun <T, E> Sequence<Outcome<T, E>>.oks(): Sequence<T> = filterIsInstance<Outcome.Ok<T>>().map { it.value }