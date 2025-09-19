package it

sealed class PeekResult<out T> {
    data class Value<T>(val value: T) : PeekResult<T>()
    data object Empty : PeekResult<Nothing>()
}
