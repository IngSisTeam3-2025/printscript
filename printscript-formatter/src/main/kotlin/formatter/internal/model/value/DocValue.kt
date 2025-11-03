package formatter.internal.model.value

import model.doc.Doc
import model.value.Value

internal data class DocValue(val value: Doc) : Value {
    override val type = DocValueType
    override fun format(): String = value.toString()
}
