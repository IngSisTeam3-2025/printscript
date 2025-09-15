package provider

sealed class DependencyResult<out T> {
    data class Success<T>(val value: T) : DependencyResult<T>()
    data object Missing : DependencyResult<Nothing>()
}
