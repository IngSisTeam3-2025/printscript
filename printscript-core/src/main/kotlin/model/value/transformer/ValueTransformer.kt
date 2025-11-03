package model.value.transformer

import model.value.Value
import type.option.Option

interface ValueTransformer {
    fun parse(input: String): Option<Value>
}
