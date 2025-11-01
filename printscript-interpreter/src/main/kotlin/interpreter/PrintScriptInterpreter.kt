package interpreter

import Interpreter
import interpreter.internal.model.error.ConfigurationError
import interpreter.internal.table.VisitorTableRegistry
import interpreter.internal.util.DefaultSymbolTable
import interpreter.internal.util.SymbolTable
import io.reader.env.EnvReader
import io.reader.input.InputReader
import io.writer.OutputWriter
import model.diagnostic.Diagnostic
import model.node.Node
import model.visitor.context.ContextVisitorTable
import model.visitor.context.VisitorContext
import type.option.Option
import type.outcome.Outcome

class PrintScriptInterpreter : Interpreter {

    override fun interpret(
        version: String,
        nodes: Sequence<Node>,
        input: InputReader,
        output: OutputWriter,
        env: EnvReader,
    ): Sequence<Diagnostic> {
        return sequence {
            when (val table = getVisitorTable(version)) {
                is Option.Some -> {
                    yieldAll(interpretNodes(nodes, table.value, input, output, env))
                }
                is Option.None -> {
                    yield(buildConfigurationError(version))
                }
            }
        }
    }

    private fun getVisitorTable(version: String): Option<ContextVisitorTable> {
        return VisitorTableRegistry.get(version)
    }

    private fun buildConfigurationError(version: String): Diagnostic {
        return ConfigurationError("Unsupported language version '$version'")
    }

    private fun interpretNodes(
        nodes: Sequence<Node>,
        table: ContextVisitorTable,
        input: InputReader,
        output: OutputWriter,
        env: EnvReader,
    ): Sequence<Diagnostic> {
        return sequence {
            var context = VisitorContext()

            context = context.register(InputReader::class, input)
            context = context.register(OutputWriter::class, output)
            context = context.register(EnvReader::class, env)
            context = context.register(SymbolTable::class, DefaultSymbolTable())

            for (node in nodes) {
                val visit = table.dispatch(node, context)

                when (val outcome = visit.outcome) {
                    is Outcome.Error -> {
                        yield(outcome.error)
                        return@sequence
                    }
                    is Outcome.Ok -> {
                        context = visit.context
                    }
                }
            }
        }
    }
}
