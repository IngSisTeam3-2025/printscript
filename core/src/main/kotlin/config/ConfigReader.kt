package config

import java.io.Reader
import java.util.Locale

class ConfigReader private constructor() {
    companion object {
        private val loaders = mutableMapOf<String, ConfigLoader>()

        init {
            register("json", JsonConfigLoader())
            register("yaml", YamlConfigLoader())
            register("yml", YamlConfigLoader())
            register("txt", TxtConfigLoader())
        }

        fun register(extension: String, loader: ConfigLoader) {
            loaders[extension.lowercase(Locale.ROOT)] = loader
        }

        fun load(path: String, reader: Reader): Config {
            val extension = extractExtension(path)
            val loader = loaders[extension]
                ?: throw ConfigLoadException(path, "No loader registered for extension: .$extension")
            return loader.load(path, reader)
        }

        private fun extractExtension(path: String): String {
            val dotIndex = path.lastIndexOf('.')
            if (dotIndex == -1 || dotIndex == path.length - 1) {
                throw ConfigLoadException(path, "Unable to determine configuration type from path")
            }
            return path.substring(dotIndex + 1).lowercase(Locale.ROOT)
        }
    }
}
