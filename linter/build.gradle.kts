plugins {
    id("shared-build-config")
    id("org.jetbrains.kotlinx.kover")

}

dependencies {
    implementation(project(":lexer"))
    implementation(project(":parser"))
}