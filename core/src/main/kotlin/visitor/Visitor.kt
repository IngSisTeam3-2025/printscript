package visitor

interface Visitor<N, R> {
    fun visit(node: N): R

    fun visitAll(nodes: Sequence<N>): Sequence<R> = sequence {
        for (node in nodes) {
            yield(visit(node))
        }
    }
}
