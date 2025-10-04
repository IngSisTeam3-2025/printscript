package util.option

fun <T> Option<T>.onNone(action: () -> Unit) {
    if (this is Option.None) action()
}
