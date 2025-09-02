package source

import token.TokenTemp

interface TokenSource {
    fun next(): TokenTemp
    fun peek(): TokenTemp
}
