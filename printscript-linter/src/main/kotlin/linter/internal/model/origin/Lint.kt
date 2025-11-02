package linter.internal.model.origin

import model.diagnostic.category.Category

internal data object Lint : Category {
    override val name: String = "Lint"
}
