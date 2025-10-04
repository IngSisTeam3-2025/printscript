package util.option

fun <T> Option<T>.onSome(block: (T) -> Unit): Option<T> {
    if (this is Option.Some) block(this.value)
    return this
}