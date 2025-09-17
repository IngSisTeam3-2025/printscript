package config

class ConfigLoadException(
    val path: String,
    message: String,
    private val line: Int? = null,
    private val column: Int? = null,
) : Exception(message) {

    override fun toString(): String {
        val builder = StringBuilder()
        builder.append("ConfigLoadException[")
        builder.append(path)
        if (line != null) {
            builder.append(":")
            builder.append(line)
            if (column != null) {
                builder.append(":")
                builder.append(column)
            }
        }
        builder.append("] ")
        builder.append(message)
        return builder.toString()
    }
}
