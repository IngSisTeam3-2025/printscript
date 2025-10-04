package model.origin

object LexerOrigin: Origin {
    override val name: String = "lexer"
}

object ParserOrigin: Origin {
    override val name: String = "parser"
}

data object LinterOrigin: Origin {
    override val name: String = "linter"
}

data object FormatterOrigin: Origin {
    override val name: String = "formatter"
}

data object ValidatorOrigin: Origin {
    override val name: String = "validator"
}

data object InterpreterOrigin: Origin {
    override val name: String = "interpreter"
}