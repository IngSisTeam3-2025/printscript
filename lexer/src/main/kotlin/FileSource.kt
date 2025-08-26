package lexer

import java.io.FileReader
import java.io.BufferedReader
import java.io.Reader

class FileSource(private val filePath: String) : Source {
    override fun getReader(): Reader {
        return BufferedReader(FileReader(filePath))
    }
}