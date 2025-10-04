package model.span

data class Position(val line: Int, val column: Int, val offset: Int) {
    fun format(): String = "$line:$column"
}