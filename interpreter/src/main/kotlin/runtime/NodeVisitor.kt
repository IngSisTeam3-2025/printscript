import ast.Num
import ast.BinOp
import ast.UnaryOp
import ast.Var
import ast.Assign
import ast.Str
import ast.VarDecl
import ast.PrintlnStmt
import ast.ExprStmt
import ast.Program
import ast.AbstractSyntaxTree

interface NodeVisitor {

    fun visit(node: AbstractSyntaxTree): RuntimeValue = when (node) {
        is Num          -> visitNum(node)
        is BinOp        -> visitBinOp(node)
        is UnaryOp      -> visitUnaryOp(node)
        is Var          -> visitVar(node)
        is Assign       -> visitAssign(node)
        is VarDecl      -> visitVarDecl(node)
        is Str          -> visitStr(node)
        is PrintlnStmt  -> visitPrintln(node)
        is ExprStmt     -> visitExprStmt(node)
        is Program      -> visitProgram(node)
        else -> error("No visit method for ${node::class.simpleName}")
    }

    // Expr
    fun visitNum(node: Num): RuntimeValue
    fun visitBinOp(node: BinOp): RuntimeValue
    fun visitUnaryOp(node: UnaryOp): RuntimeValue
    fun visitStr(node: Str): RuntimeValue
    fun visitVar(node: Var): RuntimeValue
    fun visitAssign(node: Assign): RuntimeValue

    // Stmt / Program
    fun visitVarDecl(node: VarDecl): RuntimeValue
    fun visitPrintln(node: PrintlnStmt): RuntimeValue
    fun visitExprStmt(node: ExprStmt): RuntimeValue
    fun visitProgram(node: Program): RuntimeValue
}
