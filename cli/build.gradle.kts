plugins {
    id("shared-build-config")
    id("org.jetbrains.kotlinx.kover")
}

dependencies {
    implementation(project(":core"))
    implementation("com.github.ajalt.clikt:clikt:5.0.1")
    implementation("com.github.ajalt.clikt:clikt-markdown:5.0.1")
}