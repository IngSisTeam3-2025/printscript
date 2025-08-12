package interpreter

class NodeVisitor(private val visitor: (Any) -> Unit) {

    fun visit(node: Any) {
        return visitor(node)
    }




}