plugins {
    id("base")
    id("org.jetbrains.kotlinx.kover")
}

repositories {
    mavenCentral()
}

koverMerged {
    enable()
    htmlReport { onCheck = true }
    verify { rule { bound { minValue = 20 } } }
}

tasks.named("check") { dependsOn("koverMergedVerify") }
