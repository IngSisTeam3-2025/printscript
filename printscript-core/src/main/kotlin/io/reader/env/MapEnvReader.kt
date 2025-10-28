package io.reader.env

import model.value.Value
import type.option.Option

class MapEnvReader(private val env: Map<String, Value>) : EnvReader {
    override fun read(key: String): Option<Value> =
        env[key]?.let { Option.Some(it) } ?: Option.None
}
