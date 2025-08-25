package lexer

import Token

interface TokenSource {
    fun next(): Token
    fun peek(): Token
}