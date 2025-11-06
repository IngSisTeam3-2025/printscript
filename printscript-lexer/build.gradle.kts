plugins {
    id("shared-build-config")
    id("org.jetbrains.kotlinx.kover")
    `maven-publish`
}

version = "1.0.0"

dependencies {
    implementation(project(":printscript-core"))
    implementation(project(":printscript-api"))
}