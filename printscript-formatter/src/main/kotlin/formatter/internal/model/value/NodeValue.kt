package formatter.internal.model.value

import model.node.Node
import model.value.Value

internal data class NodeValue(val value: Node) : Value {
    override val type = NodeValueType
    override fun format(): String = value.toString()
}
