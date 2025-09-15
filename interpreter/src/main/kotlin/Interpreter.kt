import ast.AstNode
import provider.DependencyProvider
import table.SymbolTable
import visitor.AstVisitor
import visitor.VisitResult
import visitor.VisitorDispatcher
import java.io.Reader
import java.io.Writer

class Interpreter(
    private val visitors: List<AstVisitor>,
) {
    private val symbolTable = SymbolTable()

    fun interpret(parser: Iterator<Result<AstNode>>, output: Writer, input: Reader, env: Map<String, String>) {
        val provider = DependencyProvider().apply {
            register(SymbolTable::class.java, symbolTable)
            register(java.io.Writer::class.java, output)
            register(java.io.Reader::class.java, input)
            register(Map::class.java, env)
        }

        val dispatcher = VisitorDispatcher(visitors)

        while (parser.hasNext()) {
            val result = parser.next()
            if (result.isFailure) {
                output.write("[ERROR] ${result.exceptionOrNull()?.message}\n")
                return
            }

            val node = result.getOrThrow()
            val res = dispatcher.dispatch(node, provider)
            if (res is VisitResult.Error) {
                output.write("[ERROR] ${res.message} at ${res.start.line}:${res.start.column}\n")
                return
            }
        }

        output.write("[SUCCESS]\n")
        output.flush()
        symbolTable.clear()
    }
}
