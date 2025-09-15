package scanner

import iterator.PeekableIterator
import lexer.matcher.Match
import lexer.matcher.Matcher
import lexer.scanner.Scan

class Scanner(private val input: PeekableIterator, private val matcher: Matcher) {
    fun scan(): Scan {
        val startPos = input.position
        val lexemeBuilder = StringBuilder()
        var bestMatch = Match()

        while (input.peek().isSuccess) {
            val ch = input.peek().getOrNull()!!
            lexemeBuilder.append(ch)

            val currentBest = matcher.match(lexemeBuilder.toString())
            if (currentBest.length > bestMatch.length) {
                bestMatch = currentBest
                input.advance() // consume
            } else {
                break
            }
        }

        return if (bestMatch.length == 0 || bestMatch.type.name.isEmpty())
            Scan.Failure("Unexpected character '${lexemeBuilder}'", startPos, startPos )
        else
            Scan.Success(bestMatch.type, lexemeBuilder.toString(), startPos, input.position)
    }
}



