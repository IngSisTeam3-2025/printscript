plugins {
    id("shared-build-config")
    id("org.jetbrains.kotlinx.kover")
    `maven-publish`
}

dependencies {
    implementation(project(":printscript-core"))
    implementation(project(":printscript-api"))
}