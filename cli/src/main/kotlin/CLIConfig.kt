import java.io.File

data class CLIConfig(
    val operation: Operation,
    val sourceFile: File,
    val version: String,
    val configFile: File?
)