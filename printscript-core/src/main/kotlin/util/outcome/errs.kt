package util.outcome

fun <T, E> Sequence<Outcome<T, E>>.errs(): Sequence<E> = filterIsInstance<Outcome.Err<E>>().map { it.error }