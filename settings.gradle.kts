plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "printscript"

include("printscript-core", "printscript-api", "printscript-lexer", "printscript-parser", "printscript-linter", "printscript-formatter", "printscript-validator", "printscript-interpreter", "printscript-cli")
