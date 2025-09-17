package config

import java.io.Reader

interface ConfigLoader {
    fun load(path: String, reader: Reader): Config
}
