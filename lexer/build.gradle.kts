plugins {
    id("shared-build-config")
    id("org.jetbrains.kotlinx.kover")
}

dependencies {
    implementation(project(":core"))
}

group = "io.github.ingsis"
version = "0.1.0"