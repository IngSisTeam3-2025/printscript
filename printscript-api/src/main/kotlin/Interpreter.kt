import io.reader.env.EnvReader
import io.reader.input.InputReader
import io.writer.OutputWriter
import model.diagnostic.Diagnostic
import model.node.Node
import type.option.Option

interface Interpreter {
    fun interpret(
        version: String,
        nodes: Sequence<Node>,
        input: InputReader,
        output: OutputWriter,
        env: EnvReader,
    ): Option<Diagnostic>
}
