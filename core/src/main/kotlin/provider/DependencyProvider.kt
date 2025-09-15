package provider

class DependencyProvider {
    private val deps = mutableMapOf<Class<*>, Any>()

    fun <T : Any> register(type: Class<T>, instance: T) {
        deps[type] = instance
    }

    fun <T : Any> get(type: Class<T>): Result<T> {
        val dep = deps[type]
        return if (dep == null) {
            Result.failure(IllegalStateException("Missing dependency: ${type.simpleName}"))
        } else {
            Result.success(type.cast(dep))
        }
    }
}
