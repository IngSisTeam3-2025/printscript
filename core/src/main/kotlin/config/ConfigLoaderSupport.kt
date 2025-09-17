package config

import java.io.Reader

internal fun readAll(reader: Reader): String {
    val builder = StringBuilder()
    val buffer = CharArray(BUFFER_SIZE)
    while (true) {
        val count = reader.read(buffer)
        if (count == -1) {
            break
        }
        builder.appendRange(buffer, 0, count)
    }
    return builder.toString()
}

internal fun warnUnknownKey(path: String, key: String, line: Int? = null) {
    if (line != null) {
        System.err.println("Unknown config key '$key' in $path at line $line")
    } else {
        System.err.println("Unknown config key '$key' in $path")
    }
}

private const val BUFFER_SIZE = 1024
