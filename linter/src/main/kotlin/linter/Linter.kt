package linter

import lexer.ILexer
import parser.IParser
import source.ISourceReader
import target.ITargetWriter

@SuppressWarnings("all")
class Linter(
    private val
    reader: ISourceReader,
    writer: ITargetWriter,
    lexer: ILexer,
    parser: IParser,
) : ILinter {

    override fun lint(): LintResult {
        TODO("Not yet implemented")
    }
}
