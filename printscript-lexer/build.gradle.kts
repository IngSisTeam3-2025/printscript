plugins {
    id("shared-build-config")
    id("org.jetbrains.kotlinx.kover")
    `maven-publish`
}

dependencies {
    implementation(project(":printscript-core"))
    implementation(project(":printscript-api"))
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            groupId = "io.github.ingsis"
            artifactId = "lexer"
            version = "0.1.0"
        }
    }
}
