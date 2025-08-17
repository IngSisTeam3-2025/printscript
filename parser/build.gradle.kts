plugins {
    id("shared-build-config")
}

dependencies {
    implementation(project(":core"))
    implementation(project(":lexer"))
}