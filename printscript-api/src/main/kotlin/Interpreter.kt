import io.reader.env.EnvReader
import io.reader.input.InputReader
import io.writer.OutputWriter
import model.diagnostic.Diagnostic
import model.node.Node

interface Interpreter {
    fun interpret(
        version: String,
        nodes: Sequence<Node>,
        input: InputReader,
        output: OutputWriter,
        env: EnvReader,
    ): Sequence<Diagnostic>
}
