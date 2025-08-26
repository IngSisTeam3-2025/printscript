package lexer

import java.io.Reader

interface Source {
    fun getReader(): Reader
}