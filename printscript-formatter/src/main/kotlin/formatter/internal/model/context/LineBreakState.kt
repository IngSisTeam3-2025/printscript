package formatter.internal.model.context

import model.trivia.Trivia

internal data class LineBreakState(
    val pendingNewlines: List<Trivia> = emptyList(),
    val isFirstNode: Boolean = true,
)
