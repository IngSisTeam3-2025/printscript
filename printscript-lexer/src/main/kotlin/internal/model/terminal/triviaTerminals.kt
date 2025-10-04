package internal.model.terminal

import model.trivia.Comment
import model.trivia.Newline
import model.trivia.Space
import model.trivia.Tab

internal class SpaceTerminal : TriviaTerminal {
    override val type = Space
    override val pattern = Regex("^ ")
    override val priority = 0
}

internal class NewlineTerminal : TriviaTerminal {
    override val type = Newline
    override val pattern = Regex("^\n")
    override val priority = 0
}

internal class TabTerminal : TriviaTerminal {
    override val type = Tab
    override val pattern = Regex("^\t")
    override val priority = 0
}

internal class CommentTerminal : TriviaTerminal {
    override val type = Comment
    override val pattern = Regex("^/\\*[\\s\\S]*?\\*/")
    override val priority = 0
}
