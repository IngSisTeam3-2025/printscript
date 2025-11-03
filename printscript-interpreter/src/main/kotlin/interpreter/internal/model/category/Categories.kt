package interpreter.internal.model.category

import model.diagnostic.category.Category

internal data object IncorrectMethodCall : Category {
    override val name: String = "Incorrect Method Call"
}

internal data object System : Category {
    override val name: String = "System"
}

internal data object TypeMismatch : Category {
    override val name: String = "Type Mismatch"
}

internal data object UndefinedIdentifier : Category {
    override val name: String = "Undefined Identifier"
}

internal data object UnsupportedOperator : Category {
    override val name: String = "Unsupported Operator"
}

internal data object IO : Category {
    override val name: String = "IO"
}

internal data object InvalidOperandType : Category {
    override val name: String = "Invalid Operand Type"
}
