import java.io.Reader
import java.io.StringReader

class StringSource(private val content: String) : Source {
    override fun getReader(): Reader {
        return StringReader(content)
    }
}
