plugins {
    id("base")
    id("org.jetbrains.kotlinx.kover")
    id("co.uzzu.dotenv.gradle") version "4.0.0"
}

repositories {
    mavenCentral()
}

koverMerged {
    enable()
    htmlReport { onCheck = true }
    verify { rule { bound { minValue = 80 } } }
    filters {
        projects {
            excludes += listOf(":printscript-api", ":printscript-core", ":printscript-cli")
        }
    }
}

tasks.named("check") { dependsOn("koverMergedVerify") }
