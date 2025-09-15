import ast.*
import location.SourceLocation
import visitors.*
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
        ReadEnvVisitor()
    )

    val interpreter = Interpreter(visitors)

    val location = SourceLocation(0,0)
    val program = listOf(Result.success<AstNode>(ReadInputNode("Enter a number", location, location)), ).iterator()

    interpreter.interpret(program, output, input, env)
}
