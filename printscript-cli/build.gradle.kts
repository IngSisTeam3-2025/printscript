plugins {
    application
    id("shared-build-config")
    id("org.jetbrains.kotlinx.kover")
}

application {
    mainClass.set("MainKt")
}

version = "1.0.1-SNAPSHOT"

dependencies {
    implementation(project(":printscript-api"))
    implementation(project(":printscript-core"))
    implementation(project(":printscript-lexer"))
    implementation(project(":printscript-parser"))
    implementation(project(":printscript-linter"))
    implementation(project(":printscript-formatter"))
    implementation(project(":printscript-validator"))
    implementation(project(":printscript-interpreter"))

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.18.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.18.0")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.18.0")

    implementation("com.github.ajalt.clikt:clikt:5.0.1")
    implementation("com.github.ajalt.clikt:clikt-markdown:5.0.1")
}
