package lexer.internal.model.terminal

import model.trivia.CommentTrivia
import model.trivia.NewlineTrivia
import model.trivia.SpaceTrivia
import model.trivia.TabTrivia

internal class CommentTerminal : TriviaTerminal {
    override val type = CommentTrivia
    override val pattern = Regex("^/\\*[\\s\\S]*?\\*/")
    override val priority = 0
}

internal class LineCommentTerminal : TriviaTerminal {
    override val type = CommentTrivia
    override val pattern = Regex("^//.*(?=\\r?\\n|$)")
    override val priority = 0
}

internal class NewlineTerminal : TriviaTerminal {
    override val type = NewlineTrivia
    override val pattern = Regex("\r\n|\n|\r")
    override val priority = 0
}

internal class SpaceTerminal : TriviaTerminal {
    override val type = SpaceTrivia
    override val pattern = Regex("^ ")
    override val priority = 0
}

internal class TabTerminal : TriviaTerminal {
    override val type = TabTrivia
    override val pattern = Regex("^\t")
    override val priority = 0
}
