package model.value.transformer

import model.value.BooleanValue
import model.value.FloatValue
import model.value.IntegerValue
import model.value.StringValue
import model.value.Value
import type.option.Option

object IntegerValueTransformer : ValueTransformer {
    override fun parse(input: String): Option<Value> {
        val result = input.toIntOrNull()
        return when (result) {
            null -> {
                Option.None
            }
            else -> {
                Option.Some(IntegerValue(result))
            }
        }
    }
}

object FloatValueTransformer : ValueTransformer {
    override fun parse(input: String): Option<Value> {
        val result = input.toFloatOrNull()
        return when (result) {
            null -> {
                Option.None
            }
            else -> {
                Option.Some(FloatValue(result))
            }
        }
    }
}

object BooleanValueTransformer : ValueTransformer {
    override fun parse(input: String): Option<Value> =
        when (input.lowercase()) {
            "true" -> {
                Option.Some(BooleanValue(true))
            }
            "false" -> {
                Option.Some(BooleanValue(false))
            }
            else -> {
                Option.None
            }
        }
}

object StringValueTransformer : ValueTransformer {
    override fun parse(input: String): Option<Value> = Option.Some(StringValue(input))
}
