import ast.AstNode
import jdk.javadoc.doclet.Reporter
import visitor.IVisitor

class Validator(private val parser: Iterator<Result<AstNode>>,
                private val visitors: Collection<IVisitor>) {

    fun validate(reporter: Reporter) {
        TODO("Not yet implemented")
    }

}