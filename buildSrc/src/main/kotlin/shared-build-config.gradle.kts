plugins {
    id("io.gitlab.arturbosch.detekt")
    kotlin("jvm")
    application
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

detekt {
    buildUponDefaultConfig = true
    config.setFrom(
        resources.text.fromString(
            """
            style:
              NewLineAtEndOfFile:
                active: false
              SpacingBetweenPackageAndImports:
                active: true
              MaxLineLength:
                active: true
                maxLineLength: 120
              LoopWithTooManyJumpStatements:
                active: false

            complexity:
              TooManyFunctions:
                active: false
              CyclomaticComplexMethod:
                active: false
            """.trimIndent()
        )
    )
}

tasks.check {
    dependsOn("detekt")
}

kotlin {
    jvmToolchain(21)
}