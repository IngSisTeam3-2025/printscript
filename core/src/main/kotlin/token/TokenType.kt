package token

data class TokenType(val name: String, val ignore: Boolean = false, val priority: Int = 0)

