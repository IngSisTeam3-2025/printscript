plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "printscript"
include("core", "lexer", "parser", "interpreter", "app","cli")
