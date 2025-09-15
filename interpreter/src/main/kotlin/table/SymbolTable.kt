package table

class SymbolTable {
    private val vars = mutableMapOf<String, Any?>()
    private val consts = mutableSetOf<String>()

    fun declare(name: String, value: Any?, constant: Boolean = false): Result<Unit> {
        if (vars.containsKey(name)) return Result.failure(IllegalStateException("Variable $name already declared"))
        vars[name] = value
        if (constant) consts.add(name)
        return Result.success(Unit)
    }

    fun assign(name: String, value: Any?): Result<Unit> {
        if (!vars.containsKey(name)) return Result.failure(IllegalStateException("Variable $name not declared"))
        if (name in consts) return Result.failure(IllegalStateException("Cannot reassign const $name"))
        vars[name] = value
        return Result.success(Unit)
    }

    fun lookup(name: String): Result<Any?> =
        if (vars.containsKey(name)) {
            Result.success(vars[name])
        } else {
            Result.failure(IllegalStateException("Undefined variable $name"))
        }

    fun clear() = vars.clear()
}
