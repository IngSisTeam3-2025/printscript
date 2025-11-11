package internal

import io.reader.env.EnvReader
import model.value.Value
import model.value.transformer.ValueTransformer
import type.option.Option

class SystemEnvReader(
    private val transformers: Collection<ValueTransformer>,
) : EnvReader {

    override fun read(key: String): Option<Value> {
        val rawValue = System.getenv(key) ?: return Option.None

        for (transformer in transformers) {
            val parsed = transformer.parse(rawValue)
            if (parsed is Option.Some) {
                return parsed
            }
        }
        return Option.None
    }
}
