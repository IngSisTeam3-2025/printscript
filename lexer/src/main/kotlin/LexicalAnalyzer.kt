package lexer

import token.Token
import iterator.PeekableIterator
import lexer.matcher.Matcher
import lexer.scanner.Scan
import scanner.Scanner
import location.SourceLocation
import java.util.Collections.emptyIterator

class LexicalAnalyzer(
    private val source: Iterator<Char> = emptyIterator(),
    private val lexers: List<Lexer> = emptyList(),
    private val reporter: Reporter
) : Iterator<Result<Token>> {

    private val scanner = Scanner(PeekableIterator(source), Matcher(lexers))

    override fun hasNext(): Boolean {
        return source.hasNext()
    }

    override fun next(): Result<Token> {
        return when (val scan = scanner.scan()) {
            is Scan.Success -> {
                Result.success(Token(scan.type, scan.lexeme, SourceLocation(0,0), SourceLocation(0,0)))
            }
            is Scan.Failure -> {
                reporter.reportError(scan.message, SourceLocation(0,0), SourceLocation(0,0))
                Result.failure(Exception(scan.message))
            }
        }
    }
}



