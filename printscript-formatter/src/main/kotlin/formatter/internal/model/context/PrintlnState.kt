package formatter.internal.model.context

import model.trivia.Trivia

internal data class PrintlnState(
    val pendingNewlines: List<Trivia> = emptyList(),
    val hadPreviousPrintln: Boolean = false,
)
