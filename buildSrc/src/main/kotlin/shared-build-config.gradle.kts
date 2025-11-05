plugins {
    id("io.gitlab.arturbosch.detekt")
    id("com.diffplug.spotless")
    id("org.jetbrains.kotlinx.kover")
    kotlin("jvm")
    id("maven-publish")
}

repositories { mavenCentral() }

dependencies { testImplementation(kotlin("test")) }

tasks.test { useJUnitPlatform() }

detekt {
    buildUponDefaultConfig = true
    config.setFrom(files("$rootDir/detekt/detekt.yml"))
}

kotlin { jvmToolchain(17) }

tasks.check { dependsOn("detekt","spotlessCheck", "koverVerify") }

tasks.named("build") {
    dependsOn("spotlessCheck", "check")
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    jvmTarget = "17"
}

spotless {
    kotlin {
        target("**/*.kt")
        targetExclude("build/**/*.kt")
        ktlint().editorConfigOverride(
            mapOf(
                "indent_size" to "4",
                "insert_final_newline" to "true"
            )
        )
        trimTrailingWhitespace()
    }
}

apply(plugin = "maven-publish")


val envFile = rootProject.file(".env")
val envVars = if (envFile.exists()) {
    envFile.readLines()
        .filter { it.isNotBlank() && !it.startsWith("#") }
        .associate {
            val (key, value) = it.split("=", limit = 2)
            key.trim() to value.trim()
        }
} else {
    emptyMap()
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/${envVars["GITHUB_REPOSITORY"] ?: System.getenv("GITHUB_REPOSITORY")}")
            credentials {
                username = envVars["GITHUB_ACTOR"] ?: System.getenv("GITHUB_ACTOR")
                password = envVars["GITHUB_TOKEN"] ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }
    publications {
        create<MavenPublication>("gpr") {
            from(components["java"])
        }
    }
}