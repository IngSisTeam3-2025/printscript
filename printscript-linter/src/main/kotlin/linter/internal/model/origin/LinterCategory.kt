package linter.internal.model.origin

import model.diagnostic.category.Category

internal data object LinterCategory : Category {
    override val name: String = "linter"
}
