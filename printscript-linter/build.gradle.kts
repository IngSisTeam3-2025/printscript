plugins {
    id("shared-build-config")
    id("org.jetbrains.kotlinx.kover")

}

version = "1.0.0-SNAPSHOT"

dependencies {
    implementation(project(":printscript-core"))
    implementation(project(":printscript-api"))
}