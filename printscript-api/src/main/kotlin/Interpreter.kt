import io.reader.SourceInput
import io.writer.SourceWriter
import model.diagnostic.Diagnostic
import model.node.Node
import util.option.Option

interface Interpreter {
    fun interpret(nodes: Sequence<Node>, input: SourceInput, output: SourceWriter): Option<Diagnostic>
}
