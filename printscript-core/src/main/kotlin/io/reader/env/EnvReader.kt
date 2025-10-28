package io.reader.env

import model.value.Value
import type.option.Option

interface EnvReader {
    fun read(key: String): Option<Value>
}
