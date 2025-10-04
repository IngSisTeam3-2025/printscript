plugins {
    id("shared-build-config")
    id("org.jetbrains.kotlinx.kover")

}

dependencies {
    implementation(project(":printscript-core"))
    implementation(project(":printscript-api"))

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.18.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.18.0")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.18.0")
}