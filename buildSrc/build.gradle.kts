import org.gradle.internal.impldep.org.codehaus.plexus.util.MatchPatterns.from

plugins {
    `kotlin-dsl`
    `maven-publish`
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.23")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.23.7")
}

allprojects {
    repositories {
        mavenCentral()
    }

    group = "io.github.ingsis"
    version = System.getenv("GITHUB_REF_NAME") ?: "0.1.0-SNAPSHOT"
}

subprojects {
    apply(plugin = "maven-publish")

    publishing {
        publications {
            create<MavenPublication>("mavenJava") {
                from(components["java"])
            }
        }
        repositories {
            maven {
                name = "GitHubPackages"
                val repo = System.getenv("GITHUB_REPOSITORY")
                url = uri("https://maven.pkg.github.com/$repo")
                credentials {
                    username = System.getenv("GITHUB_ACTOR")
                    password = System.getenv("GITHUB_TOKEN")
                }
            }
        }
    }
}