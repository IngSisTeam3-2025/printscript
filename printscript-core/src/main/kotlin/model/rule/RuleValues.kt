package model.rule

data class IntRuleValue(val value: Int) : RuleValue {
    override fun asString() = value.toString()
}

data class BoolRuleValue(val value: Boolean) : RuleValue {
    override fun asString() = value.toString()
}

data class StringRuleValue(val value: String) : RuleValue {
    override fun asString() = value
}
