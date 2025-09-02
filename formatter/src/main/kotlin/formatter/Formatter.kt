package formatter

import lexer.ILexer
import parser.IParser
import source.ISourceReader
import target.ITargetWriter

class Formatter(private val
                reader: ISourceReader,
                writer: ITargetWriter,
                lexer: ILexer,
                parser: IParser) : IFormatter {

    override fun format(): FormatResult {
        TODO("Not yet implemented")
    }

}