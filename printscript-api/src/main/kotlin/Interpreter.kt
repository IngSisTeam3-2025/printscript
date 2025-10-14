import io.reader.InputReader
import io.writer.OutputWriter
import model.diagnostic.Diagnostic
import model.node.Node
import type.option.Option

interface Interpreter {
    fun interpret(nodes: Sequence<Node>, input: InputReader, output: OutputWriter): Option<Diagnostic>
}
