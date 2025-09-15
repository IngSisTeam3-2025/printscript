package ast

import location.SourceLocation
import provider.DependencyProvider
import visitor.VisitorDispatcher

// Literals
class LiteralNode(
    val value: Any,
    private val loc: SourceLocation,
    leadingTrivia: String = "",
    trailingTrivia: String = "",
) : AstNode(leadingTrivia, trailingTrivia) {
    override val children: List<AstNode> = emptyList()
    override fun start() = loc
    override fun end() = loc
    override fun accept(
        dispatcher: VisitorDispatcher,
        provider: DependencyProvider,
    ) =
        dispatcher.dispatch(this, provider)
}

// Variable reference
class VariableNode(
    val name: String,
    private val startLoc: SourceLocation,
    private val endLoc: SourceLocation,
) : AstNode() {
    override val children = emptyList<AstNode>()
    override fun start() = startLoc
    override fun end() = endLoc
    override fun accept(
        dispatcher: VisitorDispatcher,
        provider: DependencyProvider,
    ) =
        dispatcher.dispatch(this, provider)
}

// Declaration
class VarDeclNode(
    val name: String,
    val type: String,
    val value: AstNode?,
    val constant: Boolean,
    private val startLoc: SourceLocation,
    private val endLoc: SourceLocation,
) : AstNode() {
    override val children = listOfNotNull(value)
    override fun start() = startLoc
    override fun end() = endLoc
    override fun accept(
        dispatcher: VisitorDispatcher,
        provider: DependencyProvider,
    ) =
        dispatcher.dispatch(this, provider)
}

// Assignment
class AssignmentNode(
    val name: String,
    val value: AstNode,
    private val startLoc: SourceLocation,
    private val endLoc: SourceLocation,
) : AstNode() {
    override val children = listOf(value)
    override fun start() = startLoc
    override fun end() = endLoc
    override fun accept(
        dispatcher: VisitorDispatcher,
        provider: DependencyProvider,
    ) =
        dispatcher.dispatch(this, provider)
}

// Binary op
enum class BinaryOp { ADD, SUB, MUL, DIV }

class BinaryOpNode(
    val left: AstNode,
    val right: AstNode,
    val op: BinaryOp,
    private val startLoc: SourceLocation,
    private val endLoc: SourceLocation,
) : AstNode() {
    override val children = listOf(left, right)
    override fun start() = startLoc
    override fun end() = endLoc
    override fun accept(
        dispatcher: VisitorDispatcher,
        provider: DependencyProvider,
    ) =
        dispatcher.dispatch(this, provider)
}

// Print
class PrintNode(
    val expr: AstNode,
    private val startLoc: SourceLocation,
    private val endLoc: SourceLocation,
) : AstNode() {
    override val children = listOf(expr)
    override fun start() = startLoc
    override fun end() = endLoc
    override fun accept(
        dispatcher: VisitorDispatcher,
        provider: DependencyProvider,
    ) =
        dispatcher.dispatch(this, provider)
}

// If / Else
class IfNode(
    val condition: AstNode,
    val thenBlock: List<AstNode>,
    val elseBlock: List<AstNode>?,
    private val startLoc: SourceLocation,
    private val endLoc: SourceLocation,
) : AstNode() {
    override val children = thenBlock + (elseBlock ?: emptyList()) + condition
    override fun start() = startLoc
    override fun end() = endLoc
    override fun accept(
        dispatcher: VisitorDispatcher,
        provider: DependencyProvider,
    ) =
        dispatcher.dispatch(this, provider)
}

// readInput
class ReadInputNode(
    val prompt: String,
    private val startLoc: SourceLocation,
    private val endLoc: SourceLocation,
) : AstNode() {
    override val children = emptyList<AstNode>()
    override fun start() = startLoc
    override fun end() = endLoc
    override fun accept(
        dispatcher: VisitorDispatcher,
        provider: DependencyProvider,
    ) =
        dispatcher.dispatch(this, provider)
}

// readEnv
class ReadEnvNode(
    val key: String,
    private val startLoc: SourceLocation,
    private val endLoc: SourceLocation,
) : AstNode() {
    override val children = emptyList<AstNode>()
    override fun start() = startLoc
    override fun end() = endLoc
    override fun accept(
        dispatcher: VisitorDispatcher,
        provider: DependencyProvider,
    ) =
        dispatcher.dispatch(this, provider)
}
