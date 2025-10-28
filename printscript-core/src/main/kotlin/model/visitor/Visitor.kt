package model.visitor

import model.diagnostic.Diagnostic
import model.node.Node
import model.value.Value
import type.outcome.Outcome

interface Visitor {
    fun visit(
        node: Node.Leaf,
        table: VisitorTable,
    ): Outcome<Value, Diagnostic>

    fun visit(
        node: Node.Composite,
        table: VisitorTable,
    ): Outcome<Value, Diagnostic>
}
