
interface TokenSource {
    fun next(): Token
    fun peek(): Token
}