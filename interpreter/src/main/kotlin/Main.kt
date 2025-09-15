import ast.AstNode
import ast.ReadInputNode
import location.SourceLocation
import visitors.AssignmentVisitor
import visitors.BinaryOpVisitor
import visitors.IfVisitor
import visitors.LiteralVisitor
import visitors.PrintVisitor
import visitors.ReadEnvVisitor
import visitors.ReadInputVisitor
import visitors.VarDeclVisitor
import visitors.VariableVisitor
import kotlin.Result

fun main() {
    val output = System.out.writer()
    val input = System.`in`.bufferedReader()
    val env = emptyMap<String, String>()

    val visitors = listOf(
        LiteralVisitor(),
        VariableVisitor(),
        BinaryOpVisitor(),
        PrintVisitor(),
        VarDeclVisitor(),
        AssignmentVisitor(),
        IfVisitor(),
        ReadInputVisitor(),
        ReadEnvVisitor(),
    )

    val interpreter = Interpreter(visitors)

    val location = SourceLocation(0, 0)
    val program = listOf(Result.success<AstNode>(ReadInputNode("Enter a number", location, location))).iterator()

    interpreter.interpret(program, output, input, env)
}
