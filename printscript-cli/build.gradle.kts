plugins {
    id("shared-build-config")
    id("org.jetbrains.kotlinx.kover")
}

dependencies {
    implementation(project(":printscript-api"))
    implementation(project(":printscript-core"))
    implementation(project(":printscript-lexer"))
    implementation(project(":printscript-parser"))
    implementation(project(":printscript-linter"))
    implementation(project(":printscript-formatter"))
    implementation(project(":printscript-validator"))

    implementation("com.github.ajalt.clikt:clikt:5.0.1")
    implementation("com.github.ajalt.clikt:clikt-markdown:5.0.1")
}
